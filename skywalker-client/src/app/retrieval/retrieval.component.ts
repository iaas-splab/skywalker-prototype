import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {ExtractionService} from "../services/extraction.service";
import {AnalyzeService} from "../services/analyze.service";

@Component({
  selector: 'app-retrieval',
  templateUrl: './retrieval.component.html',
  styleUrls: ['./retrieval.component.css']
})
export class RetrievalComponent implements OnInit {

  constructor(
    private extractionService: ExtractionService,
    private analyzeService: AnalyzeService
  ) {}

  ngOnInit() {}

  extractFunction(form: NgForm) {
    this.extractionService.extract(form).subscribe(response => {
      console.log(response);
    });
  }

  analyzeTemplate(form: NgForm) {
    this.analyzeService.submitTemplate(form).subscribe(response => {
      console.log(response);
    });
  }

}


/*
*
* RetrievalComponent.html:21 ERROR TypeError: Cannot read property 'extract' of undefined
    at RetrievalComponent.push../src/app/retrieval/retrieval.component.ts.RetrievalComponent.extractFunction (retrieval.component.ts:18)
    at Object.eval [as handleEvent] (RetrievalComponent.html:21)
    at handleEvent (core.js:21673)
    at callWithDebugContext (core.js:22767)
    at Object.debugHandleEvent [as handleEvent] (core.js:22470)
    at dispatchEvent (core.js:19122)
    at core.js:20612
    at SafeSubscriber.schedulerFn [as _next] (core.js:12621)
    at SafeSubscriber.push../node_modules/rxjs/_esm5/internal/Subscriber.js.SafeSubscriber.__tryOrUnsub (Subscriber.js:196)
    at SafeSubscriber.push../node_modules/rxjs/_esm5/internal/Subscriber.js.SafeSubscriber.next (Subscriber.js:134)
*
*
* */
