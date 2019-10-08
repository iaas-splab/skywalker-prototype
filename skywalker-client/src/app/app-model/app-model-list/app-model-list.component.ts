import {Component, OnInit} from '@angular/core';
import {AppModelService} from "../app-model.service";
import {AppModel} from "../AppModel";
import {CoverageModel} from "../CoverageModel";
import {Router} from "@angular/router";
import {DataService} from "../../services/data.service";
import {CoverageEvaluationBundle} from "../CoverageEvaluationBundle";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-app-model-list',
  templateUrl: './app-model-list.component.html',
  styleUrls: ['./app-model-list.component.css']
})

export class AppModelListComponent implements OnInit {
  appModels: Array<AppModel>;
  appEventSources: {[key: string]: any} = {};
  appInvokedServices: {[key: string]: any} = {};
  appFunctions: {[key: string]: any} = {};
  appCoverageModel: CoverageModel;

  constructor(
    private appModelService: AppModelService,
    private snackBarService: SnackbarService,
    private data: DataService,
    public router: Router
  ) { }

  ngOnInit() {
    this.appModelService.getAll().subscribe(data => {
      this.parseDataBodyForPrettyNg(data);
    });
  }

  parseDataBodyForPrettyNg(data: any) {
    this.appModels = data;
    for (let app of this.appModels) {
      let eventSources = new Array<EventSource>();
      let invokedServices = new Array<InvokedService>();
      let functions = new Array<HostedFunction>();

      let event;
      for (event in app.eventSources) {eventSources.push(new EventSource(event, app.eventSources[event]));}
      this.appEventSources[app.id] = eventSources;

      let service;
      for (service in app.invokedServices) {invokedServices.push(new InvokedService(service, app.invokedServices[service]));}
      this.appInvokedServices[app.id] = invokedServices;

      let hostedFunction;
      for (hostedFunction in app.functions) {functions.push(new HostedFunction(hostedFunction, app.functions[hostedFunction]));}
      this.appFunctions[app.id] = functions;
    }
  }

  resetAll() {
    this.appModelService.resetAll().subscribe(data => {
      console.log(data);
      this.ngOnInit();
      this.snackBarService.openSnackBar("Deleted all application models. Please refresh the page.",
        'close',
        1000);
    });
  }

  evaluateWithPlatformCandidate(appModel: AppModel, targetPlatform: any) {
    let bundle = new CoverageEvaluationBundle(appModel, targetPlatform);
    this.appModelService.evaluatePortability(bundle).subscribe(data => {
      this.appCoverageModel = data;
      this.data.changeCoverageModel(this.appCoverageModel);
      this.router.navigate(['app-comparison-view']);
    });
  }
}

class EventSource {
  constructor(
    public name: any,
    public properties: Array<any>
  ){}
}

class InvokedService {
  constructor(
    public name: any,
    public properties: Array<any>
  ){}
}

class HostedFunction {
  constructor(
    public name: any,
    public config: Array<any>
  ){}
}
