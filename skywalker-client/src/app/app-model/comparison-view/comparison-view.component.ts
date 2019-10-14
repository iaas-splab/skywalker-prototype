import { Component, OnInit } from '@angular/core';
import { CoverageModel } from "../CoverageModel";
import { DataService } from "../../services/data.service";
import {AppModelService} from "../app-model.service";

@Component({
  selector: 'app-comparison-view',
  templateUrl: './comparison-view.component.html',
  styleUrls: ['./comparison-view.component.css']
})
export class ComparisonViewComponent implements OnInit {

  private appCoverageModel: CoverageModel;

  constructor(
    private data: DataService,
    private appModelService: AppModelService
  ) { }

  ngOnInit() {
    this.data.currentCoverageModel.subscribe(coverageModel => {
      this.appCoverageModel = coverageModel;
      console.log(this.appCoverageModel);
    });
  }

  formatCoverageScore(eventCoverage: any) {
    return ((eventCoverage * 100).toString().slice(0,3) + "%").replace(/\.%/g,'%');
  }

  translateOriginalModel(coverageModel: CoverageModel) {
    this.appModelService.translate(coverageModel).subscribe(platformSpecificModel => {
      console.log(platformSpecificModel);
    });
  }

  discoverDataStructureSinceTypescriptIsntAnyBetterThanJavascript() {
    for (let eventSource in this.appCoverageModel.eventSourceCoverage) {
      console.log(this.appCoverageModel.eventSourceCoverage[eventSource]);
      console.log(eventSource); // schedule, http etc
      for (let propList of this.appCoverageModel.eventSourceCoverage[eventSource]) {
        for (let prop in propList) {
          console.log("-" + prop);
          console.log("--" + propList[prop]);
        }
      }
    }
  }
}
