import { Injectable } from '@angular/core';
import {MappingModule} from "../models/MappingModule";
import {Observable} from "rxjs/index";
import {MappingConfiguration} from "../models/MappingConfiguration";
import {HttpClient} from "@angular/common/http";
import {AppModel} from "../models/AppModel";
import {CoverageEvaluationBundle} from "../models/CoverageEvaluationBundle";

@Injectable({
  providedIn: 'root'
})
export class AppModelService {

  private baseUrl: string = 'http://localhost:8080/';
  private mappingApi: string = this.baseUrl + 'models';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.mappingApi + '/');
  }

  resetAll(): Observable<any> {
    return this.http.delete(this.mappingApi + '/');
  }

  upload(module: MappingModule): Observable<any> {
    return this.http.post(this.mappingApi + '/' +  'upload/', module);
  }

  evalPortability(coverageEvaluationBundle: CoverageEvaluationBundle): Observable<any> {
    return this.http.post(this.mappingApi + '/', coverageEvaluationBundle);
  }

  passMappingConfiguration(mappingConfig: MappingConfiguration): Observable<any> {
    return this.http.post(this.mappingApi + '/' + 'generate/', mappingConfig);
  }
}
