package com.codedifferently.firebaseapiauthenticationdemo.domain.users.controller;

import com.codedifferently.firebaseapiauthenticationdemo.domain.users.service.UserService;
import com.codedifferently.firebaseapiauthenticationdemo.security.PrincipalDetailsArgumentResolver;
import com.codedifferently.firebaseapiauthenticationdemo.security.models.FireBaseUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    //@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @BeforeEach
    public void setUp(){
        FireBaseUser fireBaseUser = new FireBaseUser();
        fireBaseUser.setEmail("test@user.com");
        fireBaseUser.setUid("xyz");
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(service))
                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver(fireBaseUser))
                .build();
    }

    @Test
    public void getUserInfoTest01() throws Exception {
        BDDMockito.doReturn(true).when(service).doesExist("xyz");
        mockMvc.perform(MockMvcRequestBuilders.get("/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid", is("xyz")))
                .andExpect(jsonPath("$.email", is("test@user.com")));
    }
}
