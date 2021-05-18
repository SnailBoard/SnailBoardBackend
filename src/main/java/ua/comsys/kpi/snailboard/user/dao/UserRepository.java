package ua.comsys.kpi.snailboard.user.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    List<User> findAllByUsernameContaining(String username, Pageable pageable);

    List<User> findByUsernameContaining(String username);
}
