import { Component, OnInit } from '@angular/core';
import {MappingRepoService} from "../services/mapping-repo.service";

@Component({
  selector: 'app-property-repo-table',
  templateUrl: './property-repo-table.component.html',
  styleUrls: ['./property-repo-table.component.css']
})
export class PropertyRepoTableComponent implements OnInit {

  private dataSource;
  displayedColumns: string[] = ['genericResourceId', 'genericServiceProperty', 'genericServicePropertyMap'];

  constructor(private mappingRepoService: MappingRepoService) { }

  ngOnInit() {
    this.mappingRepoService.getAllPropertyMappings().subscribe(data => {
      console.log(data);
      this.dataSource = data;
      let entry;
      for (entry of data) {
        // console.log(entry);
        // console.log(entry["genericResourceId"]);
      }
    });
  }

  // formatServicePropertyMap(element) {
  //   let genericServicePropertyMap: {[key: string]: Array<string>} = this.dataSource[element].getValue();
  //   let s: string = "";
  //   console.log("prop map: " + genericServicePropertyMap);
  //   let property: {[key: string]: Array<string>};
  //   for (property of genericServicePropertyMap) {
  //     console.log(property);
  //     console.log("val: " + propertyMap[property].getValue());
  //     s += propertyMap[property].getKey() + ": " + propertyMap[property].getValue().toString() + "\n";
  //   }
  //   return s;
  // }

  resetAll() {
    this.mappingRepoService.resetAllPropertyMappings().subscribe(data => {
      console.log(data);
      // this.router.navigate(['/app-service-repo-table']);
    })
  }

}
