package com.nnk.springboot.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BidListControllerTest {
	
	
	  	@Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    BidListRepository bidListRepository;

	    @Test
	    @WithMockUser(authorities = "USER")
	    public void showBidListTest() throws Exception {
	        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void showBidListTestAdmin() throws Exception {
	        mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void addBidListAdmin() throws Exception {
	        this.mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
	    }

	    @Test
	    @WithMockUser
	    public void addBidList() throws Exception {
	        this.mockMvc.perform(get("/bidList/add")).andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void validateBidListAdmin() throws Exception {
	        this.mockMvc.perform(post("/bidList/validate")
	                .param("Account", "BobAccount")
	                .param("type", "livret")
	                .param("bidQuantity", "5")
	                .with(csrf())
	        ).andExpect(redirectedUrl("/bidList/list"));
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void validateBidListAdminHasErrors() throws Exception {
	        this.mockMvc.perform(post("/bidList/validate")
	                .param("Account", "BobAccount")
	                .param("type", "livret")
	                .param("bidQuantity", "aaa")
	                .with(csrf())
	        ).andExpect(model().hasErrors());
	    }

	    @Test
	    @WithMockUser
	    public void validBidList() throws Exception {
	        this.mockMvc.perform(post("/bidList/validate")
	                .param("Account", "BobAccount")
	                .param("type", "livret")
	                .param("bidQuantity", "5")
	                .with(csrf())
	        ).andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void showUpdateBidListAdmin() throws Exception {
	        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

	        this.mockMvc.perform(get("/bidList/update/" + bid.getBidListId()))
	                .andExpect(model().attribute("bidList", Matchers.hasProperty("account", Matchers.equalTo("Account"))))
	                .andExpect(model().attribute("bidList", Matchers.hasProperty("type", Matchers.equalTo("Type"))))
	                .andExpect(model().attribute("bidList", Matchers.hasProperty("bidQuantity", Matchers.equalTo(10.0d))));
	    }

	    @Test
	    @WithMockUser
	    public void showUpdateBidList() throws Exception {
	        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

	        this.mockMvc.perform(get("/bidList/update/" + bid.getBidListId())).andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void updateBidListAdmin() throws Exception {
	        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));
	        this.mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
	                .param("account", "nuAccount")
	                .param("type", "nuType")
	                .param("bidQuantity", "12.0")
	                .with(csrf())
	        ).andExpect(redirectedUrl("/bidList/list"));
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void updateBidListAdminHasError() throws Exception {
	        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));
	        this.mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
	                .param("account", "nuAccount")
	                .param("type", "nuType")
	                .param("bidQuantity", "A.0")
	                .with(csrf())
	        ).andExpect(model().hasErrors());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void deleteBidListAdmin() throws Exception {
	        BidList bid = bidListRepository.save(new BidList("Account", "Type", 10.0d));

	        this.mockMvc.perform(get("/bidList/delete/" + bid.getBidListId())).andExpect(status().isFound()).andReturn();
	    }
}
