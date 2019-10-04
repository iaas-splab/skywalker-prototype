import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {TemplateService} from "../template.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-retrieval',
  templateUrl: './retrieval.component.html',
  styleUrls: ['./retrieval.component.css']
})


export class RetrievalComponent implements OnInit {

  constructor(
    private extractionService: TemplateService,
    private snackBar: MatSnackBar,
    public router: Router
  ) {}

  ngOnInit() {}

  extractFunction(form: NgForm) {
    this.extractionService.crawlPlatform(form).subscribe(response => {
      console.log(response);
      this.openSnackBar('Added deployment model template', 'close', 2000);
      this.router.navigate(['/template-list']);
    });
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    });
  }
}
