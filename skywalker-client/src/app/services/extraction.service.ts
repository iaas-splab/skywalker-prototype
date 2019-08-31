import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Template} from "../models/Template";


@Injectable({
  providedIn: 'root'
})
export class ExtractionService {

  private baseUrl: string = 'http://localhost:8080/';
  private extractionApi: string = this.baseUrl + 'extract/';

  constructor(private http: HttpClient) { }

  extract(platformDeployment: any): Observable<any> {
    return this.http.post(this.extractionApi, platformDeployment);
  }

  analyze(template: Template): Observable<any> {
    return this.http.post(this.extractionApi + 'template/', template);
  }
}
