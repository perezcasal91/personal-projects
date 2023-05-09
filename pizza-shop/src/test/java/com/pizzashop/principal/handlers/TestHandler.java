package com.pizzashop.principal.handlers;

import com.pizzashop.principal.controllers.rest.*;
import com.pizzashop.principal.services.impls.AuthServiceImplDBTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.springframework.test.context.ActiveProfiles;

@Suite
@SuiteDisplayName("Test Handler")
@SelectClasses(
        {
                AuthRestControllerDBTest.class,
                AuthServiceImplDBTest.class,
                RoleRestControllerDBTest.class,
                UserFieldRestControllerDBTest.class,
                UserRestControllerDBTest.class,
                CategoryRestControllerDBTest.class,
                ProductRestControllerDBTest.class,
                ProductFieldRestControllerDBTest.class
        }
)
@ActiveProfiles("test")
public class TestHandler {
}
