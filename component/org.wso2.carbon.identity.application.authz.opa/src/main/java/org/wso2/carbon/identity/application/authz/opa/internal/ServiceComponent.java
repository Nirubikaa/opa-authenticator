package org.wso2.carbon.identity.application.authz.opa.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.identity.application.authz.opa.handler.impl.OPABasedAuthorizationHandler;
import org.wso2.carbon.identity.application.authentication.framework.handler.request.PostAuthenticationHandler;

@Component(
        name = "identity.authenticator.opa.component",
        immediate = true
)
public class ServiceComponent {

    private static final Log log = LogFactory.getLog(ServiceComponent.class);

    @Activate
    protected void activate(ComponentContext ctxt){

        try {
            OPABasedAuthorizationHandler opabAsedAuthorizationHandler = new OPABasedAuthorizationHandler();
            ctxt.getBundleContext().registerService(PostAuthenticationHandler.class.getName(),
                    opabAsedAuthorizationHandler, null);
            if (log.isDebugEnabled()){
                log.debug("OPA authorization handler bundle is activated");
            }
            log.info("OPA authorization handler bundle is activated");
        } catch (Throwable throwable) {
            log.error("Error while starting identity appplication OPA component", throwable);
        }
    }
}
