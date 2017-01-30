package com.floriantoenjes.forum.topic;

import com.floriantoenjes.forum.board.BoardService;
import com.floriantoenjes.forum.post.Post;
import com.floriantoenjes.forum.user.Role;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class TopicControllerTest {
    private MockMvc mockMvc;

    private User user;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicController topicController;

    @Before
    public void setUp() throws Exception {
        user = userService.findByUsername("user1");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }

    @Test
    public void viewTopicDetailTest() throws Exception {
        Topic topic = new Topic(user, "Test Topic");
        Post post = new Post(user, "Test Post");
        topic.addPost(post);
        topicService.save(topic);

        mockMvc.perform(get("/topics/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("topic"))
                .andExpect(model().attribute("topic", any(Topic.class)));
    }

    @Test
    public void addReplyTest() throws Exception {
        final String TEXT = "Test post";

        mockMvc.perform(post("/topics/1")
        .param("id" , "")
        .param("text", TEXT))
        .andExpect(redirectedUrl("/topics/1"));
        Topic topic = topicService.findOne(1L);
        Post post = topic.getPosts().get(topic.getPosts().size() - 1);
        assertEquals(post.getText(), TEXT);
    }

    @Test
    public void newTopicFormTest() throws Exception {
        mockMvc.perform(get("/topics/add")
                .param("boardId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("topic_form"));

    }

    @Test
    public void newTopicFormWithoutUserTest() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));

        mockMvc.perform(get("/topics/add")
                .param("boardId", "1"))
                .andExpect(redirectedUrl("/boards/1"));
    }

    @Test
    public void addTopic() throws Exception {

    }

}