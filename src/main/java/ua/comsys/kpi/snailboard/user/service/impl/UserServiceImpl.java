package ua.comsys.kpi.snailboard.user.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.comsys.kpi.snailboard.role.dao.RoleRepository;
import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.role.model.Roles;
import ua.comsys.kpi.snailboard.user.dao.UserRepository;
import ua.comsys.kpi.snailboard.user.exception.UserExistsException;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

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

    @Override
    public User createUser(User userEntity) {
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            LOG.warn(USER_ALREADY_EXISTS, userEntity.getEmail());
            throw new UserExistsException();
        }
        Role userRole = roleRepository.findByCode(Roles.ROLE_USER)
                .orElseThrow(IllegalStateException::new);
        userEntity.setRoles(Collections.singletonList(userRole));
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent() &&
                passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
