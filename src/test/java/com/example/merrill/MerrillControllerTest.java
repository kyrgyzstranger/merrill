package com.example.merrill;

import com.example.merrill.model.Project;
import com.example.merrill.model.RegisteredUser;
import com.example.merrill.model.UnregisteredUser;
import com.example.merrill.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MerrillControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MerrillController controller;

    @Test
    public void getUsersTest(){
        UnregisteredUser unregisteredUser = new UnregisteredUser();
        unregisteredUser.setId("1");
        UnregisteredUser[] unregUsers = {unregisteredUser};
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId("2");
        RegisteredUser[] regUsers = {registeredUser};
        Project project = new Project();
        project.setProjectId("4");
        project.setUserId("1");
        Project[] projects = {project};
        Mockito.when(restTemplate.getForEntity(MerrillController.UNREG_USERS_URL, UnregisteredUser[].class))
                .thenReturn(new ResponseEntity<>(unregUsers, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(MerrillController.REG_USERS_URL, RegisteredUser[].class))
                .thenReturn(new ResponseEntity<>(regUsers, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(MerrillController.PROJECTS_URL, Project[].class))
                .thenReturn(new ResponseEntity<>(projects, HttpStatus.OK));
        List<User> users = controller.getUsers();
        Assert.assertEquals(users.size(), 2);
        Assert.assertFalse(unregisteredUser.getProjectIds().isEmpty());
        Assert.assertTrue(registeredUser.getProjectIds().isEmpty());
    }

}
