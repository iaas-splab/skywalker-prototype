import { Component, OnInit } from '@angular/core';
import {MappingModule} from "..//MappingModule";
import {MappingService} from "..//mapping.service";
import {Router} from "@angular/router";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-mapping-list',
  templateUrl: './mapping-list.component.html',
  styleUrls: ['./mapping-list.component.css']
})
export class MappingListComponent implements OnInit {
  mappingModules: Array<MappingModule>;

  constructor(
    private mappingService: MappingService,
    private snackBarService: SnackbarService,
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
      this.ngOnInit();
      this.snackBarService.openSnackBar("Reset all deployment model templates.",
        'close',
        1000);
    });
  }
}
