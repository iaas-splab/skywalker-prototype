import { Component, OnInit } from '@angular/core';
import {TemplateService} from "../services/template.service";
import {Template} from "../models/Template";
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";

@Component({
  selector: 'app-model-mapping',
  templateUrl: './model-mapping.component.html',
  styleUrls: ['./model-mapping.component.css']
})
export class ModelMappingComponent implements OnInit {
  templates: Array<Template>;
  mappingModules: Array<MappingModule>;

  constructor(
    private templateService: TemplateService,
    private mappingService: MappingService
  ) { }

  ngOnInit() {
    this.templateService.getAll().subscribe(data => {
      this.templates = data;
    });
    this.mappingService.getAll().subscribe(data => {
      this.mappingModules = data;
    });
  }

  onStart() {
    console.log("started");
  }

}
