import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component';
import { RetrievalComponent } from './retrieval/retrieval.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatCardModule, MatButtonModule, MatInputModule, MatExpansionModule } from "@angular/material";
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    RetrievalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    FormsModule,
    MatButtonModule,
    MatInputModule,
    MatExpansionModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
