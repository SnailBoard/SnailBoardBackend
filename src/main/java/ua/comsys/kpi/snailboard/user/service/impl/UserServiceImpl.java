package ua.comsys.kpi.snailboard.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.role.dao.RoleRepository;
import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.role.model.Roles;
import ua.comsys.kpi.snailboard.user.dao.UserRepository;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.exception.UserExistsException;
import ua.comsys.kpi.snailboard.user.exception.UserNotFoundException;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_ALREADY_EXISTS = "User {} already exists";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    Converter<User, UserInfoDto> userUserInfoDtoConverter;

    @Override
    public User createUser(User user) {
        validateUserIsNew(user);
        Role userRole = roleRepository.findByCode(Roles.ROLE_USER)
                .orElseThrow(IllegalStateException::new);
        user.setRoles(Collections.singletonList(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void validateUserIsNew(User user) {
        validateEmailIsNew(user.getEmail());
        validateUsernameIsNew(user.getUsername());
    }

    private void validateEmailIsNew(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            LOG.warn(USER_ALREADY_EXISTS, email);
            throw new UserExistsException();
        }
    }

    private void validateUsernameIsNew(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            LOG.warn(USER_ALREADY_EXISTS, username);
            throw new UserExistsException();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }


    @Override
    public UserInfoDto getUserInfoByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userUserInfoDtoConverter::convert)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> getUsersByUsername(String username) {
        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public List<User> getUsersByUsernameWithLimit(String username, Integer limit) {
        return userRepository.findAllByUsernameContaining(username, PageRequest.of(0, limit));
    }
}
