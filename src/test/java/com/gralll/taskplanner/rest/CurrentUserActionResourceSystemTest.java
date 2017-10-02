package com.gralll.taskplanner.rest;

import com.gralll.taskplanner.domain.Category;
import com.gralll.taskplanner.domain.Status;
import com.gralll.taskplanner.rest.AuthenticationResource.JWTToken;
import com.gralll.taskplanner.rest.props.TestUserProperties;
import com.gralll.taskplanner.service.dto.LoginDto;
import com.gralll.taskplanner.service.dto.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties(TestUserProperties.class)
@ActiveProfiles(profiles = {"dev", "test"})
public class CurrentUserActionResourceSystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestUserProperties testUserProperties;

    @Test
    public void shouldCreateTaskForCurrentUser() {
        //given
        final TaskDto taskDto = getDummyTask();

        //when
        HttpEntity<Object> entity = new HttpEntity<>(taskDto, getHeadersWithToken());
        ResponseEntity<TaskDto> response = this.restTemplate.postForEntity("/tasks", entity, TaskDto.class);

        //then
        TaskDto createdTask = response.getBody();
        assertNotNull(createdTask.getUserId());
        assertNotNull(createdTask.getId());
        assertThatEqualsDummyTask(createdTask);
    }

    @Test
    public void shouldGetAllTasksFromCurrentUser() {
        //given
        final TaskDto dummyTask = getDummyTask();
        final HttpHeaders headersWithToken = getHeadersWithToken();
        final HttpEntity<Object> entityForCreate = new HttpEntity<>(dummyTask, headersWithToken);
        this.restTemplate.postForEntity("/tasks", entityForCreate, TaskDto.class);

        //when
        HttpEntity<Object> entityForGet = new HttpEntity<>(null, headersWithToken);
        ResponseEntity<List<TaskDto>> response = this.restTemplate.exchange(
                "/tasks",
                HttpMethod.GET,
                entityForGet,
                new ParameterizedTypeReference<List<TaskDto>>() {});

        //then
        List<TaskDto> tasks = response.getBody();
        assertNotNull(tasks);
        TaskDto task = tasks.get(0);
        assertNotNull(task.getId());
        assertThatEqualsDummyTask(task);
    }

    private void assertThatEqualsDummyTask(TaskDto task) {
        TaskDto dummyTask = getDummyTask();
        assertThat(task.getActive(), is(dummyTask.getActive()));
        assertThat(task.getCategory(), is(dummyTask.getCategory()));
        assertThat(task.getName(), is(dummyTask.getName()));
        assertThat(task.getStatus(), is(dummyTask.getStatus()));
    }

    private TaskDto getDummyTask() {
        final TaskDto taskDto = new TaskDto();
        taskDto.setActive(true);
        taskDto.setCategory(Category.BUY.toString());
        taskDto.setStatus(Status.IN_PROGRESS.toString());
        taskDto.setName("Buy something");
        return taskDto;
    }

    private HttpHeaders getHeadersWithToken() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<JWTToken> responseEntity = authenticate(new LoginDto(testUserProperties.getLogin(), testUserProperties.getPassword()));
        String idToken = responseEntity.getBody().getIdToken();
        httpHeaders.set("Authorization", "Bearer " + idToken);
        return httpHeaders;
    }

    private ResponseEntity<JWTToken> authenticate(LoginDto loginDto) {
        return this.restTemplate.postForEntity(
                        "/authenticate",
                        loginDto,
                        JWTToken.class);
    }

}