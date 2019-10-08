import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {Template} from "../deployment-model-templates/Template";
import {DeploymentPackage} from "./DeploymentPackage";

@Injectable({
  providedIn: 'root'
})
export class DeploymentPackageService {

  private baseUrl: string = 'http://localhost:8080/';
  private packageApi: string = this.baseUrl + 'packages';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.packageApi + '/');
  }

  crawlPlatform(platformDeployment: any): Observable<any> {
    return this.http.post(this.packageApi + '/' + 'crawl/', platformDeployment);
  }

  analyzeFunctions(deploymentPackage: DeploymentPackage): Observable<any> {
    return this.http.post(this.packageApi + '/' + 'functions/', deploymentPackage);
  }

  upload(template: Template): Observable<any> {
    return this.http.put(this.packageApi + '/', template);
  }

  resetAll(): Observable<any> {
    return this.http.delete(this.packageApi + '/');
  }
}
