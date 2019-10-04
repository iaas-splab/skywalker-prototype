import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { RetrievalComponent } from './retrieval/retrieval.component';

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
import { FileInputComponent } from './file-input/file-input.component';
import { TemplateListComponent } from './template-list/template-list.component';
import { MappingListComponent } from './mapping-list/mapping-list.component';
import { AddMappingComponent } from './add-mapping/add-mapping.component';
import { ModelMappingComponent } from './model-mapping/model-mapping.component';
import { AppModelListComponent } from './app-model-list/app-model-list.component';
import { ComparisonViewComponent } from './comparison-view/comparison-view.component';
import { ServiceRepoTableComponent } from './service-repo-table/service-repo-table.component';
import { AddServiceMappingComponent } from './add-service-mapping/add-service-mapping.component';
import { PropertyRepoTableComponent } from './property-repo-table/property-repo-table.component';
import { AddPropertyMappingComponent } from './add-property-mapping/add-property-mapping.component';
import { AddAppModelComponent } from './add-app-model/add-app-model.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    RetrievalComponent,
    FileInputComponent,
    TemplateListComponent,
    MappingListComponent,
    AddMappingComponent,
    ModelMappingComponent,
    AppModelListComponent,
    ComparisonViewComponent,
    ServiceRepoTableComponent,
    AddServiceMappingComponent,
    PropertyRepoTableComponent,
    AddPropertyMappingComponent,
    AddAppModelComponent
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
