//package de.iaas.skywalker.adapter.java.adapter.pattern;
//
//public class AzureApplicationModelAdapter implements ApplicationModelAdapter {
//    private GenericApplicationModel GAM;
//
//    public AzureApplicationModelAdapter(GenericApplicationModel azureTemplate) {
//        this.GAM = azureTemplate;
//    }
//
//    @Override
//    public String getEvents() {
//        return getEventsAzure(GAM.getEvents());
//    }
//
//    @Override
//    public String getInvokedServices() {
//        return getInvokedServicesAzure(GAM.getInvokedServices());
//    }
//
//    @Override
//    public String getFunction() {
//        return getFunctionAzure(GAM.getFunction());
//    }
//
//    private String getEventsAzure(String genericProperty) {
//        return "inputBinding";
//    }
//
//    private String getInvokedServicesAzure(String genericProperty) {
//        return "outputBinding";
//    }
//
//    private String getFunctionAzure(String genericProperty) {
//        return "scriptFile";
//    }
//}
