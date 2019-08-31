import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Template} from "../models/Template";

@Injectable({
  providedIn: 'root'
})
export class AnalyzeService {

  private baseUrl: string = 'http://localhost:8080/';
  private analysisApi: string = this.baseUrl + 'analyze/';

  constructor(private http: HttpClient) { }

  submitTemplate(template: any): Observable<any> {
    return this.http.post(this.analysisApi, template)
  }
}
