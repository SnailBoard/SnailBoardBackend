package ua.comsys.kpi.snailboard.role.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.comsys.kpi.snailboard.user.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "roles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Roles name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
