import { Component, OnInit } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import {TemplateService} from "../template.service";
import {Template} from "../Template";

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
    const fileReader = new FileReader();
    fileReader.onload = (e) => {
      this.templateContent = (<string>fileReader.result);
      const template: Template = new Template(this.selectedFile.name, this.templateContent);
      this.extractionService.upload(template).subscribe(response => {
        console.log(response);
      });
    };
    fileReader.readAsText(this.selectedFile);
  }

  constructor(
    private http: HttpClient,
    private extractionService: TemplateService
  ) { }

  ngOnInit() {
  }

}



