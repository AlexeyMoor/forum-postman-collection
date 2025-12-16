package ait.cohort70.accounting.service;

import ait.cohort70.accounting.dto.UserDto;
import ait.cohort70.accounting.dto.UserRegisterDto;
import ait.cohort70.accounting.dto.UserRolesDto;
import ait.cohort70.accounting.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);

    UserDto login(String login);

    UserDto getUser(String login);

    UserDto deleteUser(String login);

    UserDto updateUser(String login, UserUpdateDto userUpdateDto);

    UserRolesDto addRole(String login, String role);

    UserRolesDto removeRole(String login, String role);

    void changePassword(String login, String newPassword);
}
