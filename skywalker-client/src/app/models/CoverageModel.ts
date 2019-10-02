export class CoverageModel {
  constructor(
    public id: string,
    public eventSourceCoverage: {[key: string]: Array<{[key: string]: string}>}
  ){}

}
