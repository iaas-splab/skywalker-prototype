import { Component, OnInit } from '@angular/core';
import {TemplateService} from "../services/template.service";
import {Template} from "../models/Template";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-template-list',
  templateUrl: './template-list.component.html',
  styleUrls: ['./template-list.component.css']
})
export class TemplateListComponent implements OnInit {
  templates: Array<Template>;

  constructor(
    private templateService: TemplateService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.templateService.getAll().subscribe(data => {
      this.templates = data;
    });
  }
}
