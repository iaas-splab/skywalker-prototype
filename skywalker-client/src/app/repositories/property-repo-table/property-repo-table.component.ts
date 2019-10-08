import { Component, OnInit } from '@angular/core';
import {MappingRepoService} from "../mapping-repo.service";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-property-repo-table',
  templateUrl: './property-repo-table.component.html',
  styleUrls: ['./property-repo-table.component.css']
})
export class PropertyRepoTableComponent implements OnInit {

  private dataSource;
  displayedColumns: string[] = ['genericResourceId', 'genericServiceProperty', 'genericServicePropertyMap'];

  constructor(
    private mappingRepoService: MappingRepoService,
    private snackBarService: SnackbarService
  ) { }

  ngOnInit() {
    this.mappingRepoService.getAllPropertyMappings().subscribe(data => {
      console.log(data);
      this.dataSource = data;
    });
  }

  resetAll() {
    this.mappingRepoService.resetAllPropertyMappings().subscribe(data => {
      console.log(data);
      this.ngOnInit();
      this.snackBarService.openSnackBar("Reset table", 'close', 1000);

    })
  }

}
