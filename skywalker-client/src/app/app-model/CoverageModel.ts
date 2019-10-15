export class CoverageModel {
  constructor(
    public id: string,
    public eventSourceCoverage: {[key: string]: Array<{[key: string]: string}>},
    public targetPlatform: string,
    public platformCoverageScore: any,
    public propertyCoverageScores: {[key: string]: any},
    public deploymentModelId: string
  ){}

}
