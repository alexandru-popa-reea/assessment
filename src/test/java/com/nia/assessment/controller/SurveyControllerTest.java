package com.nia.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nia.assessment.dto.SurveyDto;
import com.nia.assessment.model.SurveyEntity;
import com.nia.assessment.service.SurveyService;
import com.nia.assessment.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SurveyController.class)
class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private TokenService tokenService;

    @Test
    void saveSurvey_HappyPath_should_return_200_OK() throws Exception {

        // given
        SurveyDto surveyDto = SurveyDto.builder().patientId(1L).skinGrade((short) 5).sleepGrade((short) 9).build();
        given(surveyService.save(any(SurveyDto.class))).willReturn(SurveyEntity.builder().id(33L).build());

        //when
        ResultActions result = mockMvc.perform(post("/api/surveys")
                .content(asJsonString(surveyDto))
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        //then
        result.andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}