package ait.cohort70.accounting.service;

import ait.cohort70.accounting.dao.UserAccountRepository;
import ait.cohort70.accounting.dto.RolesDto;
import ait.cohort70.accounting.dto.UserDto;
import ait.cohort70.accounting.dto.UserEditDto;
import ait.cohort70.accounting.dto.UserRegisterDto;
import ait.cohort70.accounting.dto.exception.InvalidDataException;
import ait.cohort70.accounting.dto.exception.UserExistsException;
import ait.cohort70.accounting.dto.exception.UserNotFoundException;
import ait.cohort70.accounting.model.Role;
import ait.cohort70.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService, CommandLineRunner {
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
            throw new UserExistsException();
        }
        UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
        userAccount.addRole("USER");
        String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        userAccount.setPassword(encodedPassword);
        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto getUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto removeUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        userAccountRepository.delete(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UserEditDto userEditDto) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        boolean flag = false;
        if (userEditDto.getFirstName() != null && !userEditDto.getFirstName().equals(userAccount.getFirstName())) {
            userAccount.setFirstName(userEditDto.getFirstName());
            flag = true;
        }
        if (userEditDto.getLastName() != null && !userEditDto.getLastName().equals(userAccount.getLastName())) {
            userAccount.setLastName(userEditDto.getLastName());
            flag = true;
        }
        if (flag) {
            userAccountRepository.save(userAccount);
        }
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        boolean flag;
        try {
            if (isAddRole) {
                flag = userAccount.addRole(role);
            } else {
                flag = userAccount.removeRole(role);
            }
        } catch (Exception e) {
            throw new InvalidDataException();
        }
        if (flag) {
            userAccountRepository.save(userAccount);
        }
        return modelMapper.map(userAccount, RolesDto.class);
    }

    @Override
    public void changePassword(String login, String newPassword) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        String encodedPassword = passwordEncoder.encode(newPassword);
        userAccount.setPassword(encodedPassword);
        userAccountRepository.save(userAccount);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userAccountRepository.existsById("admin")) {
            UserAccount admin = UserAccount.builder()
                    .login("admin")
                    .password(passwordEncoder.encode("admin"))
                    .firstName("Admin")
                    .lastName("Admin")
                    .role(Role.USER)
                    .role(Role.MODERATOR)
                    .role(Role.ADMINISTRATOR)
                    .build();
            userAccountRepository.save(admin);
        }
    }
}

