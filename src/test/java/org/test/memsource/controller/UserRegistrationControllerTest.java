package org.test.memsource.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.test.memsource.service.UserService;

@RunWith(SpringRunner.class)
public class UserRegistrationControllerTest {

    @Mock
    private UserService service;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in webapp-mode (same config as spring-boot)
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilter, "/*")
                .build();
    }

    //    @Test
    //    public void getAnonymousId_sunny() throws Exception {
    //        String accessToken = obtainAccessToken();
    //        mockMvc.perform(get(CUSTOMER_OAUTH + accessToken + "/identity:anonymousId")
    //                .header("Authorization", "Bearer " + accessToken)
    //                .accept(MediaType.APPLICATION_JSON))
    //                .andDo(print())
    //                .andExpect(status().isOk())
    //        ;
    //    }

}
