package com.floriantoenjes.forum.post;

import com.floriantoenjes.forum.file.StorageService;
import com.floriantoenjes.forum.topic.Topic;
import com.floriantoenjes.forum.user.Role;
import com.floriantoenjes.forum.user.User;
import com.floriantoenjes.forum.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.io.FileInputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PostControllerTest {
    private final String TEST_TEXT = "Test text.";
    private MockMvc mockMvc;
    private User user;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private Validator validator;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private PostController postController;

    @Before
    public void setUp() throws Exception {
        user = new User("test_user", "password", new Role("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void viewPostFormTest() throws Exception {
        Post post = createMockPost();
        when(postService.findOne(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))

                .andExpect(status().isOk())
                .andExpect(view().name("post_form"))
                .andExpect(model().attribute("post", post));
    }

    @Test
    public void updatePostTest() throws Exception {
        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        Topic topic = new Topic(user, "Test Topic");
        Post post = createMockPost();
        topic.addPost(post);
        when(postService.findOne(1L)).thenReturn(post);
        MockMultipartFile image = new MockMultipartFile("file", "image.jpg",
                "image/jpeg", new FileInputStream("upload-dir/test/298219102.PNG"));

        mockMvc.perform(fileUpload("/posts/1")
                .file(image)
                .param("text", TEST_TEXT))

                .andExpect(redirectedUrlPattern("/topics/*"));
    }

    // ToDo: Write a test for failed post validation

    public Post createMockPost() {
        return new Post(user, TEST_TEXT);
    }
}