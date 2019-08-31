import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./retrieval/retrieval.component";
import {FileInputComponent} from "./file-input/file-input.component";

const routes: Routes = [
  {
    path: 'retrieval',
    component: RetrievalComponent
  },
  {
    path: 'view2',
    component: FileInputComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
