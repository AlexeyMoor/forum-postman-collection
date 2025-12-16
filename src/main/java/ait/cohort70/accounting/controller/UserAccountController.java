package ait.cohort70.accounting.controller;

import ait.cohort70.accounting.dto.UserDto;
import ait.cohort70.accounting.dto.UserRegisterDto;
import ait.cohort70.accounting.dto.UserRolesDto;
import ait.cohort70.accounting.dto.UserUpdateDto;
import ait.cohort70.accounting.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody UserRegisterDto userRegisterDto) {
        return userAccountService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public UserDto login(Principal principal) {
        return userAccountService.login(principal.getName());
    }

    @GetMapping("/user/{login}")
    public UserDto getUser(@PathVariable String login) {
        return userAccountService.getUser(login);
    }

    @DeleteMapping("/user/{login}")
    public UserDto deleteUser(@PathVariable String login) {
        return userAccountService.deleteUser(login);
    }

    @PatchMapping("/user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto userUpdateDto) {
        return userAccountService.updateUser(login, userUpdateDto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public UserRolesDto addRole(@PathVariable String login, @PathVariable String role) {
        return userAccountService.addRole(login, role);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public UserRolesDto removeRole(@PathVariable String login, @PathVariable String role) {
        return userAccountService.removeRole(login, role);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(Principal principal, @RequestHeader("X-Password") String newPassword) {
        userAccountService.changePassword(principal.getName(), newPassword);
    }
}
