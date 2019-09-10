import { Component, OnInit } from '@angular/core';
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-mapping-list',
  templateUrl: './mapping-list.component.html',
  styleUrls: ['./mapping-list.component.css']
})
export class MappingListComponent implements OnInit {
  mappingModules: Array<MappingModule>;

  constructor(
    private mappingService: MappingService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.mappingService.getAll().subscribe(data => {
      this.mappingModules = data;
    });
    this.openSnackBar('Please reload the page to refresh the list if the most recent changes are not contained, yet.', 'close', 2000);
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    });
  }

}
