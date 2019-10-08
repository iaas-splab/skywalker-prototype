import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RetrievalComponent} from "./deployment-model-templates/retrieval/retrieval.component";
import {TemplateListComponent} from "./deployment-model-templates/template-list/template-list.component";
import {MappingListComponent} from "./mapping-modules/mapping-list/mapping-list.component";
import {AddMappingComponent} from "./mapping-modules/add-mapping/add-mapping.component";
import {AppModelListComponent} from "./app-model/app-model-list/app-model-list.component";
import {ComparisonViewComponent} from "./app-model/comparison-view/comparison-view.component";
import {ServiceRepoTableComponent} from "./repositories/service-repo-table/service-repo-table.component";
import {AddServiceMappingComponent} from "./repositories/add-service-mapping/add-service-mapping.component";
import {PropertyRepoTableComponent} from "./repositories/property-repo-table/property-repo-table.component";
import {AddPropertyMappingComponent} from "./repositories/add-property-mapping/add-property-mapping.component";
import {AddAppModelComponent} from "./app-model/add-app-model/add-app-model.component";
import {LandingPageComponent} from "./start-page/landing-page/landing-page.component";
import {DeploymentPackagesListComponent} from "./deployment-packages/deployment-packages-list/deployment-packages-list.component";
import {AddDeploymentPackageComponent} from "./deployment-packages/add-deployment-package/add-deployment-package.component";

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent
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
  },
  {
    path: 'app-deployment-packages-list',
    component: DeploymentPackagesListComponent
  },
  {
    path: 'app-deployment-packages-add',
    component: AddDeploymentPackageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
