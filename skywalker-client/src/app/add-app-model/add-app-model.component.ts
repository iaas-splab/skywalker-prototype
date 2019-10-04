import { Component, OnInit } from '@angular/core';
import {TemplateService} from "../services/template.service";
import {Template} from "../models/Template";
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";
import {MappingConfiguration} from "../models/MappingConfiguration";
import {MatSnackBar} from "@angular/material";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-app-model',
  templateUrl: './add-app-model.component.html',
  styleUrls: ['./add-app-model.component.css']
})
export class AddAppModelComponent implements OnInit {
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
    private snackBar: MatSnackBar,
    public router: Router
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
      this.openSnackBar("Generated Deployment Model for " + this.applicationName, 'close', 2000);
      this.router.navigate(['/app-model-list']);
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
