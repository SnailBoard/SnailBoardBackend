package ua.comsys.kpi.snailboard.notification;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ua.comsys.kpi.snailboard.user.model.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String message;

    @Column
    private String srcUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User usr;
}
