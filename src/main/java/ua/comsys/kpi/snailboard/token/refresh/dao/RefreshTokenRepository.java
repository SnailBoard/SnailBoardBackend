package ua.comsys.kpi.snailboard.token.refresh.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.token.refresh.model.RefreshToken;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByEmail(String email);
}
