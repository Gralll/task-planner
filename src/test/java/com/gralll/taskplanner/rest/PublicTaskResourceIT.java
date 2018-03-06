package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.domain.Category;
import com.gralll.taskplanner.domain.Status;
import com.gralll.taskplanner.domain.User;
import com.gralll.taskplanner.service.TaskService;
import com.gralll.taskplanner.service.UserService;
import com.gralll.taskplanner.service.dto.TaskDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PublicTaskResource.class)
@ActiveProfiles(profiles = {"dev", "test"})
@EnableSpringDataWebSupport
public class PublicTaskResourceIT {

    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TaskService taskService;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final PublicTaskResource publicTaskResource = new PublicTaskResource(taskService, userService);
        this.mvc = MockMvcBuilders.standaloneSetup(publicTaskResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void shouldReturnFirstTenTasksByUserId() throws Exception {
        //given
        List<TaskDto> taskDtos = Arrays.asList(getDummyTask(), getDummyTask());
        given(this.taskService.findAllByUserId(anyLong(), any()))
                .willReturn(new PageImpl<>(taskDtos));
        given(this.userService.findOneById(anyLong()))
                .willReturn(Optional.of(new User()));
        this.mvc.perform(get("/users/50/tasks")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "ASC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{},{}]"));
    }

    private TaskDto getDummyTask() {
        final TaskDto taskDto = new TaskDto();
        taskDto.setActive(true);
        taskDto.setCategory(Category.BUY.toString());
        taskDto.setStatus(Status.IN_PROGRESS.toString());
        taskDto.setName("Buy something");
        taskDto.setUserId(50L);
        return taskDto;
    }
}