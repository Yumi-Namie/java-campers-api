package com.camper.demo.signup.controller;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.signup.dto.SignupRequestDTO;
import com.camper.demo.signup.dto.SignupResponseDTO;
import com.camper.demo.signup.entity.Signup;
import com.camper.demo.signup.service.SignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupControllerUnitTest {

    private SignupController signupController;

    @Mock
    private SignupService signupServiceMock;

    @BeforeEach
    void setUp() {
        signupController = new SignupController(signupServiceMock);
    }

    @Test
    void createSignup() {
        // Mocking Data
        var requestDTO = new SignupRequestDTO();
        requestDTO.setCamperId(1L);
        requestDTO.setActivityId(1L);
        requestDTO.setTime(10);

        // Mocking service behavior
        var createdSignup = new Signup();
        createdSignup.setId(1L);
        createdSignup.setCamper(new Camper());
        createdSignup.getCamper().setId(1L);
        createdSignup.setActivity(new Activity());
        createdSignup.getActivity().setId(1L);
        createdSignup.setTime(10);

        when(signupServiceMock.createSignup(requestDTO)).thenReturn(createdSignup);

        ResponseEntity<SignupResponseDTO> responseEntity = signupController.createSignup(requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        SignupResponseDTO responseDTO = responseEntity.getBody();
        assertNotNull(responseDTO);
        assertEquals(createdSignup.getId(), responseDTO.getId());
        assertEquals(createdSignup.getCamper().getId(), responseDTO.getCamperId());
        assertEquals(createdSignup.getActivity().getId(), responseDTO.getActivityId());
        assertEquals(createdSignup.getTime(), responseDTO.getTime());
    }




    @Test
    void getAllSignups() {
        // Mocking service behavior
        List<Signup> signupList = new ArrayList<>();

        Camper camper1 = new Camper();
        camper1.setId(1L);

        Camper camper2 = new Camper();
        camper2.setId(2L);

        Activity activity1 = new Activity();
        activity1.setId(1L);

        Activity activity2 = new Activity();
        activity2.setId(2L);

        Signup signup1 = new Signup();
        signup1.setId(1L);
        signup1.setCamper(camper1);
        signup1.setActivity(activity1);
        signup1.setTime(10);

        Signup signup2 = new Signup();
        signup2.setId(2L);
        signup2.setCamper(camper2);
        signup2.setActivity(activity2);
        signup2.setTime(15);

        signupList.add(signup1);
        signupList.add(signup2);


        // Mock the behavior of signupServiceMock
        when(signupServiceMock.getAllSignups()).thenReturn(signupList);

        // Call the controller method you want to test
        ResponseEntity<List<SignupResponseDTO>> responseEntity = signupController.getAllSignups();

        // Verify expectations
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<SignupResponseDTO> responseDTOList = responseEntity.getBody();
        assertNotNull(responseDTOList);
        assertEquals(signupList.size(), responseDTOList.size());

        // Verify if each Signup in the response list corresponds to the Signup in the mock service
        for (int i = 0; i < signupList.size(); i++) {
            Signup signup = signupList.get(i);
            SignupResponseDTO responseDTO = responseDTOList.get(i);

            assertEquals(signup.getId(), responseDTO.getId());
            assertEquals(signup.getCamper().getId(), responseDTO.getCamperId());
            assertEquals(signup.getActivity().getId(), responseDTO.getActivityId());
            assertEquals(signup.getTime(), responseDTO.getTime());
        }
    }

}