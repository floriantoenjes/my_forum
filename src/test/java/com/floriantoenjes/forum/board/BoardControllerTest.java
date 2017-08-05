package com.floriantoenjes.forum.board;

import com.floriantoenjes.forum.topic.Topic;
import com.floriantoenjes.forum.topic.TopicService;
import com.floriantoenjes.forum.user.Role;
import com.floriantoenjes.forum.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BoardControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BoardService boardService;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private BoardController boardController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    public void viewBoardsTest() throws Exception {
        List<Board> boardList = new ArrayList<>();
        boardList.add(new Board("Test Board"));
        when(boardService.findAll()).thenReturn(boardList);

        mockMvc.perform(get("/boards/"))

                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("boards", boardList));
    }

    @Test
    public void viewBoardDetailTest() throws Exception {
        Board board = new Board("Test Board");
        when(boardService.findOne(1L)).thenReturn(board);
        Topic topic = new Topic(new User("user", "password", new Role("ROLE_USER")), "Test Topic");
        List<Topic> topicList = new ArrayList<>();
        topicList.add(topic);
        when(topicService.findByBoardOrderByLastPostDate(board, new PageRequest(0, 10)))
                .thenReturn(new PageImpl<>(topicList));


        mockMvc.perform(get("/boards/1"))

            .andExpect(status().isOk())
            .andExpect(view().name("board"))
            .andExpect(model().attribute("board", board))
            .andExpect(model().attribute("topics", topicList))
            .andExpect(model().attribute("currentPage", 0));
    }

}