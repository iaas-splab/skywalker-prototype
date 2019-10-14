import { Injectable } from '@angular/core';
import {Observable} from "rxjs/index";
import {MappingConfiguration} from "./MappingConfiguration";
import {HttpClient} from "@angular/common/http";
import {CoverageEvaluationBundle} from "./CoverageEvaluationBundle";
import {CoverageModel} from "./CoverageModel";

@Injectable({
  providedIn: 'root'
})
export class AppModelService {

  private baseUrl: string = 'http://localhost:8080/';
  private appApi: string = this.baseUrl + 'app';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.appApi + '/');
  }

  resetAll(): Observable<any> {
    return this.http.delete(this.appApi + '/');
  }

  generate(mappingConfig: MappingConfiguration): Observable<any> {
    return this.http.put(this.appApi + '/', mappingConfig);
  }

  translate(coverageModel: CoverageModel): Observable<any> {
    return this.http.post(this.appApi + '/' + 'translation', coverageModel);
  }

  evaluatePortability(coverageEvaluationBundle: CoverageEvaluationBundle): Observable<any> {
    return this.http.post(this.appApi + '/', coverageEvaluationBundle);
  }
}
