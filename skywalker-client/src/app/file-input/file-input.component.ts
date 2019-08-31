import { Component, OnInit } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import {ExtractionService} from "../services/extraction.service";
import {Template} from "../models/Template";

@Component({
  selector: 'app-file-input',
  templateUrl: './file-input.component.html',
  styleUrls: ['./file-input.component.css']
})
export class FileInputComponent implements OnInit {
  selectedFile: File = null;
  templateContent: string = null;

  onFileSelected(event) {
    this.selectedFile = <File> event.target.files[0];
  }

  onUpload() {
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      this.templateContent = (<string>fileReader.result);
      let fileFormat = this.selectedFile.name.split('.').pop();
      let temp: Template = new Template(fileFormat, this.templateContent);
      console.log(temp);
      this.extractionService.analyze(temp).subscribe(response => {
        console.log(response);
      });
    };
    fileReader.readAsText(this.selectedFile);
    console.log("NAME: " + this.selectedFile.name);
  }

  constructor(
    private http: HttpClient,
    private extractionService: ExtractionService
  ) { }

  ngOnInit() {
  }

}



