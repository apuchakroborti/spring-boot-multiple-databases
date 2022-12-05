package com.apu.multiple.database.api.mysql.services;

import com.apu.multiple.database.api.mysql.dto.UserDto;
import com.apu.multiple.database.api.mysql.entity.User;
import com.apu.multiple.database.api.mysql.repository.UserRepository;
import com.apu.multiple.database.api.mysql.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpls implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserUtils.dtoToEntityUser(userDto);
        user = userRepository.save(user);
        return UserUtils.entityToDtoUser(user);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserUtils::entityToDtoUser)
                .collect(Collectors.toList());

    }
}
