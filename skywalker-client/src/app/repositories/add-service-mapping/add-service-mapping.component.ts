import { Component, OnInit } from '@angular/core';
import {MappingRepoService} from "../mapping-repo.service";
import {Router} from "@angular/router";
import {SnackbarService} from "../../services/snackbar.service";

@Component({
  selector: 'app-add-service-mapping',
  templateUrl: './add-service-mapping.component.html',
  styleUrls: ['./add-service-mapping.component.css']
})
export class AddServiceMappingComponent implements OnInit {

  constructor(
    private mappingRepoService: MappingRepoService,
    private snackBarService: SnackbarService,
    public router: Router
  ) { }

  ngOnInit() {
  }

  add(form: any) {
    let eventSourceMapping: EventSourceMapping = new EventSourceMapping(
      form.genericResourceId,
      form.provider,
      form.providerResourceId,
      form.serviceProperties.trimLeft().split(",")
    );
    this.mappingRepoService.addServiceMapping(eventSourceMapping).subscribe(response => {
      console.log(response);
      this.snackBarService.openSnackBar('Added service mapping entry', 'close', 2000);
      this.router.navigate(['/app-service-repo-table']);
    });
  }
}

class EventSourceMapping {
  constructor(
    public genericResourceId: string,
    public provider: string,
    public providerResourceId: string,
    public serviceProperties: Array<string>
  ){ }
}
