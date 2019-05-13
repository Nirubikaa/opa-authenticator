package org.wso2.carbon.extension.identity.authenticator.internal;

public class Dataholder {

    private static Dataholder instance = new Dataholder();

    public static Dataholder getInstance() {

        return instance;
    }

}
