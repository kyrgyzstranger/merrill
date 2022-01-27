package com.example.merrill;

import com.example.merrill.model.RegisteredUser;
import com.example.merrill.model.UnregisteredUser;
import com.example.merrill.model.Project;
import com.example.merrill.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MerrillController {

    @Autowired
    private RestTemplate restTemplate;
    public static final String UNREG_USERS_URL = "https://5c3ce12c29429300143fe570.mockapi.io/api/unregisteredusers";
    public static final String REG_USERS_URL = "https://5c3ce12c29429300143fe570.mockapi.io/api/registeredusers";
    public static final String PROJECTS_URL = "https://5c3ce12c29429300143fe570.mockapi.io/api/projectmemberships";

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        ResponseEntity<UnregisteredUser[]> responseForUnregistered =
                restTemplate.getForEntity(UNREG_USERS_URL, UnregisteredUser[].class);
        ResponseEntity<RegisteredUser[]> responseForRegistered =
                restTemplate.getForEntity(REG_USERS_URL, RegisteredUser[].class);
        ResponseEntity<Project[]> responseForProjects =
                restTemplate.getForEntity(PROJECTS_URL, Project[].class);
        UnregisteredUser[] unregisteredUsers = responseForUnregistered.getBody();
        RegisteredUser[] registeredUsers = responseForRegistered.getBody();
        Project[] projectArr = responseForProjects.getBody();
        Collections.addAll(users, Objects.requireNonNull(unregisteredUsers));
        Collections.addAll(users, Objects.requireNonNull(registeredUsers));
        Collections.addAll(projects, Objects.requireNonNull(projectArr));
        Map<String, List<Project>> map = projects.stream().collect(Collectors.groupingBy(Project::getUserId));
        for(User u : users){
            List<String> ids;
            if(map.containsKey(u.getId())) {
                ids = map.get(u.getId()).stream().map(Project::getId).collect(Collectors.toList());
            }else{
                ids = new ArrayList<>();
            }
            u.setProjectIds(ids);
        }
        return users;
    }
}
