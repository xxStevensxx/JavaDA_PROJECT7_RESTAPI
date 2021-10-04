package com.nnk.springboot.controllers.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;




public class BidListControllerTest {
	
	
	@Autowired
	private MockMvc mockMVC;
	
	
	@Autowired
	BidListRepository bidListRepository;
	
	@Test
    @WithMockUser(authorities = "USER")
    public void showBidListTestUser() throws Exception {

    	mockMVC.perform(get("/bidList/list")).andExpect(status().isForbidden());
    	
    }
	
	
	@Test
    @WithMockUser(authorities = "ADMIN")
    public void showBidListTestAdmin() throws Exception {

    	mockMVC.perform(get("/bidList/list")).andExpect(status().isOk());
    	
    }
	
	@Test
    @WithMockUser(authorities = "ADMIN")
    public void addBidListAdmin() throws Exception {
        this.mockMVC.perform(get("/bidList/add")).andExpect(status().isOk());
        
    }
	
	
	@Test
    @WithMockUser
    public void addBidList() throws Exception {
        this.mockMVC.perform(get("/bidList/add")).andExpect(status().isForbidden());
        
    }
	
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateBidListAdmin() throws Exception {
    	
    	mockMVC.perform(post("/bidList/validate")
    			.param("Account", "TestAccount")
    			.param("type", "PEA")
    			.param("bidQuantity", "5")
    			.with(csrf())).andExpect(redirectedUrl("/bidList/list"));
    	
    }
    
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateBidListAdminHasErrors() throws Exception {
        mockMVC.perform(post("/bidList/validate")
                .param("Account", "TestAccount")
                .param("type", "PEA")
                .param("bidQuantity", "abc")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }
    
    
    @Test
    @WithMockUser
    public void testValidBidList() throws Exception {
        this.mockMVC.perform(post("/bidList/validate")
                .param("Account", "TestAccount")
                .param("type", "PEA")
                .param("bidQuantity", "5")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }
    
    
    
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showUpdateBidListAdmin() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

        this.mockMVC.perform(get("/bidList/update/" + bid.getBidListId()))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("account", Matchers.equalTo("Account"))))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("type", Matchers.equalTo("Type"))))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("bidQuantity", Matchers.equalTo(10.0d))));
    }
    
    @Test
    @WithMockUser
    public void showUpdateBidList() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

        this.mockMVC.perform(get("/bidList/update/" + bid.getBidListId())).andExpect(status().isForbidden());
    }
    
    
    @WithMockUser(authorities = "ADMIN")
    @Test
    public void updateBidListAdmin() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));
        this.mockMVC.perform(post("/bidList/update/" + bid.getBidListId())
                .param("account", "updateAccount")
                .param("type", "updatePEL")
                .param("bidQuantity", "12.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/bidList/list"));
    }
    
    
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testUpdateBidListAdminHasError() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));
        this.mockMVC.perform(post("/bidList/update/" + bid.getBidListId())
                .param("account", "updateAccount")
                .param("type", "updatePEL")
                .param("bidQuantity", "A.0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }
    
    
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testDeleteBidListAdmin() throws Exception {
        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

        this.mockMVC.perform(get("/bidList/delete/" + bid.getBidListId())).andExpect(status().isFound()).andReturn();
    }
    
}
