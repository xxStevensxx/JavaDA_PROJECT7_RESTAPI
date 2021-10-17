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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RuleControllerTest {
	

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showRuleNameAdmin() throws Exception {
        this.mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void sowRuleName() throws Exception {
        this.mockMvc.perform(get("/ruleName/list")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addRuleNameAdmin() throws Exception {
        this.mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void addRuleName() throws Exception {
        this.mockMvc.perform(get("/ruleName/add")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateRuleNameAdmin() throws Exception {
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateRuleNameAdminHasError() throws Exception {
        this.mockMvc.perform(post("/ruleName/validate")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void validateRuleName() throws Exception {
        this.mockMvc.perform(post("/ruleName/validate")
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showUpdateRuleNameAdmin() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));

        this.mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("name", Matchers.equalTo("name"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("description", Matchers.equalTo("description"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("json", Matchers.equalTo("json"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("template", Matchers.equalTo("template"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("sqlStr", Matchers.equalTo("sqlStr"))))
                .andExpect(model().attribute("ruleName", Matchers.hasProperty("sqlPart", Matchers.equalTo("sqlPart"))));
    }

    @Test
    @WithMockUser
    public void showUpdateRuleName() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));

        this.mockMvc.perform(get("/ruleName/update/" + ruleName.getId())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateRuleNameAdmin() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        this.mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                .param("name", "name")
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateRuleNameAdminHasError() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));
        this.mockMvc.perform(post("/ruleName/update/" + ruleName.getId())
                .param("description", "description")
                .param("json", "json")
                .param("template", "template")
                .param("sqlStr", "sqlStr")
                .param("sqlPart", "sqlPart")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteRuleNameAdmin() throws Exception {
        RuleName ruleName = ruleNameRepository.save(new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"));

        this.mockMvc.perform(get("/ruleName/delete/" + ruleName.getId())).andExpect(status().isFound()).andReturn();
    }
}
