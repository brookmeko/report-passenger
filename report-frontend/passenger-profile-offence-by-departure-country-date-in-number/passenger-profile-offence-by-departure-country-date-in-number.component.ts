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

import { CountryService } from "@look-up/country/services/country.service";

@Component({
  selector: 'app-passenger-profile-offence-by-departure-country-date-in-number',
  templateUrl: './passenger-profile-offence-by-departure-country-date-in-number.component.html',
  styleUrls: ['./passenger-profile-offence-by-departure-country-date-in-number.component.scss'],
})
export class PassengerProfileOffenceByDepartureCountryDateInNumberComponent {
  form = new FormGroup({});
  model = {};
  fields: FormlyFieldConfig[] = [
    {
      fieldGroupClassName: 'row', // Added 'mb-3' class for margin bottom
      fieldGroup: [

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
          key: 'country.id',
          type: 'select',
          props: {
            label: this.translate.instant('PAGES.LOOK_UP.COUNTRY.TITLE'),
            required: true,
            options: this.countryService.getCountries().pipe(
              map(response => {
                return <any>response.data?.countries;
              }),
            ),
            valueProp: 'id',
            labelProp: 'countryName',
          },
        },


        {
          className: 'col-md-3',
          key: 'reportFormat',
          type: 'select',
          templateOptions: {
            label: this.translate.instant('PAGES.REPORT.LICENSE.REPORT_FORMAT'),
            required: true,
            options: [
              { value: 'Table', label:  this.translate.instant('PAGES.REPORT.LICENSE.TABLE') },
              { value: 'Chart', label: this.translate.instant('PAGES.REPORT.LICENSE.CHART') },
              { value: 'Table with chart', label: this.translate.instant('PAGES.REPORT.LICENSE.TABLE_WITH_CHART')},
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
      const {   startDate, endDate ,reportFormat, departureCountry} = this.model;


      // Perform your logic here, e.g., call the API with the selected values
      this.loadReport(  startDate, endDate,reportFormat, departureCountry);
    }
  }



  // @ts-ignore
  pdfUrl: SafeResourceUrl;

  constructor(private http: HttpClient,
              private sanitizer: DomSanitizer,
              private countryService: CountryService,

              private translate: TranslateService, private httpService: HttpService) {
  }


  // @ts-ignore
  loadReport(  startDate, endDate,reportFormat,departureCountry): void {
    console.log(status, startDate, endDate )
    const headers = new HttpHeaders({ 'Accept': 'application/pdf' });
    this.http.get(environment.cmsGateWayRootContext + 'customsOperations/passenger/report/offence/departureCountry/transportationDate/inNumber/'  + startDate + '/' + endDate + '/' + departureCountry +'/'+ reportFormat, {
      headers: headers,
      responseType: 'arraybuffer',
    }).subscribe(response => {
      const blob = new Blob([response], { type: 'application/pdf' });
      this.pdfUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
    });
   }
}
