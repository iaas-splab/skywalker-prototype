import { Component, OnInit } from '@angular/core';
import { CoverageModel } from "../CoverageModel";
import { DataService } from "../../services/data.service";

@Component({
  selector: 'app-comparison-view',
  templateUrl: './comparison-view.component.html',
  styleUrls: ['./comparison-view.component.css']
})
export class ComparisonViewComponent implements OnInit {

  private appCoverageModel: CoverageModel;

  constructor(private data: DataService) { }

  ngOnInit() {
    this.data.currentCoverageModel.subscribe(coverageModel => this.appCoverageModel = coverageModel);
    // this.discoverDataStructureSinceTypescriptIsntAnyBetterThanJavascript();
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
