package ait.cohort70.accounting.service;

import ait.cohort70.accounting.dto.RolesDto;
import ait.cohort70.accounting.dto.UserDto;
import ait.cohort70.accounting.dto.UserEditDto;
import ait.cohort70.accounting.dto.UserRegisterDto;

public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);

    UserDto getUser(String login);

    UserDto removeUser(String login);

    UserDto updateUser(String login, UserEditDto userEditDto);

    RolesDto changeRolesList(String login, String role, boolean isAddRole);

    void changePassword(String login, String newPassword);
}
