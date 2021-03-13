package ua.comsys.kpi.snailboard.role.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.role.model.Roles;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(Roles name);
}
