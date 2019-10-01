package de.iaas.skywalker.sqlite;

import de.iaas.skywalker.models.ServiceMapping;
import io.jsondb.JsonDBTemplate;
import io.jsondb.crypto.DefaultAESCBCCipher;
import io.jsondb.crypto.ICipher;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class ServiceRepoHelper {

    public static void main(String[] args) throws GeneralSecurityException {
        String dbFilesLocation = "sqlite/jsondb/";

        //Java package name where POJO's are present
        String baseScanPackage = "de.iaas.skywalker.models";

        //Optionally a Cipher object if you need Encryption
        ICipher cipher = new DefaultAESCBCCipher("1r8+24pibarAWgS85/Heeg==");

        JsonDBTemplate jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage, cipher);

        jsonDBTemplate.createCollection(ServiceMapping.class);
        ServiceMapping sm = new ServiceMapping();
        ////////////////////////////////////////////////////
        sm.setGenericResourceId("http");
        sm.setProvider("aws");
        sm.setProviderResourceId("http");
        sm.setServiceProperties(new ArrayList<String>(){{
            add("path");
            add("authorizer");
            add("method");
        }});
        jsonDBTemplate.insert(sm);

        sm = new ServiceMapping();
        sm.setGenericResourceId("http");
        sm.setProvider("azure");
        sm.setProviderResourceId("http");
        sm.setServiceProperties(new ArrayList<String>(){{
            add("route");
            add("authLevel");
            add("methods");
        }});
        jsonDBTemplate.insert(sm);
        ////////////////////////////////////////////////////

        ////////////////////////////////////////////////////
        sm = new ServiceMapping();
        sm.setGenericResourceId("storage");
        sm.setProvider("aws");
        sm.setProviderResourceId("s3");
        sm.setServiceProperties(new ArrayList<String>(){{
            add("bucket");
            add("event");
            add("rules");
        }});
        jsonDBTemplate.insert(sm);

        sm = new ServiceMapping();
        sm.setGenericResourceId("storage");
        sm.setProvider("azure");
        sm.setProviderResourceId("blob");
        sm.setServiceProperties(new ArrayList<String>(){{
            add("path");
        }});
        jsonDBTemplate.insert(sm);
        ////////////////////////////////////////////////////

        String jxQuery = String.format("/.[genericResourceId='%s']", "storage");
        List<ServiceMapping> serviceMappings = jsonDBTemplate.find(jxQuery, ServiceMapping.class);

        ServiceMapping i = jsonDBTemplate.findById("storage", ServiceMapping.class);
    }
}
