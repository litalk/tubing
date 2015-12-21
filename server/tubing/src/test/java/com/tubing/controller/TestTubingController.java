package com.tubing.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class TestTubingController {

    private MockMvc _mockMvc;

    @Before
    public void setUp() {

        _mockMvc = MockMvcBuilders.standaloneSetup(new TubingController()).build();
    }

    @Test
    public void testLogin() throws Exception {

        _mockMvc.perform(MockMvcRequestBuilders.post("/tubing/login", 1L)).
                andExpect(MockMvcResultMatchers.status().isOk());
    }
}
