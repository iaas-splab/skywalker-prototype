import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./retrieval/retrieval.component";

const routes: Routes = [
  {
    path: 'retrieval',
    component: RetrievalComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
