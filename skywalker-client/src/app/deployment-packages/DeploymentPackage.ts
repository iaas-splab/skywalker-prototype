export class DeploymentPackage {
  constructor(
    public id: string,
    public analyzed: any,
    public functions: {[key: string]: string},
    public deploymentModel: string
  ){}

}
