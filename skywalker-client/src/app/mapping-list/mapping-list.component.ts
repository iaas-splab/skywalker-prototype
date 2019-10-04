import { Component, OnInit } from '@angular/core';
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";
import {MatSnackBar} from "@angular/material";
import {Router} from "@angular/router";

@Component({
  selector: 'app-mapping-list',
  templateUrl: './mapping-list.component.html',
  styleUrls: ['./mapping-list.component.css']
})
export class MappingListComponent implements OnInit {
  mappingModules: Array<MappingModule>;

  constructor(
    private mappingService: MappingService,
    private snackBar: MatSnackBar,
    public router: Router
  ) { }

  ngOnInit() {
    this.mappingService.getAll().subscribe(data => {
      this.mappingModules = data;
    });
  }

  resetAll() {
    this.mappingService.resetAll().subscribe(data => {
      console.log(data);
      this.openSnackBar("Reset all deployment model templates.",
        'close',
        1000);
      this.router.navigate(['/mapping-list']);
    });
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    });
  }

}
