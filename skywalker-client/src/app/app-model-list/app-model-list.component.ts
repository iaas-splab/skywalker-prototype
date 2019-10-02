import { Component, OnInit } from '@angular/core';
import {AppModelService} from "../services/app-model.service";
import {AppModel} from "../models/AppModel";
import {MatSnackBar} from "@angular/material";

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

  constructor(
    private appModelService: AppModelService,
    private snackBar: MatSnackBar
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
      this.openSnackBar("Deleted all application models. Please refresh the page.",
        'close',
        1000);
    });
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    }).afterDismissed().subscribe(() => {
      this.mode = "";
    });
  }
}

class EventSource {
  constructor(
    public name: any,
    public properties: Array<string>
  ){}
}

class InvokedService {
  constructor(
    public name: any,
    public properties: Array<string>
  ){}
}

class HostedFunction {
  constructor(
    public name: any,
    public config: Array<string>
  ){}
}
