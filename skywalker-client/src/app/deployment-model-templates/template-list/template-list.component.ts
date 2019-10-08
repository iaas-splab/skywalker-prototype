import { Component, OnInit } from '@angular/core';
import {TemplateService} from "../template.service";
import {Template} from "../Template";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-template-list',
  templateUrl: './template-list.component.html',
  styleUrls: ['./template-list.component.css']
})
export class TemplateListComponent implements OnInit {
  templates: Array<Template>;

  constructor(
    private templateService: TemplateService,
    private extractionService: TemplateService,
    private snackBarService: SnackbarService,
  ) { }

  ngOnInit() {
    this.templateService.getAll().subscribe(data => {
      this.templates = data;
    });
  }

  resetAll() {
    this.extractionService.resetAll().subscribe(data => {
      console.log(data);
      this.ngOnInit();
      this.snackBarService.openSnackBar("Reset all deployment model templates.",
        'close',
        1000);
    });
  }
}
