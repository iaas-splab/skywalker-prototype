import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {TemplateService} from "../template.service";
import {Router} from "@angular/router";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-retrieval',
  templateUrl: './retrieval.component.html',
  styleUrls: ['./retrieval.component.css']
})


export class RetrievalComponent implements OnInit {

  constructor(
    private extractionService: TemplateService,
    private snackBarService: SnackbarService,
    public router: Router
  ) {}

  ngOnInit() {}

  extractFunction(form: NgForm) {
    this.extractionService.crawlPlatform(form).subscribe(response => {
      console.log(response);
      this.snackBarService.openSnackBar('Added deployment model template', 'close', 2000);
      this.router.navigate(['/template-list']);
    });
  }
}
