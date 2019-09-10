import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./retrieval/retrieval.component";
import {FileInputComponent} from "./file-input/file-input.component";
import {TemplateListComponent} from "./template-list/template-list.component";
import {MappingListComponent} from "./mapping-list/mapping-list.component";
import {AddMappingComponent} from "./add-mapping/add-mapping.component";

const routes: Routes = [
  {
    path: 'template-list',
    component: TemplateListComponent
  },
  {
    path: 'mapping-list',
    component: MappingListComponent
  },
  {
    path: 'template-edit',
    component: RetrievalComponent
  },
  {
    path: 'mapping-add',
    component: AddMappingComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
