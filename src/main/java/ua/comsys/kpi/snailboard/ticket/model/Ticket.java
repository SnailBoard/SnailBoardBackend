package ua.comsys.kpi.snailboard.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.comsys.kpi.snailboard.column.Columns;
import ua.comsys.kpi.snailboard.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column
    private String text;

    @Column
    private Integer number;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
    private List<TicketHistory> ticketHistories = new ArrayList<>();

    @ManyToMany(mappedBy = "tickets")
    private List<User> users = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "column_id")
    private Columns column;
}
