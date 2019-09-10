import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {TemplateService} from "../services/template.service";

@Component({
  selector: 'app-retrieval',
  templateUrl: './retrieval.component.html',
  styleUrls: ['./retrieval.component.css']
})


export class RetrievalComponent implements OnInit {

  constructor(
    private extractionService: TemplateService,
  ) {}

  ngOnInit() {}

  extractFunction(form: NgForm) {
    this.extractionService.crawlPlatform(form).subscribe(response => {
      console.log(response);
    });
  }
}
