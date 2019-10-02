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


  constructor(private appModelService: AppModelService) { }

  ngOnInit() {
    this.appModelService.getAll().subscribe(data => {
      this.appModels = data;
      for (let app of this.appModels) {
        for (event in app.eventSources) {
          this.eventSources.push(new EventSource(event, app.eventSources[event]));
        }
      }
    });
  }
}

class EventSource {
  constructor(
    public eventName: any,
    public eventProperties: Array<string>
  ){}
}
