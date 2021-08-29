package ua.comsys.kpi.snailboard.ticket.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ua.comsys.kpi.snailboard.board.model.Board;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ticket_number")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketNumber {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    @Column
    private Integer number;

}
