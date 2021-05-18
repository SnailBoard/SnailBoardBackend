package ua.comsys.kpi.snailboard.column;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ua.comsys.kpi.snailboard.board.model.Board;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "columns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Columns {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String code;

    @Column
    private LocalDate createdAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

}
