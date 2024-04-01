import { Component, ComponentFactoryResolver, ComponentRef, ViewChild, ViewContainerRef } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { FormlyFieldConfig } from "@ngx-formly/core";
import { TranslateService } from "@ngx-translate/core";

import { PassengerProfileByTransportationNumberInNumberComponent } from '../passenger-profile-by-transportation-number-in-number/passenger-profile-by-transportation-number-In-number.component';
import { PassengerProfileByTransportationNumberInDetailComponent } from '../passenger-profile-by-transportation-number-in-detail/passenger-profile-by-transportation-number-in-detail.component';
import { PassengerProfileByTransportationDateInNumberComponent } from '../passenger-profile-by-transportation-date-in-number/passenger-profile-by-transportation-date-in-number.component';
import { PassengerProfileByTransportationDateInDetailComponent } from '../passenger-profile-by-transportation-date-in-detail/passenger-profile-by-transportation-date-in-detail.component';
import { PassengerProfileOffenceByTransportationNumberInNumberComponent } from '../passenger-profile-offence-by-transportation-number-in-number/passenger-profile-offence-by-transportation-number-in-number.component';
import { PassengerProfileOffenceByTransportationNumberInDetailComponent } from '../passenger-profile-offence-by-transportation-number-in-detail/passenger-profile-offence-by-transportation-number-in-detail.component';
import { PassengerProfileOffenceByTransportationDateInNumberComponent } from '../passenger-profile-offence-by-transportation-date-in-number/passenger-profile-offence-by-transportation-date-in-number.component';
import { PassengerProfileOffenceByDepartureCountryDateInNumberComponent } from '../passenger-profile-offence-by-departure-country-date-in-number/passenger-profile-offence-by-departure-country-date-in-number.component';
import { PassengerProfileOffenceByRandomSelectionCriteriaInNumberComponent } from '../passenger-profile-offence-by-random-selection-criteria-in-number/passenger-profile-offence-by-random-selection-criteria-in-number.component';
import { PassengerProfileOffenceByRandomSelectionCriteriaInDetailComponent } from '../passenger-profile-offence-by-random-selection-criteria-in-detail/passenger-profile-offence-by-random-selection-criteria-in-detail.component';
import { PassengerProfileByRiskCriterialInDetailComponent } from '../passenger-profile-by-risk-criterial-in-detail/passenger-profile-by-risk-criterial-in-detail.component';
import { PassengerProfileByRiskCriterialFrequencyInNumberComponent } from '../passenger-profile-by-risk-criterial-frequency-in-number/passenger-profile-by-risk-criterial-frequency-in-number.component';
import { PassengerProfileByRiskCriterialSelectivityInDetailComponent } from '../passenger-profile-by-risk-criterial-selectivity-in-detail/passenger-profile-by-risk-criterial-selectivity-in-detail.component';
import { PassengerProfilesByRiskLevelFrequencyInNumberComponent } from '../passenger-profile-by-risk-level-frequency-in-number/passenger-profile-by-risk-level-frequency-in-number.component';
import {
  PassengerProfilesByFrequencyOfEachCriteriaComponent
} from "@app/report-management/passenger-service/passenger-profiles-by-frequency-of-each-criteria/passenger-profiles-by-frequency-of-each-criteria.component";
import {
  PassengerProfilesByRiskSelectivityLevelInDetailComponent
} from "@app/report-management/passenger-service/passenger-profiles-by-risk-selectivity-level-in-detail/passenger-profiles-by-risk-selectivity-level-in-detail.component";
import {
  SummaryOfFrequencyRiskLevelComponent
} from "@app/report-management/passenger-service/summary-of-frequency-risk-level/summary-of-frequency-risk-level.component";
import {
  PassengerProfileSummaryManuallyReroutedComponent
} from "@app/report-management/passenger-service/passenger-profiles-summary-manually-rerouted/passenger-profiles-summary-manually-rerouted.component";
import {
  PassengerProfileSelectRiskSummaryReroutedComponent
} from "@app/report-management/passenger-service/passenger-profiles-select-risk-summary-rerouted/passenger-profiles-select-risk-summary-rerouted.component";
import {
  PassengerProfilesReroutedRandomSelectionRiskLevelComponent
} from "@app/report-management/passenger-service/passenger-profiles-rerouted-random-selection-risk-level/passenger-profiles-rerouted-random-selection-risk-level.component";

@Component({
  selector: 'app-passenger-service-viewer',
  templateUrl: './passenger-service-viewer.component.html',
  styleUrls: ['./passenger-service-viewer.component.scss']
})
export class PassengerServiceViewerComponent {

  @ViewChild('componentContainer', { read: ViewContainerRef, static: true })
  componentContainer!: ViewContainerRef;

  componentRef!: ComponentRef<any>;

  constructor(private resolver: ComponentFactoryResolver, private translate: TranslateService) { }


  form = new FormGroup({});
  fields: FormlyFieldConfig[] = [
    {
      key: 'selection',
      type: 'select',
      templateOptions: {

        options: [

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_IN_TRANSPORTATION_NUMBER'), value: 'PassengerProfileByTransportationNumberInNumber' },
          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_IN_TRANSPORTATION_NUMBER_DETAIL'), value: 'PassengerProfileByTransportationNumberInDetail' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_IN_TRANSPORTATION_DATE'), value: 'PassengerProfileByTransportationDateInNumber' },
          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_IN_TRANSPORTATION_DATE_DETAIL'), value: 'PassengerProfileByTransportationDateInDetail' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_OFFENCE_IN_TRANSPORTATION_NUMBER'), value: 'PassengerProfileOffenceByTransportationNumberInNumber' },
          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_OFFENCE_IN_TRANSPORTATION_NUMBER_DETAIL'), value: 'PassengerProfileOffenceByTransportationNumberInDetail' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_OFFENCE_IN_TRANSPORTATION_DATE'), value: 'PassengerProfileOffenceByTransportationDateInNumber' },
          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_OFFENCE_IN_DEPARTURE_COUNTRY_DETAIL'), value: 'PassengerProfileOffenceByDepartureCountryDateInNumber' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_OFFENCE_RANDOM_IN_TRANSPORTATION_NUMBER'), value: 'PassengerProfileOffenceByRandomSelectionCriteriaInNumber' },
          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_OFFENCE_RANDOM_IN_TRANSPORTATION_NUMBER_DETAIL'), value: 'PassengerProfileOffenceByRandomSelectionCriteriaInDetail' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_BY_RISK_CRITERIAL_DETAIL'), value: 'PassengerProfileByRiskCriterialInDetail' },
          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_IN_RISK_CRITERIAL_FREQUENCY_IN_NUMBER'), value: 'PassengerProfileByRiskCriterialFrequencyInNumber' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_BY_RISK_CRITERIAL_SELECTIVITY_DETAIL'), value: 'PassengerProfileByRiskCriterialSelectivityInDetail' },
          // { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_BY_TRNSPORTATION_DATE_IN_NUMBER'), value: 'PassengerProfilesByRiskLevelFrequencyInNumber' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_BY_FREQUENCY_OF_EACH_CRITERIA'), value: 'PassengerProfilesByFrequencyOfEachCriteria' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_BY_RISK_SELECTIVITY_LEVEL_IN_DETAIL'), value: 'PassengerProfilesByRiskSelectivityLevelInDetail' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.SUMMARY_OF_FREQUENCY_RISK_LEVEL'), value: 'SummaryOfFrequencyRiskLevel' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_SUMMARY_MANUALLY_REROUTED'), value: 'PassengerProfileSummaryManuallyRerouted' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_SELECT_RISK_SUMMARY_REROUTED'), value: 'PassengerProfileSelectRiskSummaryRerouted' },

          { label: this.translate.instant('PAGES.REPORT.PASSENGER_SERVICE.PASSENGER_PROFILES_REROUTED_RANDOM_SELECTION_RISK_LEVEL'), value: 'PassengerProfilesReroutedRandomSelectionRiskLevel' },


        ],
        placeholder: this.translate.instant(
          'PAGES.REPORT.PASSENGER_SERVICE.SELECT_PASSENGERS_REPORT',
        ),
        change: (field) => {
          // @ts-ignore
          const selectedValue = field.formControl.value;
          // Perform actions based on the selected value
          console.log('Selected value:', selectedValue);
          this.updateComponents(selectedValue);


        },
      },
      hooks: {
        onInit: (field) => {
          // @ts-ignore
          field.formControl.valueChanges.subscribe((value) => {
            this.updateComponents(value);
          });
        },
      },
    },
  ];



  updateComponents(selectedValue: string) {
    // Remove the previously rendered component (if any)
    if (this.componentRef) {
      this.componentRef.destroy();
    }

    // Display or hide components based on the selected value
    switch (selectedValue) {

      case 'PassengerProfileByTransportationNumberInNumber':
        const passengerProfileByTransportationNumberInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByTransportationNumberInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByTransportationNumberInNumberComponentFactory);
        break;

      case 'PassengerProfileByTransportationNumberInDetail':
        const passengerProfileByTransportationNumberInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByTransportationNumberInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByTransportationNumberInDetailComponentFactory);
        break;

      case 'PassengerProfileByTransportationDateInNumber':
        const passengerProfileByTransportationDateInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByTransportationDateInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByTransportationDateInNumberComponentFactory);
        break;

      case 'PassengerProfileByTransportationDateInDetail':
        const passengerProfileByTransportationDateInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByTransportationDateInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByTransportationDateInDetailComponentFactory);
        break;

      case 'PassengerProfileOffenceByTransportationNumberInNumber':
        const passengerProfileOffenceByTransportationNumberInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileOffenceByTransportationNumberInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileOffenceByTransportationNumberInNumberComponentFactory);
        break;

      case 'PassengerProfileOffenceByTransportationNumberInDetail':
        const passengerProfileOffenceByTransportationNumberInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileOffenceByTransportationNumberInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileOffenceByTransportationNumberInDetailComponentFactory);
        break;

      case 'PassengerProfileOffenceByTransportationDateInNumber':
        const passengerProfileOffenceByTransportationDateInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileOffenceByTransportationDateInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileOffenceByTransportationDateInNumberComponentFactory);
        break;

      case 'PassengerProfileOffenceByDepartureCountryDateInNumber':
        const passengerProfileOffenceByDepartureCountryDateInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileOffenceByDepartureCountryDateInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileOffenceByDepartureCountryDateInNumberComponentFactory);
        break;

      case 'PassengerProfileOffenceByRandomSelectionCriteriaInNumber':
        const passengerProfileOffenceByRandomSelectionCriteriaInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileOffenceByRandomSelectionCriteriaInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileOffenceByRandomSelectionCriteriaInNumberComponentFactory);
        break;

      case 'PassengerProfileOffenceByRandomSelectionCriteriaInDetail':
        const passengerProfileOffenceByRandomSelectionCriteriaInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileOffenceByRandomSelectionCriteriaInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileOffenceByRandomSelectionCriteriaInDetailComponentFactory);
        break;

      case 'PassengerProfileByRiskCriterialInDetail':
        const passengerProfileByRiskCriterialInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByRiskCriterialInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByRiskCriterialInDetailComponentFactory);
        break;

      case 'PassengerProfileByRiskCriterialFrequencyInNumber':
        const passengerProfileByRiskCriterialFrequencyInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByRiskCriterialFrequencyInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByRiskCriterialFrequencyInNumberComponentFactory);
        break;

      case 'PassengerProfileByRiskCriterialSelectivityInDetail':
        const passengerProfileByRiskCriterialSelectivityInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileByRiskCriterialSelectivityInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileByRiskCriterialSelectivityInDetailComponentFactory);
        break;

      case 'PassengerProfilesByRiskLevelFrequencyInNumber':
        const passengerProfilesByRiskLevelFrequencyInNumberComponentFactory = this.resolver.resolveComponentFactory(PassengerProfilesByRiskLevelFrequencyInNumberComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfilesByRiskLevelFrequencyInNumberComponentFactory);
        break;

      case 'PassengerProfilesByFrequencyOfEachCriteria':
        const passengerProfilesByFrequencyOfEachCriteriaComponentFactory = this.resolver.resolveComponentFactory(PassengerProfilesByFrequencyOfEachCriteriaComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfilesByFrequencyOfEachCriteriaComponentFactory);
        break;


      case 'PassengerProfilesByRiskSelectivityLevelInDetail':
        const passengerProfilesByRiskSelectivityLevelInDetailComponentFactory = this.resolver.resolveComponentFactory(PassengerProfilesByRiskSelectivityLevelInDetailComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfilesByRiskSelectivityLevelInDetailComponentFactory);
        break;


      case 'SummaryOfFrequencyRiskLevel':
        const summaryOfFrequencyRiskLevelComponentFactory = this.resolver.resolveComponentFactory(SummaryOfFrequencyRiskLevelComponent);
        this.componentRef = this.componentContainer.createComponent(summaryOfFrequencyRiskLevelComponentFactory);
        break;

      case 'PassengerProfileSummaryManuallyRerouted':
        const passengerProfileSummaryManuallyReroutedComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileSummaryManuallyReroutedComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileSummaryManuallyReroutedComponentFactory);
        break;

      case 'PassengerProfileSelectRiskSummaryRerouted':
        const passengerProfileSelectRiskSummaryReroutedComponentFactory = this.resolver.resolveComponentFactory(PassengerProfileSelectRiskSummaryReroutedComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfileSelectRiskSummaryReroutedComponentFactory);
        break;

      case 'PassengerProfilesReroutedRandomSelectionRiskLevel':
        const passengerProfilesReroutedRandomSelectionRiskLevelComponentFactory = this.resolver.resolveComponentFactory(PassengerProfilesReroutedRandomSelectionRiskLevelComponent);
        this.componentRef = this.componentContainer.createComponent(passengerProfilesReroutedRandomSelectionRiskLevelComponentFactory);
        break;

    }
  }


}
