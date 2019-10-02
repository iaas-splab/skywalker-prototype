import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./retrieval/retrieval.component";
import {FileInputComponent} from "./file-input/file-input.component";
import {TemplateListComponent} from "./template-list/template-list.component";
import {MappingListComponent} from "./mapping-list/mapping-list.component";
import {AddMappingComponent} from "./add-mapping/add-mapping.component";
import {ModelMappingComponent} from "./model-mapping/model-mapping.component";
import {AppModelListComponent} from "./app-model-list/app-model-list.component";
import {ComparisonViewComponent} from "./comparison-view/comparison-view.component";

const routes: Routes = [
  {
    path: '',
    component: ModelMappingComponent
  },
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
  },
  {
    path: 'app-model-list',
    component: AppModelListComponent
  },
  {
    path: 'app-comparison-view',
    component: ComparisonViewComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
