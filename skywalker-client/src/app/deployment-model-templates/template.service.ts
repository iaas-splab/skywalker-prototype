import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Template} from "./Template";


@Injectable({
  providedIn: 'root'
})
export class TemplateService {

  private baseUrl: string = 'http://localhost:8080/';
  private templateApi: string = this.baseUrl + 'templates';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.templateApi + '/');
  }

  crawlPlatform(platformDeployment: any): Observable<any> {
    return this.http.post(this.templateApi + '/' + 'crawlPlatform/', platformDeployment);
  }

  upload(template: Template): Observable<any> {
    return this.http.post(this.templateApi + '/' +  'upload/', template);
  }

  resetAll(): Observable<any> {
    return this.http.delete(this.templateApi + '/');
  }
}
