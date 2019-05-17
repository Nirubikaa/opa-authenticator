package org.wso2.carbon.identity.application.authz.opa.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.wso2.carbon.identity.application.authz.opa.handler.opaDecision.OpaDecision;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.PostAuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.AbstractPostAuthnHandler;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.PostAuthnHandlerFlowStatus;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OPABasedAuthorizationHandler extends AbstractPostAuthnHandler {

    private static final Log log = LogFactory.getLog(OPABasedAuthorizationHandler.class);


    /**
     * Executes the authorization flow
     *
     * @param request  request
     * @param response response
     * @param context  context
     */
    @Override
    public PostAuthnHandlerFlowStatus handle(HttpServletRequest request, HttpServletResponse response,
                                             AuthenticationContext context) throws PostAuthenticationFailedException {
        if (log.isDebugEnabled()) {
            log.debug("In policy authorization flow...");
        }


        if (!isAuthorizationEnabled(context) || getAuthenticatedUser(context) == null) {
            return PostAuthnHandlerFlowStatus.SUCCESS_COMPLETED;
        }

        JSONObject query = new JSONObject("{'input': { 'user':'bob', 'method':'POST'}}");

        try {
            OpaDecision client = new OpaDecision.Builder("policy", "opa")
                    .query(query)
                    .build();
            boolean resp = client.getResponse();

            if(resp) {
                return PostAuthnHandlerFlowStatus.SUCCESS_COMPLETED;
            } else {
                return PostAuthnHandlerFlowStatus.UNSUCCESS_COMPLETED;
            }

        } catch (IOException e) {
            throw new PostAuthenticationFailedException("Authorization Failed", "OPA policy evaluation failed");
        }
    }



    private AuthenticatedUser getAuthenticatedUser(AuthenticationContext authenticationContext) {

        if (authenticationContext != null && authenticationContext.getSequenceConfig() != null) {
            return authenticationContext.getSequenceConfig().getAuthenticatedUser();
        }
        return null;
    }

    private boolean isAuthorizationEnabled(AuthenticationContext authenticationContext) {

        if (authenticationContext != null && authenticationContext.getSequenceConfig() != null &&
                authenticationContext.getSequenceConfig().getApplicationConfig() != null) {
            return authenticationContext.getSequenceConfig().getApplicationConfig().isEnableAuthorization();
        }
        return false;
    }

}