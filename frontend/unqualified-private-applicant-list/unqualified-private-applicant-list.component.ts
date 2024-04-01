import {Component, OnInit} from '@angular/core';
import {
  SearchTraineeApplicationResponse,
  TraineeApplicationResponse,
  TraineeApplicationStatus
} from "@license/trainee-application/models/trainee-application.model";
import {map, Observable} from "rxjs";
import {FormGroup} from "@angular/forms";
import {TraineeApplicationType, Training} from "@license/training/models/training.model";
import {FormlyFieldConfig} from "@ngx-formly/core";
import {TraineeApplicationService} from "@license/trainee-application/services/trainee-application.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {TrainingService} from "@license/training/services/training.service";
import {TranslateService} from "@ngx-translate/core";
import {filter, take} from "rxjs/operators";
import {DataTableColumn} from "@model/data-table.model";

@Component({
  selector: 'app-unqualified-private-applicant-list',
  templateUrl: './unqualified-private-applicant-list.component.html',
  styleUrls: ['./unqualified-private-applicant-list.component.scss']
})
export class UnqualifiedPrivateApplicantListComponent implements OnInit{

  selectedTraining: any;
  traineeApplicationResponse$: Observable<TraineeApplicationResponse[]> | null = null;
  trainingId: any;
  successAlert: any = null;
  form = new FormGroup({});
  model: Training = {} as Training;
  fields: FormlyFieldConfig[]=[
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
              map((response:any) => response.data)
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
          }
        },
      ],
    },
  ];

  constructor(
    private traineeApplicationService: TraineeApplicationService,
    private modalService: NgbModal,
    private trainingService: TrainingService,
    private translate: TranslateService
  ) {  }

  ngOnInit() {
    // Initialize the form with the fields
    this.form = new FormGroup({});
    this.model = {} as Training;
  }

  fetchTraineeApplications() {
    this.traineeApplicationResponse$ = this.traineeApplicationService
      .fetchByTrainingIdAndTraineeApplicationTypeAndByTraineeApplicationStatus(
        this.selectedTraining,
        TraineeApplicationStatus.UNQUALIFIED,
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
}
