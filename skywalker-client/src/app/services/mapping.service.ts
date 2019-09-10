import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {Template} from "../models/Template";
import {MappingModule} from "../models/MappingModule";

@Injectable({
  providedIn: 'root'
})
export class MappingService {

  private baseUrl: string = 'http://localhost:8080/';
  private mappingApi: string = this.baseUrl + 'mapmodules';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.mappingApi + '/');
  }

  upload(module: MappingModule): Observable<any> {
    return this.http.post(this.mappingApi + '/' +  'upload/', module);
  }
}
