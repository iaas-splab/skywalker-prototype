import { Injectable } from '@angular/core';
import {MappingModule} from "../mapping-modules/MappingModule";
import {Observable} from "rxjs/index";
import {MappingConfiguration} from "./MappingConfiguration";
import {HttpClient} from "@angular/common/http";
import {AppModel} from "./AppModel";
import {CoverageEvaluationBundle} from "./CoverageEvaluationBundle";

@Injectable({
  providedIn: 'root'
})
export class AppModelService {

  private baseUrl: string = 'http://localhost:8080/';
  private appApi: string = this.baseUrl + 'models';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.appApi + '/');
  }

  resetAll(): Observable<any> {
    return this.http.delete(this.appApi + '/');
  }

  generate(mappingConfig: MappingConfiguration): Observable<any> {
    return this.http.put(this.appApi + '/' +  'generate/', mappingConfig);
  }

  evalPortability(coverageEvaluationBundle: CoverageEvaluationBundle): Observable<any> {
    return this.http.post(this.appApi + '/', coverageEvaluationBundle);
  }

  passMappingConfiguration(mappingConfig: MappingConfiguration): Observable<any> {
    return this.http.post(this.appApi + '/' + 'generate/', mappingConfig);
  }
}
