package com.floriantoenjes.forum.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BoardControllerTest {
    private MockMvc mockMvc;

    @Mock
    BoardService boardService;

    @InjectMocks
    BoardController boardController;

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
                .andExpect(view().name("index"))
                .andExpect(model().attribute("boards", boardList));
    }

    @Test
    public void board() throws Exception {

    }

}