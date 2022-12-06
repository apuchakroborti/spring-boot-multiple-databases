package com.apu.multiple.database.api.mysql.util;

import com.apu.multiple.database.api.mysql.dto.UserDto;
import com.apu.multiple.database.api.mysql.models.User;

public class UserUtils {
    public static User dtoToEntityUser(UserDto userDto){
        return new User(userDto.getId(), userDto.getFirstName(), userDto.getLastName());
    }
    public static UserDto entityToDtoUser(User user){
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName());
    }
}
