import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./retrieval/retrieval.component";
import {TemplateListComponent} from "./template-list/template-list.component";
import {MappingListComponent} from "./mapping-list/mapping-list.component";
import {AddMappingComponent} from "./add-mapping/add-mapping.component";
import {ModelMappingComponent} from "./model-mapping/model-mapping.component";
import {AppModelListComponent} from "./app-model-list/app-model-list.component";
import {ComparisonViewComponent} from "./comparison-view/comparison-view.component";
import {ServiceRepoTableComponent} from "./service-repo-table/service-repo-table.component";
import {AddServiceMappingComponent} from "./add-service-mapping/add-service-mapping.component";
import {PropertyRepoTableComponent} from "./property-repo-table/property-repo-table.component";
import {AddPropertyMappingComponent} from "./add-property-mapping/add-property-mapping.component";
import {AddAppModelComponent} from "./add-app-model/add-app-model.component";

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
  },
  {
    path: 'app-service-repo-table',
    component: ServiceRepoTableComponent
  },
  {
    path: 'app-add-service-mapping',
    component: AddServiceMappingComponent
  },
  {
    path: 'app-property-repo-table',
    component: PropertyRepoTableComponent
  },
  {
    path: 'app-add-property-mapping',
    component: AddPropertyMappingComponent
  },
  {
    path: 'app-add-app-model-mapping',
    component: AddAppModelComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
