import { Component, OnInit } from '@angular/core';
import {Form, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MappingRepoService} from "../mapping-repo.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-add-property-mapping',
  templateUrl: './add-property-mapping.component.html',
  styleUrls: ['./add-property-mapping.component.css']
})
export class AddPropertyMappingComponent implements OnInit {
  formGroup : FormGroup;
  form: FormArray;

  constructor(
    private _formBuilder: FormBuilder,
    private mappingRepoService: MappingRepoService,
    private snackBar: MatSnackBar,
    public router: Router
  ) { }

  ngOnInit() {
    this.formGroup = this._formBuilder.group({
      form : this._formBuilder.array([this.init()])
    });
    this.addItem();
  }

  init() {
    return this._formBuilder.group({
      cont: new FormControl('', [Validators.required]),
    })
  }

  addItem(){
    this.form = this.formGroup.get('form') as FormArray;
    this.form.push(this.init());
  }

  add(form: Form, formArray: any) {
    let dict: {[key: string]: Array<string>} = {};
    for (let buffer in formArray) {
      for (let mapping in formArray[buffer]) {
        dict[formArray[buffer][mapping]["cont"].toString().trimLeft().split(":")[0]]
          = formArray[buffer][mapping]["cont"].toString().trimLeft().split(":")[1].trimLeft().split(",");
      }
    }
    let prop: GenericServiceProperty = new GenericServiceProperty(form["genericResourceId"].toString(), dict);
    this.mappingRepoService.addPropertyMapping(prop).subscribe(data => {
      console.log(data);
      this.openSnackBar("Added new entry ", 'close', 2000);
      this.router.navigate(['/app-property-repo-table']);
    });

  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    }).afterDismissed().subscribe(() => {
    });
  }
}

class GenericServiceProperty {
  constructor(
    public genericResourceId: string,
    public genericServicePropertyMap: {[key: string]: Array<string>}
  ){ }
}

