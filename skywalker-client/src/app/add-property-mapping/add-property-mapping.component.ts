import { Component, OnInit } from '@angular/core';
import {Form, FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MappingRepoService} from "../services/mapping-repo.service";

@Component({
  selector: 'app-add-property-mapping',
  templateUrl: './add-property-mapping.component.html',
  styleUrls: ['./add-property-mapping.component.css']
})
export class AddPropertyMappingComponent implements OnInit {
  isLinear = false;
  formGroup : FormGroup;
  form: FormArray;

  constructor(
    private _formBuilder: FormBuilder,
    private mappingRepoService: MappingRepoService
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
    console.log("bin drin");
    let dict: {[key: string]: Array<string>} = {};
    for (let buffer in formArray) {
      for (let mapping in formArray[buffer]) {
        // console.log(formArray[buffer][mapping]["cont"].toString().trimLeft().split(":")[0]);
        // console.log(formArray[buffer][mapping]["cont"].toString().trimLeft().split(":")[1].trimLeft().split(","));
        dict[formArray[buffer][mapping]["cont"].toString().trimLeft().split(":")[0]]
          = formArray[buffer][mapping]["cont"].toString().trimLeft().split(":")[1].trimLeft().split(",");
      }
    }
    console.log(dict);
    let prop: GenericServiceProperty = new GenericServiceProperty(form["genericResourceId"].toString(), dict);
    this.mappingRepoService.addPropertyMapping(prop).subscribe(data => {
      // console.log(data);
    });
  }
}

class GenericServiceProperty {
  constructor(
    public genericResourceId: string,
    public genericServicePropertyMap: {[key: string]: Array<string>}
  ){ }
}

