package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostControllerTest {
    private MockMvc mockMvc;

    @Mock
    PostService postService;

    @Mock
    UserService userService;

    @InjectMocks
    private PostController postController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }


    @Test
    public void viewPostFormTest() throws Exception {
        mockMvc.perform(get("/posts/1"))

                .andExpect(status().isOk())
                .andExpect(view().name("post"));
    }

    @Test
    public void updatePost() throws Exception {

    }

}