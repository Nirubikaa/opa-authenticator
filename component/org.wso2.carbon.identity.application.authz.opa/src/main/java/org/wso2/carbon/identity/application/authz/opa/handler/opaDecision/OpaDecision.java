package org.wso2.carbon.identity.application.authz.opa.handler.opaDecision;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpaDecision {

    private final HttpURLConnection connection;

    public static class Builder {
        private final String policyId;
        private final String packageName;

        private JSONObject query = new JSONObject();
        private String domain = "localhost";
        private int port = 8181;

        public Builder(String policyId, String packageName) {
            this.policyId = policyId;
            this.packageName = packageName;
        }

        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder query(JSONObject query) {
            this.query = query;
            return this;
        }

        public OpaDecision build() throws IOException {
            return new OpaDecision(this);
        }
    }

    private OpaDecision(Builder builder) throws IOException {
        StringBuilder urlBuilder = new StringBuilder()
                .append("http://")
                .append(builder.domain)
                .append(":")
                .append(builder.port)
                .append("/v1/data/")
                .append(builder.packageName)
                .append("/")
                .append(builder.policyId)
                .append("/allow");

        URL url = new URL(urlBuilder.toString());
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            writer.write(builder.query.toString());
        }
    }

    public boolean getResponse() throws IOException {
        JSONObject query = new JSONObject(IOUtils.toString(connection.getInputStream(), "UTF-8"));
        boolean status;
        try {
            status = Boolean.parseBoolean(query.get("result").toString());
        } catch (JSONException e) {
            status = false;
        }

        return status;

    }

}
