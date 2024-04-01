import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import {
  SearchTraineeApplicationResponse,
  TraineeApplicationResponse,
  TraineeApplicationStatus,

} from '@license/trainee-application/models/trainee-application.model';
import { filter, map, take } from 'rxjs/operators';
import { DataTableColumn } from '@model/data-table.model';
import { TraineeApplicationService } from '@license/trainee-application/services/trainee-application.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TrainingService } from '@license/training/services/training.service';
import {TraineeApplicationType, Training} from '@license/training/models/training.model';
import { FormGroup } from '@angular/forms';
import { FormlyFieldConfig } from '@ngx-formly/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-training-private-accepted-list',
  templateUrl: './training-private-accepted-list.component.html',
  styleUrls: ['./training-private-accepted-list.component.scss'],
})
export class TrainingPrivateAcceptedListComponent implements OnInit {
  selectedTraining: any;
  traineeApplicationResponse$: Observable<TraineeApplicationResponse[]> | null = null;
  successAlert: any = null;
  trainingId: any;
  form = new FormGroup({});
  model: Training = {} as Training;
  fields: FormlyFieldConfig[] = [
    {
      fieldGroup: [
        {
          key: 'training.id',
          type: 'select',
          props: {
            placeholder: this.translate.instant(
              'PAGES.LICENSE.TRAINING.SELECT_TRAINING_STATUS_TYPE'
            ),
            required: true,
            options: this.trainingService.fetchTraining().pipe(
              map((response: any) => response.data)
            ) as Observable<any[]>,
            valueProp: 'id',
            labelProp: 'title',
            change: (field) => {
              const id = field.formControl?.value;
              this.selectedTraining = id;
              if (this.selectedTraining) {
                this.fetchTraineeApplications();
              } else {
                this.traineeApplicationResponse$ = null;
              }
            },
          },
        },
      ],
    },
  ];

  constructor(
    private traineeApplicationService: TraineeApplicationService,
    private modalService: NgbModal,
    private trainingService: TrainingService,
    private translate: TranslateService
  ) {}

  ngOnInit() {
    // Initialize the form with the fields
    this.form = new FormGroup({});
    this.model = {} as Training;
  }

  fetchTraineeApplications() {
    this.traineeApplicationResponse$ = this.traineeApplicationService
      .fetchByTrainingIdAndTraineeApplicationTypeAndByTraineeApplicationStatus(
        this.selectedTraining,
        TraineeApplicationStatus.CHECKED,
        TraineeApplicationType.PRIVATE
      )
      .pipe(
        take(1),
        filter((response: SearchTraineeApplicationResponse) => !!response?.data),
        map((response: SearchTraineeApplicationResponse) => response.data || [])
      );
  }

  readonly tableColumns: DataTableColumn<TraineeApplicationResponse>[] = [
    {
      header: 'PAGES.LICENSE.TRAINEE.FIRST_NAME',
      value: (row: TraineeApplicationResponse) =>
        row.traineeProfileResponse.firstName,
    },
    {
      header: 'PAGES.LICENSE.TRAINEE.MIDDLE_NAME',
      value: (row: TraineeApplicationResponse) =>
        row.traineeProfileResponse.middleName,
    },
    {
      header: 'PAGES.LICENSE.TRAINEE.LAST_NAME',
      value: (row: TraineeApplicationResponse) =>
        row.traineeProfileResponse.lastName,
    },
    {
      header: 'PAGES.LICENSE.TRAINEE.GENDER',
      value: (row: TraineeApplicationResponse) =>
        row.traineeProfileResponse.gender,
    },
    {
      header: 'PAGES.LICENSE.TRAINEE.STATUS',
      value: (row: TraineeApplicationResponse) =>
        row.traineeApplicationStatus,
    },
    {
      header: 'PAGES.LICENSE.TRAINEE.TYPE',
      value: (row: TraineeApplicationResponse) =>
        row.traineeApplicationType,
    },
  ];

  // You can add other methods or functionalities as needed
  checkExamQuota() {
    this.traineeApplicationService.checkExamQuota(this.selectedTraining);
  }
}
