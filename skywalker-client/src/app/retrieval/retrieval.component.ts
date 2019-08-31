import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {ExtractionService} from "../services/extraction.service";

@Component({
  selector: 'app-retrieval',
  templateUrl: './retrieval.component.html',
  styleUrls: ['./retrieval.component.css']
})


export class RetrievalComponent implements OnInit {

  constructor(
    private extractionService: ExtractionService,
  ) {}

  ngOnInit() {}

  extractFunction(form: NgForm) {
    this.extractionService.extract(form).subscribe(response => {
      console.log(response);
    });
  }
}
