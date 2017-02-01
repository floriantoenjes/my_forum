package com.floriantoenjes.forum.web;

import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.post.PostService;
import com.floriantoenjes.forum.user.Role;
import com.floriantoenjes.forum.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SearchControllerTest {
    private MockMvc mockMvc;
    private User user;

    @Mock
    private PostService postService;

    @InjectMocks
    private SearchController searchController;

    @Before
    public void setUp() throws Exception {
        user = new User("test_user", "password", new Role("ROLE_USER"));
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void viewSearchFormTest() throws Exception {
        mockMvc.perform(get("/search/form"))

                .andExpect(status().isOk())
                .andExpect(view().name("search_form"));
    }

    @Test
    public void viewSearchResultsTest() throws Exception {
        List<Post> postList = new ArrayList<>();
        Post post = new Post(user, "Test query");
        postList.add(post);
        Page<Post> page = new PageImpl<>(postList);
        when(postService.findAll()).thenReturn(postList);

        mockMvc.perform(get("/search/results")
        .param("query", "Test query"))

        .andExpect(model().attribute("results", postList))
        .andExpect(view().name("search_results"));
    }

}