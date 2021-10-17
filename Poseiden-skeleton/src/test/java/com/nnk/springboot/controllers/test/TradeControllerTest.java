package com.nnk.springboot.controllers.test;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TradeControllerTest {
	
	
	  @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private TradeRepository tradeRepository;

	    @WithMockUser(authorities = "ADMIN")
	    @Test
	    public void showTradeAdmin() throws Exception {
	        this.mockMvc.perform(get("/trade/list")).andExpect(status().isOk());
	    }

	    @Test
	    @WithMockUser
	    public void showTrade() throws Exception {
	        this.mockMvc.perform(get("/trade/list")).andExpect(status().isForbidden());
	    }
	    
	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void addTradeAdmin() throws Exception {
	        this.mockMvc.perform(get("/trade/add")).andExpect(status().isOk());
	    }
	    @Test
	    @WithMockUser
	    public void addTrade() throws Exception {
	        this.mockMvc.perform(get("/trade/add")).andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void validateTradeAdmin() throws Exception {
	        this.mockMvc.perform(post("/trade/validate")
	                .param("account", "account")
	                .param("type", "type")
	                .param("buyQuantity", "10.0")
	                .with(csrf())
	        ).andExpect(redirectedUrl("/trade/list"));
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void validateTradeAdminHasError() throws Exception {
	        this.mockMvc.perform(post("/trade/validate")
	                .param("type", "type")
	                .param("buyQuantity", "10.0")
	                .with(csrf())
	        ).andExpect(model().hasErrors());
	    }

	    @Test
	    @WithMockUser
	    public void validateTrade() throws Exception {
	        this.mockMvc.perform(post("/trade/validate")
	                .param("account", "account")
	                .param("type", "type")
	                .param("buyQuantity", "10.0")
	                .with(csrf())
	        ).andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void showUpdateTradeAdmin() throws Exception {
	        Trade trade = tradeRepository.save(new Trade("Account", "Type", 10.0d));

	        this.mockMvc.perform(get("/trade/update/" + trade.getTradeId()))
	                .andExpect(model().attribute("trade", Matchers.hasProperty("account", Matchers.equalTo("Account"))))
	                .andExpect(model().attribute("trade", Matchers.hasProperty("type", Matchers.equalTo("Type"))))
	                .andExpect(model().attribute("trade", Matchers.hasProperty("buyQuantity", Matchers.equalTo(10.0d))));
	    }

	    @Test
	    @WithMockUser
	    public void showUpdateTrade() throws Exception {
	        Trade trade = tradeRepository.save(new Trade("Account", "Type", 10.0d));

	        this.mockMvc.perform(get("/trade/update/" + trade.getTradeId())).andExpect(status().isForbidden());
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void updateTradeAdmin() throws Exception {
	        Trade trade = tradeRepository.save(new Trade("Account", "Type", 10.0d));
	        this.mockMvc.perform(post("/trade/update/" + trade.getTradeId())
	                .param("account", "account")
	                .param("type", "type")
	                .param("buyQuantity", "10.0")
	                .with(csrf())
	        ).andExpect(redirectedUrl("/trade/list"));
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void updateTradeAdminHasError() throws Exception {
	        Trade trade = tradeRepository.save(new Trade("Account", "Type", 10.0d));
	        this.mockMvc.perform(post("/trade/update/" + trade.getTradeId())
	                .param("account", "account")
	                .param("type", "type")
	                .param("buyQuantity", "A.0")
	                .with(csrf())
	        ).andExpect(model().hasErrors());
	    }


	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void deleteTradeAdmin() throws Exception {
	        Trade trade = tradeRepository.save(new Trade("Account", "Type", 10.0d));

	        this.mockMvc.perform(get("/trade/delete/" + trade.getTradeId())).andExpect(status().isFound()).andReturn();
	    }
	}
