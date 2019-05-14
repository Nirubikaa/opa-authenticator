package org.wso2.carbon.identity.application.authz.opa.internal;

public class Dataholder {

    private static Dataholder instance = new Dataholder();

    public static Dataholder getInstance() {

        return instance;
    }

}
