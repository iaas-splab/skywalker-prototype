import { Injectable } from '@angular/core';
import { BehaviorSubject } from "rxjs/internal/BehaviorSubject";
import {CoverageModel} from "../models/CoverageModel";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private coverageModel = new BehaviorSubject<CoverageModel>(null);
  currentCoverageModel = this.coverageModel.asObservable();

  constructor() { }

  changeCoverageModel(coverageModel: CoverageModel) {
    this.coverageModel.next(coverageModel);
  }
}
