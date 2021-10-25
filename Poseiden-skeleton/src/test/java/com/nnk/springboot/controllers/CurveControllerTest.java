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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CurveControllerTest {

	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showCurvePointAdmin() throws Exception {
        this.mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser()
    public void showCurvePoint() throws Exception {
        this.mockMvc.perform(get("/curvePoint/list")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addCurvePointAdmin() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void addCurvePoint() throws Exception {
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateCurvePointAdmin() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "90")
                .param("term", "10.0")
                .param("value", "10.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateCurvePointAdminHasError() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "90")
                .param("term", "A")
                .param("value", "B")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void validateCurvePoint() throws Exception {
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "90")
                .param("term", "10.0")
                .param("value", "10.0")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showUpdateCurvePointAdmin() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));

        this.mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("curveId", Matchers.equalTo(42))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("term", Matchers.equalTo(10.0d))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("value", Matchers.equalTo(10.0d))));
    }

    @Test
    @WithMockUser
    public void showUpdateCurvePoint() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));

        this.mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateCurvePointAdmin() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));
        this.mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .param("curveId", "91")
                .param("term", "12.0")
                .param("value", "12.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateCurvePointAdminHasError() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));
        this.mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .param("curveId", "91")
                .param("term", "12.0")
                .param("value", "A")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteBidListAdmin() throws Exception {
        CurvePoint curvePoint = curvePointRepository.save(new CurvePoint(42, 10.0d, 10.0d));

        this.mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId())).andExpect(status().isFound()).andReturn();
    }
}
