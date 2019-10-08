import { Component, OnInit } from '@angular/core';
import {MappingRepoService} from "../mapping-repo.service";

@Component({
  selector: 'app-service-repo-table',
  templateUrl: './service-repo-table.component.html',
  styleUrls: ['./service-repo-table.component.css']
})
export class ServiceRepoTableComponent implements OnInit {

  private dataSource;
  displayedColumns: string[] = ['id', 'genericResourceId', 'provider', 'providerResourceId', 'serviceProperties'];

  constructor(private mappingRepoService: MappingRepoService) { }

  ngOnInit() {
    this.mappingRepoService.getAllServiceMappings().subscribe(data => {
      this.dataSource = data;
    });
  }

  resetAll() {
    this.mappingRepoService.resetAllServiceMappings().subscribe(data => {
      console.log(data);
      this.ngOnInit();
    })
  }

}
