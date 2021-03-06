package ua.comsys.kpi.snailboard.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.comsys.kpi.snailboard.board.Board;
import ua.comsys.kpi.snailboard.user.User;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "statistic")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistic {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private Integer time;

    @Column
    private Integer tasks;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User usr;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;
}
