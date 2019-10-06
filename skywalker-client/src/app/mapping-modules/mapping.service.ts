import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {MappingModule} from "./MappingModule";

@Injectable({
  providedIn: 'root'
})
export class MappingService {

  private baseUrl: string = 'http://localhost:8080/';
  private mappingApi: string = this.baseUrl + 'mapping';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.mappingApi + '/');
  }

  upload(module: MappingModule): Observable<any> {
    return this.http.put(this.mappingApi + '/', module);
  }

  resetAll(): Observable<any> {
    return this.http.delete(this.mappingApi + '/');
  }
}
