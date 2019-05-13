package org.wso2.carbon.extension.identity.authenticator.handler.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.ObjectFactory;
import org.wso2.balana.utils.policy.PolicyBuilder;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.util.FrameworkUtils;
import org.wso2.carbon.identity.entitlement.common.util.PolicyCreatorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({LogFactory.class, FrameworkUtils.class, PolicyCreatorUtil.class, PolicyBuilder.class})
@PowerMockIgnore("javax.xml.*")
public class OPABasedAuthorizationHandlerTest {

    private OPABAsedAuthorizationHandler opaBasedAuthorizationHandler;
    private AuthenticationContext context;
    private Log log = mock(Log.class);

    private static final String DECISION = "true";
    private static final boolean opaResponse= true;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;


    @ObjectFactory
    public IObjectFactory getObjectFactory() {

        return new PowerMockObjectFactory();
    }

    @BeforeClass
    public void init() {

        mockStatic(LogFactory.class);
        when(LogFactory.getLog(OPABAsedAuthorizationHandler.class)).thenReturn(log);
        opaBasedAuthorizationHandler = spy(new OPABAsedAuthorizationHandler());
        context = mock(AuthenticationContext.class);
    }

//    @Test
//    public void testEvaluateOPAResponse() throws Exception {
//
//        String response = WhiteboxImpl.invokeMethod(opaBasedAuthorizationHandler,
//                "evaluateOPAResponse",opaResponse);
//        assertEquals(response, DECISION);
//    }


}
