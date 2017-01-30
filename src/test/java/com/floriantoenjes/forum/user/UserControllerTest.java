package com.floriantoenjes.forum.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void loginFormTest() throws Exception {
        mockMvc.perform(get("/login"))

                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void viewMembersTest() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User("test_user", "password", new Role("ROLE_USER")));
        when(userService.findAll()).thenReturn(userList);

        mockMvc.perform(get("/members"))

                .andExpect(view().name("members"))
                .andExpect(model().attribute("users", userList));
    }

}