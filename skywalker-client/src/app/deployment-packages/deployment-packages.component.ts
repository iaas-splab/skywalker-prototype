import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-deployment-packages',
  templateUrl: './deployment-packages.component.html',
  styleUrls: ['./deployment-packages.component.css']
})
export class DeploymentPackagesComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  resetAll() {
    this.extractionService.resetAll().subscribe(data => {
      console.log(data);
      this.openSnackBar("Reset all deployment model templates.",
        'close',
        1000);
      this.router.navigate(['/template-list']);
    });
  }

  openSnackBar(message: string, action: string, duration: number) {
    this.snackBar.open(message, action, {
      duration: duration,
    });
  }
}
