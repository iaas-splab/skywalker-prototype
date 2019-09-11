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
  MatTooltipModule
} from "@angular/material";
import { FormsModule } from '@angular/forms';
import { FileInputComponent } from './file-input/file-input.component';
import { TemplateListComponent } from './template-list/template-list.component';
import { MappingListComponent } from './mapping-list/mapping-list.component';
import { AddMappingComponent } from './add-mapping/add-mapping.component';
import { ModelMappingComponent } from './model-mapping/model-mapping.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    RetrievalComponent,
    FileInputComponent,
    TemplateListComponent,
    MappingListComponent,
    AddMappingComponent,
    ModelMappingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    FormsModule,
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
    MatTooltipModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
