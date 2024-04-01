import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {
  PassengerServiceViewerComponent
} from "@app/report-management/passenger-service/passenger-service-viewer/passenger-service-viewer.component";




const routes: Routes = [
  {
    path: '',
    component: PassengerServiceViewerComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PassengerRoutingModule { }
