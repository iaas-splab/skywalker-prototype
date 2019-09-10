import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./retrieval/retrieval.component";
import {FileInputComponent} from "./file-input/file-input.component";
import {TemplateListComponent} from "./template-list/template-list.component";

const routes: Routes = [
  {
    path: 'template-list',
    component: TemplateListComponent
  },
  {
    path: 'view2',
    component: FileInputComponent
  },
  {
    path: 'template-edit',
    component: RetrievalComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
