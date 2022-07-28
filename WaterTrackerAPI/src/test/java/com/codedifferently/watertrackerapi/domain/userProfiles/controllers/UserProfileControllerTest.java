package com.codedifferently.watertrackerapi.domain.userProfiles.controllers;

import com.codedifferently.watertrackerapi.JsonConverter;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceCreationException;
import com.codedifferently.watertrackerapi.domain.core.exceptions.ResourceNotFoundException;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileCreateRequest;
import com.codedifferently.watertrackerapi.domain.userProfiles.dtos.UserProfileDTO;
import com.codedifferently.watertrackerapi.domain.userProfiles.models.UserProfile;
import com.codedifferently.watertrackerapi.domain.userProfiles.services.UserProfileService;
import com.codedifferently.watertrackerapi.security.PrincipalDetailsArgumentResolver;
import com.codedifferently.watertrackerapi.security.firebase.models.FireBaseUser;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserProfileControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;
    private UserProfileCreateRequest mockDetail;
    private UserProfile mockUserProfile;
    private UserProfileDTO mockDTO;

    @BeforeEach
    public void setUp(){
        mockUserProfile = new UserProfile("Test","User","test@user.com");
        mockUserProfile.setId("xyz1234");
        mockDetail = new UserProfileCreateRequest("Test", "User","test@user.com", "password");
        mockDTO = new UserProfileDTO(mockUserProfile);
        FireBaseUser fireBaseUser = new FireBaseUser();
        fireBaseUser.setEmail("test@user.com");
        fireBaseUser.setUid("xyz");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserProfileController(userProfileService))
                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(fireBaseUser))
                .build();
    }

    @Test
    @DisplayName("User ProfileCreate - /api/v1/userprofile/create : success")
    public void createInventoryItemRequestSuccess() throws Exception{
        BDDMockito.doReturn(mockDTO).when(userProfileService).create(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/userprofile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.asJsonString(mockDetail)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is("xyz1234")));
    }

    @Test
    @DisplayName("User ProfileCreate - /api/v1/userprofile/create : failed")
    public void createInventoryItemRequestFailed() throws Exception {
        BDDMockito.doThrow(new ResourceCreationException("Exists")).when(userProfileService).create(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/userprofile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.asJsonString(mockDetail)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("User Get All - /api/v1/userprofile  : success")
    public void getAllInventoryItemSuccess() throws Exception{
        List<UserProfileDTO> itemList = new ArrayList<>();
        itemList.add(mockDTO);
        BDDMockito.doReturn(itemList).when(userProfileService).getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/userprofile"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("User Get by Id - /api/v1/userprofile/{id}: success")
    public void getByIdSuccess() throws Exception {
        BDDMockito.doReturn(mockDTO).when(userProfileService).getById("xyz1234");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/userprofile/{id}", "xyz1234"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is("xyz1234")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("Test")));
    }

    @Test
    @DisplayName("User Get by Id - /api/v1/userprofile/{id}: Failed")
    public void getByIdFailed() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(userProfileService).getById("xyz1234");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/userprofile/{id}", "xyz1234"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Get by email /api/v1/userprofile/findByEmail/{email} : success")
    public void getByEmailSuccess() throws Exception {
        BDDMockito.doReturn(mockDTO).when(userProfileService).getByEmail("test@user.com");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/userprofile/findByEmail/{email}", "test@user.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is("xyz1234")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("Test")));
    }

    @Test
    @DisplayName("Get by email /api/v1/userprofile/findByEmail/{email} : fail")
    public void getByEmailFail() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(userProfileService).getByEmail("test@user.com");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/userprofile/findByEmail/{email}", "test@user.com"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("User follow - /api/v1/userprofile/follow : success")
    public void followUserRequestSuccess() throws Exception{
        Map<String, String> followRequest = new HashMap<>();
        followRequest.put("userId", "xyz");
        followRequest.put("followerId", "abc");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/userprofile/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.asJsonString(followRequest)))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @DisplayName("User follow - /api/v1/userprofile/follow : fail")
    public void followUserRequestFailed() throws Exception{
        Map<String, String> followRequest = new HashMap<>();
        followRequest.put("userId", "xyz");
        followRequest.put("followerId", "abc");
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(userProfileService)
                .follow("xyz","abc");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/userprofile/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.asJsonString(followRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
