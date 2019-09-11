import { Component, OnInit } from '@angular/core';
import {TemplateService} from "../services/template.service";
import {Template} from "../models/Template";
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";
import {MappingConfiguration} from "../models/MappingConfiguration";

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

  constructor(
    private templateService: TemplateService,
    private mappingService: MappingService
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
    console.log(this.selectedTemplate);
    console.log(this.selectedModule);
    this.mappingConfig = new MappingConfiguration(this.selectedTemplate.name, this.selectedModule.name);
    console.log("MAPPING CONFIG: " + this.mappingConfig.templateName);
    this.mappingService.passMappingConfiguration(this.mappingConfig).subscribe(data => {
      console.log(data);
    });
  }

}
