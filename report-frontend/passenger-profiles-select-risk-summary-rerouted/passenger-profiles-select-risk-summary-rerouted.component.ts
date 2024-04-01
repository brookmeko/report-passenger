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
  selector: 'app-passenger-profiles-select-risk-summary-rerouted',
  templateUrl: './passenger-profiles-select-risk-summary-rerouted.component.html',
  styleUrls: ['./passenger-profiles-select-risk-summary-rerouted.component.scss'],
})
export class PassengerProfileSelectRiskSummaryReroutedComponent {
  form = new FormGroup({});
  model = {};
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'd-flex justify-content-between mb-3', // Added 'mb-3' class for margin bottom
      fieldGroup: [

        {
          key: 'startDate',
          type: 'input',
          props: {
            type: 'date',
            label: 	 this.translate.instant('PAGES.REPORT.LICENSE.START_DATE'),
            required: true,
          },
        },
        {
          key: 'endDate',
          type: 'input',
          props: {
            type: 'date',
            label: 	this.translate.instant('PAGES.REPORT.LICENSE.END_DATE'),
            required: true,
          },
        },

        {
          key: 'reportFormat',
          type: 'select',
          templateOptions: {
            label: this.translate.instant('PAGES.REPORT.LICENSE.REPORT_FORMAT'),
            required: true,
            options: [
              { value: 'Table', label:  this.translate.instant('PAGES.REPORT.LICENSE.TABLE') },
              // { value: 'Chart', label: this.translate.instant('PAGES.REPORT.LICENSE.CHART') },
              // { value: 'Table with chart', label: this.translate.instant('PAGES.REPORT.LICENSE.TABLE_WITH_CHART')},
            ],
            placeholder: this.translate.instant(
              'PAGES.REPORT.LICENSE.SELECT_REPORT_FORMAT',
            ),
          },
        },
      ],
    },
  ];


  onSubmit() {
    if (this.form.valid) {
      // @ts-ignore
      const {   startDate, endDate ,reportFormat} = this.model;


      // Perform your logic here, e.g., call the API with the selected values
      this.loadReport(  startDate, endDate,reportFormat);
    }
  }



  // @ts-ignore
  pdfUrl: SafeResourceUrl;

  constructor(private http: HttpClient,
              private sanitizer: DomSanitizer,
              private translate: TranslateService, private httpService: HttpService) {
  }


  // @ts-ignore
  loadReport(  startDate, endDate,reportFormat): void {
    console.log(status, startDate, endDate )
    const headers = new HttpHeaders({ 'Accept': 'application/pdf' });
    this.http.get(environment.cmsGateWayRootContext + 'customsOperations/passenger/report/rerouted/selectRisk/'  + startDate + '/' + endDate  +'/'+ reportFormat, {
      headers: headers,
      responseType: 'arraybuffer',
    }).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    });
   }
}
