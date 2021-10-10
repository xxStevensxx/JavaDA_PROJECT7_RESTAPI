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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CurveControllerTest {

	
	@Autowired
	private MockMvc mockMVC;
	
	@Autowired
	CurvePointRepository curveRepository;
	
		@Test
		@WithMockUser(authorities = "ADMIN")
	    public void showCurvePointAdmin() throws Exception {
	        mockMVC.perform(get("/curvePoint/list")).andExpect(status().isOk());
	    }

    	@Test
	    @WithMockUser()
	    public void showCurvePoint() throws Exception {
    		
	        this.mockMVC.perform(get("/curvePoint/list")).andExpect(status().isForbidden());
	    }
    	
	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void addCurvePointAdminTest() throws Exception {
	        this.mockMVC.perform(get("/curvePoint/add")).andExpect(status().isOk());
	    }

	    @WithMockUser
	    public void testAddCurvePoint() throws Exception {
	        this.mockMVC.perform(get("/curvePoint/add")).andExpect(status().isForbidden());
	    }

	    
//	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void validateCurvePointAdminTest() throws Exception {
	        this.mockMVC.perform(post("/curvePoint/validate")
	                .param("curveId", "90")
	                .param("term", "10.0")
	                .param("value", "10.0")
	                .with(csrf())
	        ).andExpect(redirectedUrl("/curvePoint/list"));
	    }

	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void validateCurvePointAdminHasErrorTest() throws Exception {
	        this.mockMVC.perform(post("/curvePoint/validate")
	                .param("curveId", "90")
	                .param("term", "A")
	                .param("value", "B")
	                .with(csrf())
	        ).andExpect(model().hasErrors());
	    }


	    @Test
	    @WithMockUser
	    public void validateCurvePointTest() throws Exception {
	        this.mockMVC.perform(post("/curvePoint/validate")
	                .param("curveId", "90")
	                .param("term", "10.0")
	                .param("value", "10.0")
	                .with(csrf())
	        ).andExpect(status().isForbidden());
	    }
	    
	    
//	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void showUpdateCurvePointAdminTest() throws Exception {
	        CurvePoint curvePoint = curveRepository.save(new CurvePoint(42, 10.0d, 10.0d));

	        this.mockMVC.perform(get("/curvePoint/update/" + curvePoint.getId()))
	                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("curveId", Matchers.equalTo(42))))
	                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("term", Matchers.equalTo(10.0d))))
	                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("value", Matchers.equalTo(10.0d))));
	    }
	    
//	    @Test
	    @WithMockUser
	    public void showUpdateCurvePointTest() throws Exception {
	        CurvePoint curvePoint = curveRepository.save(new CurvePoint(42, 10.0d, 10.0d));

	        this.mockMVC.perform(get("/curvePoint/update/" + curvePoint.getId())).andExpect(status().isForbidden());
	    }

//	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void updateCurvePointAdminTest() throws Exception {
	        CurvePoint curvePoint = curveRepository.save(new CurvePoint(42, 10.0d, 10.0d));
	        this.mockMVC.perform(post("/curvePoint/update/" + curvePoint.getId())
	                .param("curveId", "91")
	                .param("term", "12.0")
	                .param("value", "12.0")
	                .with(csrf())
	        ).andExpect(redirectedUrl("/curvePoint/list"));
	    }

//	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void updateCurvePointAdminHasErrorTest() throws Exception {
	        CurvePoint curvePoint = curveRepository.save(new CurvePoint(42, 10.0d, 10.0d));
	        this.mockMVC.perform(post("/curvePoint/update/" + curvePoint.getId())
	                .param("curveId", "91")
	                .param("term", "12.0")
	                .param("value", "A")
	                .with(csrf())
	        ).andExpect(model().hasErrors());
	    }

//	    @Test
	    @WithMockUser(authorities = "ADMIN")
	    public void deleteBidListAdminTest() throws Exception {
	        CurvePoint curvePoint = curveRepository.save(new CurvePoint(42, 10.0d, 10.0d));

	        this.mockMVC.perform(get("/curvePoint/delete/" + curvePoint.getId())).andExpect(status().isFound()).andReturn();
	    }
}
