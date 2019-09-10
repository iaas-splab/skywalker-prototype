import { Component, OnInit } from '@angular/core';
import {MappingModule} from "../models/MappingModule";
import {MappingService} from "../services/mapping.service";

@Component({
  selector: 'app-mapping-list',
  templateUrl: './mapping-list.component.html',
  styleUrls: ['./mapping-list.component.css']
})
export class MappingListComponent implements OnInit {
  mappingModules: Array<MappingModule>;

  constructor(private mappingService: MappingService) { }

  ngOnInit() {
    this.mappingService.getAll().subscribe(data => {
      this.mappingModules = data;
    });
  }

}
