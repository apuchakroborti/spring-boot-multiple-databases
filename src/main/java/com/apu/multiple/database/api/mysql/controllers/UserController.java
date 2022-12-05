package com.apu.multiple.database.api.mysql.controllers;

import com.apu.multiple.database.api.mysql.dto.UserDto;
import com.apu.multiple.database.api.mysql.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public UserDto addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @GetMapping("/findUsers")
    public List<UserDto> findUsers(){
        return userService.getUsers();
    }
}
