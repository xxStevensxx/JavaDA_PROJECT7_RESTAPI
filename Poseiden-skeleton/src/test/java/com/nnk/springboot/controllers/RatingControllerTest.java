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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RatingControllerTest {
	

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showRatingAdmin() throws Exception {
        this.mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void showRating() throws Exception {
        this.mockMvc.perform(get("/rating/list")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void addRatingAdmin() throws Exception {
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void addRating() throws Exception {
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateRatingAdmin() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void validateRatingAdminError() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void validateRating() throws Exception {
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void showUpdateRatingAdmin() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));

        this.mockMvc.perform(get("/rating/update/" + rating.getId()))
                .andExpect(model().attribute("rating", Matchers.hasProperty("moodysRating", Matchers.equalTo("moodysRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("sandPRating", Matchers.equalTo("sandPRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("fitchRating", Matchers.equalTo("fitchRating"))))
                .andExpect(model().attribute("rating", Matchers.hasProperty("orderNumber", Matchers.equalTo(42))));
    }

    @Test
    @WithMockUser
    public void showUpdateRating() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));

        this.mockMvc.perform(get("/rating/update/" + rating.getId())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateBidListAdmin() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void updateBidListAdminHasError() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));
        this.mockMvc.perform(post("/rating/update/" + rating.getId())
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteRatingAdmin() throws Exception {
        Rating rating = ratingRepository.save(new Rating("moodysRating", "sandPRating", "fitchRating", 42));

        this.mockMvc.perform(get("/rating/delete/" + rating.getId())).andExpect(status().isFound()).andReturn();
    }
}
