import {AppModel} from "./AppModel";

export class CoverageEvaluationBundle {
  constructor(
    public gam: AppModel,
    public targetPlatformId: string
  ){}

}
