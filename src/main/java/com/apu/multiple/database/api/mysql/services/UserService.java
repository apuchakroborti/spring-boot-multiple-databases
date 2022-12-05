package com.apu.multiple.database.api.mysql.services;
import java.util.*;

import com.apu.multiple.database.api.mysql.dto.UserDto;

public interface UserService {
    UserDto addUser(UserDto bookDto);
    List<UserDto> getUsers();

}
