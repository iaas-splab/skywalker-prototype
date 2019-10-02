import { Component, OnInit } from '@angular/core';
import {AppModelService} from "../services/app-model.service";
import {AppModel} from "../models/AppModel";

@Component({
  selector: 'app-app-model-list',
  templateUrl: './app-model-list.component.html',
  styleUrls: ['./app-model-list.component.css']
})

export class AppModelListComponent implements OnInit {
  appModels: Array<AppModel>;
  eventSources: Array<EventSource> = new Array<EventSource>();
  invokedServices: Array<InvokedService> = new Array<InvokedService>();
  functions: Array<HostedFunction> = new Array<HostedFunction>();

  constructor(private appModelService: AppModelService) { }

  ngOnInit() {
    this.appModelService.getAll().subscribe(data => {
      this.appModels = data;
      for (let app of this.appModels) {
        for (event in app.eventSources) {
          this.eventSources.push(new EventSource(event, app.eventSources[event]));
        }
        let service;
        for (service in app.invokedServices) {
          this.invokedServices.push(new InvokedService(service, app.invokedServices[service]));
        }
        let hostedFunction;
        for (hostedFunction in app.functions) {
          this.functions.push(new HostedFunction(hostedFunction, app.functions[hostedFunction]));
        }
      }
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
