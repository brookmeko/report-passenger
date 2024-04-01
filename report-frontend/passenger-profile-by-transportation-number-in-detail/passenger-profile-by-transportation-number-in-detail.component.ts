import {Component, SecurityContext} from '@angular/core';
// import {LicenseService} from "@license/training/services/feed-back.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {FormGroup} from "@angular/forms";
import {FormlyFieldConfig} from "@ngx-formly/core";
import {map} from "rxjs";
import {TranslateService} from "@ngx-translate/core";
import {HttpService} from "@core/services/http.service";
import {take} from "rxjs/operators";
import {environment} from "../../../../environments/environment";
import {RegionService} from "@look-up/regions/services/region.service";

@Component({
  selector: 'app-passenger-profile-by-transportation-number-in-detail',
  templateUrl: './passenger-profile-by-transportation-number-in-detail.component.html',
  styleUrls: ['./passenger-profile-by-transportation-number-in-detail.component.scss'],
})
export class PassengerProfileByTransportationNumberInDetailComponent {
  form = new FormGroup({});
  model = {};
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row', // Added 'mb-3' class for margin bottom
      fieldGroup: [

        {
          className: 'col-md-3',
          key: 'transportationNumber',
          type: 'input',
          templateOptions: {
            label: 	this.translate.instant('PAGES.REPORT.PASSENGER.FLIGHT_NUMBER'),
            required: true,
           },
        },
        {
          className: 'col-md-3',
          key: 'startDate',
          type: 'input',
          props: {
            type: 'date',
            label: 	 this.translate.instant('PAGES.REPORT.LICENSE.START_DATE'),
            required: true,
          },
        },
        {
          className: 'col-md-3',
          key: 'endDate',
          type: 'input',
          props: {
            type: 'date',
            label: 	this.translate.instant('PAGES.REPORT.LICENSE.END_DATE'),
            required: true,
          },
        },

        {
          className: 'col-md-3',
          key: 'riskLevel',
          type: 'select',
          templateOptions: {
            label: this.translate.instant('PAGES.REPORT.PASSENGER.RISK_LEVEL'),
            required: true,
            options: [
              { value: 'RED', label: this.translate.instant('PAGES.REPORT.PASSENGER.RED') },
              { value: 'GREEN', label: this.translate.instant('PAGES.REPORT.PASSENGER.GREEN')},

            ],
            placeholder: this.translate.instant(
              'PAGES.REPORT.PASSENGER.SELECT_RISK_LEVEL',
            ),
          },
        },

      ],
    },
  ];


  onSubmit() {
    if (this.form.valid) {
      // @ts-ignore
      const {  transportationNumber, startDate, endDate ,riskLevel} = this.model;


      // Perform your logic here, e.g., call the API with the selected values
      this.loadReport( transportationNumber, startDate, endDate,riskLevel);
    }
  }



  // @ts-ignore
  pdfUrl: SafeResourceUrl;

  constructor(private http: HttpClient,
              private sanitizer: DomSanitizer,
              private translate: TranslateService, private httpService: HttpService) {
  }


  // @ts-ignore
  loadReport( transportationNumber, startDate, endDate,riskLevel): void {
    console.log(status, startDate, endDate )
    const headers = new HttpHeaders({ 'Accept': 'application/pdf' });
    this.http.get(environment.cmsGateWayRootContext + 'customsOperations/passenger/report/transportationNumber/inDetail/'  + startDate + '/' + endDate + '/'+ transportationNumber +'/'+ riskLevel, {
      headers: headers,
      responseType: 'arraybuffer',
    }).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    });
   }
}
