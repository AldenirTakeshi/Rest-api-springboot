package br.com.takeshi.spring_boot_rest.controllers;

import br.com.takeshi.spring_boot_rest.data.dto.v2.UserDtoV2;
import br.com.takeshi.spring_boot_rest.service.v2.UserServiceV2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControllerV2 {


    private final UserServiceV2 userServiceV2;

    public UserControllerV2(UserServiceV2 userServiceV2) {
        this.userServiceV2 = userServiceV2;
    }

    @PostMapping("/v2")
    public UserDtoV2 create(@RequestBody UserDtoV2 user){
        return userServiceV2.create(user);
    }
}
