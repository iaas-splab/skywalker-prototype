export class CoverageModel {
  public targetPlatform: string;
  constructor(
    public id: string,
    public eventSourceCoverage: {[key: string]: Array<{[key: string]: string}>},
  ){}

}
