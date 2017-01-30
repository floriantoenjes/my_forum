package com.floriantoenjes.forum.topic;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

public class TopicControllerTest {
    private MockMvc mockMvc;

    private TopicController topicController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }

    @Test
    public void viewTopicDetailTest() throws Exception {

    }

    @Test
    public void addReply() throws Exception {

    }

    @Test
    public void newTopicForm() throws Exception {

    }

    @Test
    public void addTopic() throws Exception {

    }

}