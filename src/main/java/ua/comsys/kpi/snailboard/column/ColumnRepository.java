package ua.comsys.kpi.snailboard.column;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Columns, UUID> {
}
