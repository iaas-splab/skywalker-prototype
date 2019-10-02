export class AppModel {
  constructor(
    public id: string,
    public eventSources: {[key: string]: Array<String>},
    public functions: {[key: string]: Array<String>},
    public invokedServices: {[key: string]: Array<String>},
){}

}
