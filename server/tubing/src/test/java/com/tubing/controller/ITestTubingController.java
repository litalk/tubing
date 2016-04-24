package com.tubing.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tubing.TubingApplicationTests;
import com.tubing.common.ObjectMapperUtils;
import com.tubing.dal.model.Account;

//@Ignore
//@RunWith(MockitoJUnitRunner.class)
public class ITestTubingController extends TubingApplicationTests {
    
    private MockMvc _mockMvc;
    @Autowired
    private TubingController _controller;
    
    @Before
    public void setUp() {
        
        _mockMvc = MockMvcBuilders.standaloneSetup(_controller).build();
    }
    
    @Test
    public void testLogin() throws Exception {
        
        _mockMvc.perform(
                MockMvcRequestBuilders.post("/tubing/login", 1L).contentType(
                        MediaType.APPLICATION_JSON).content(getUserJson())).andExpect(
                                MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    public void testAddToPlaylistFormat1() throws Exception {
        
        String shazam =
                "Found Travesuras (Remix) by Nicky Jam with Shazam, have a listen: http://shz.am/t146768962";
        addToPlayList(shazam);
    }
    
    @Test
    public void testAddToPlaylistFormat2() throws Exception {
        
        String shazam =
                "I just used Shazam to discover Llévame Contigo by Romeo Santos. http://shz.am/t54018231";
        addToPlayList(shazam);
    }
    
    private void addToPlayList(String shazam) throws Exception {
        
        _mockMvc.perform(
                MockMvcRequestBuilders.post("/tubing/playlist", 1L).contentType(
                        MediaType.APPLICATION_JSON).content(shazam)).andExpect(
                                MockMvcResultMatchers.status().isOk());
    }
    
    public String getUserJson() {
        
        return ObjectMapperUtils.from(
                new Account("Krovi Sumsum", "470859838725a3b"));
    }
}
