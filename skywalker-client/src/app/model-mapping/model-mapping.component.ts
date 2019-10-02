import { Component, OnInit } from '@angular/core';
import {TemplateService} from "../services/template.service";
import {Template} from "../models/Template";
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";
import {MappingConfiguration} from "../models/MappingConfiguration";
import {MatSnackBar} from "@angular/material";
import {promise} from "selenium-webdriver";
import Promise = promise.Promise;

@Component({
  selector: 'app-model-mapping',
  templateUrl: './model-mapping.component.html',
  styleUrls: ['./model-mapping.component.css']
})
export class ModelMappingComponent implements OnInit {
  templates: Array<Template>;
  mappingModules: Array<MappingModule>;
  selectedModule: MappingModule;
  selectedTemplate: Template;
  mappingConfig: MappingConfiguration;
  applicationName: string;
  mode = "";

  constructor(
    private templateService: TemplateService,
    private mappingService: MappingService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.templateService.getAll().subscribe(data => {
      this.templates = data;
      console.log(this.templates);
    });
    this.mappingService.getAll().subscribe(data => {
      this.mappingModules = data;
    });
  }

  onStart() {
    this.mappingConfig = new MappingConfiguration(this.selectedTemplate.name, this.selectedModule.name, this.applicationName);
    this.mode = "indeterminate";
    setTimeout(() => {
      this.openSnackBar("Uploaded Deployment Model", 'close', 2000);
    }, 1000);
    this.mappingService.passMappingConfiguration(this.mappingConfig).subscribe(data => {
      console.log(data);
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
