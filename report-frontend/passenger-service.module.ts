import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PassengerRoutingModule} from './passenger-service-routing.module';
import { TranslateModule } from "@ngx-translate/core";
import {
  NgbAccordionModule,
  NgbAlertModule,
  NgbDatepickerModule
} from "@ng-bootstrap/ng-bootstrap";
import { SharedModule } from "../../shared/shared.module";
import { CmsModalModule } from "../../shared/cms-modal/cms-modal.module";
import { FormsModule } from "@angular/forms";
import { PassengerServiceViewerComponent } from './passenger-service-viewer/passenger-service-viewer.component';
import {
  PassengerProfileOffenceByTransportationNumberInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-offence-by-transportation-number-in-number/passenger-profile-offence-by-transportation-number-in-number.component";
import {
  PassengerProfileOffenceByTransportationNumberInDetailComponent
} from "@app/report-management/passenger-service/passenger-profile-offence-by-transportation-number-in-detail/passenger-profile-offence-by-transportation-number-in-detail.component";
import {
  PassengerProfileByTransportationNumberInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-by-transportation-number-in-number/passenger-profile-by-transportation-number-In-number.component";
import {
  PassengerProfileByTransportationNumberInDetailComponent
} from "@app/report-management/passenger-service/passenger-profile-by-transportation-number-in-detail/passenger-profile-by-transportation-number-in-detail.component";
import {
  PassengerProfileByTransportationDateInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-by-transportation-date-in-number/passenger-profile-by-transportation-date-in-number.component";
import {
  PassengerProfileByTransportationDateInDetailComponent
} from "@app/report-management/passenger-service/passenger-profile-by-transportation-date-in-detail/passenger-profile-by-transportation-date-in-detail.component";
import {
  PassengerProfileOffenceByTransportationDateInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-offence-by-transportation-date-in-number/passenger-profile-offence-by-transportation-date-in-number.component";
import {
  PassengerProfileOffenceByDepartureCountryDateInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-offence-by-departure-country-date-in-number/passenger-profile-offence-by-departure-country-date-in-number.component";
import {
  PassengerProfileOffenceByRandomSelectionCriteriaInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-offence-by-random-selection-criteria-in-number/passenger-profile-offence-by-random-selection-criteria-in-number.component";
import {
  PassengerProfileOffenceByRandomSelectionCriteriaInDetailComponent
} from "@app/report-management/passenger-service/passenger-profile-offence-by-random-selection-criteria-in-detail/passenger-profile-offence-by-random-selection-criteria-in-detail.component";
import {
  PassengerProfileByRiskCriterialInDetailComponent
} from "@app/report-management/passenger-service/passenger-profile-by-risk-criterial-in-detail/passenger-profile-by-risk-criterial-in-detail.component";
import {
  PassengerProfileByRiskCriterialFrequencyInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-by-risk-criterial-frequency-in-number/passenger-profile-by-risk-criterial-frequency-in-number.component";
import {
  PassengerProfileByRiskCriterialSelectivityInDetailComponent
} from "@app/report-management/passenger-service/passenger-profile-by-risk-criterial-selectivity-in-detail/passenger-profile-by-risk-criterial-selectivity-in-detail.component";
import {
  PassengerProfilesByRiskLevelFrequencyInNumberComponent
} from "@app/report-management/passenger-service/passenger-profile-by-risk-level-frequency-in-number/passenger-profile-by-risk-level-frequency-in-number.component";

import {CountryService} from "@look-up/country/services/country.service";
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


@NgModule({
  declarations: [
    PassengerProfileByTransportationNumberInNumberComponent,
    PassengerProfileByTransportationNumberInDetailComponent ,
    PassengerProfileByTransportationDateInNumberComponent ,
    PassengerProfileByTransportationDateInDetailComponent,
    PassengerProfileOffenceByTransportationNumberInNumberComponent ,
    PassengerProfileOffenceByTransportationNumberInDetailComponent ,
    PassengerProfileOffenceByTransportationDateInNumberComponent ,
    PassengerProfileOffenceByDepartureCountryDateInNumberComponent ,
    PassengerProfileOffenceByRandomSelectionCriteriaInNumberComponent,
    PassengerProfileOffenceByRandomSelectionCriteriaInDetailComponent ,
    PassengerProfileByRiskCriterialInDetailComponent ,
    PassengerProfileByRiskCriterialFrequencyInNumberComponent ,
    PassengerProfileByRiskCriterialSelectivityInDetailComponent,
    PassengerProfilesByRiskLevelFrequencyInNumberComponent ,
    PassengerServiceViewerComponent,
    PassengerProfilesByFrequencyOfEachCriteriaComponent,
    PassengerProfilesByRiskSelectivityLevelInDetailComponent,
    SummaryOfFrequencyRiskLevelComponent,
    PassengerProfileSummaryManuallyReroutedComponent,
    PassengerProfileSelectRiskSummaryReroutedComponent,
    PassengerProfilesReroutedRandomSelectionRiskLevelComponent,
  ],
  imports: [
    CommonModule,
    PassengerRoutingModule,
    TranslateModule,
    NgbAlertModule,
    NgbAccordionModule,
    NgbDatepickerModule,
    SharedModule,
    CmsModalModule,
    FormsModule,
  ],
  providers: [
    CountryService,
  ],
})


export class PassengerServiceModule { }
