import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './start-page/navigation/navigation.component';
import { RetrievalComponent } from './deployment-model-templates/retrieval/retrieval.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {
  MatCardModule,
  MatButtonModule,
  MatInputModule,
  MatExpansionModule,
  MatGridListModule,
  MatSelectModule,
  MatOptionModule,
  MatSnackBarModule,
  MatListModule,
  MatToolbarModule,
  MatTooltipModule,
  MatProgressBarModule,
  MatDialogModule,
  MatTableModule,
  MatStepperModule
} from "@angular/material";
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { FileInputComponent } from './deployment-model-templates/file-input/file-input.component';
import { TemplateListComponent } from './deployment-model-templates/template-list/template-list.component';
import { MappingListComponent } from './mapping-modules/mapping-list/mapping-list.component';
import { AddMappingComponent } from './mapping-modules/add-mapping/add-mapping.component';
import { AppModelListComponent } from './app-model/app-model-list/app-model-list.component';
import { ComparisonViewComponent } from './app-model/comparison-view/comparison-view.component';
import { ServiceRepoTableComponent } from './repositories/service-repo-table/service-repo-table.component';
import { AddServiceMappingComponent } from './repositories/add-service-mapping/add-service-mapping.component';
import { PropertyRepoTableComponent } from './repositories/property-repo-table/property-repo-table.component';
import { AddPropertyMappingComponent } from './repositories/add-property-mapping/add-property-mapping.component';
import { AddAppModelComponent } from './app-model/add-app-model/add-app-model.component';
import { LandingPageComponent } from './start-page/landing-page/landing-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    RetrievalComponent,
    FileInputComponent,
    TemplateListComponent,
    MappingListComponent,
    AddMappingComponent,
    AppModelListComponent,
    ComparisonViewComponent,
    ServiceRepoTableComponent,
    AddServiceMappingComponent,
    PropertyRepoTableComponent,
    AddPropertyMappingComponent,
    AddAppModelComponent,
    LandingPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatInputModule,
    MatExpansionModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatSelectModule,
    MatOptionModule,
    HttpClientModule,
    MatSnackBarModule,
    MatListModule,
    MatToolbarModule,
    MatTooltipModule,
    MatProgressBarModule,
    MatDialogModule,
    MatTableModule,
    MatStepperModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
