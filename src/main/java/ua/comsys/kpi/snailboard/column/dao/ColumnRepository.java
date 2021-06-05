package ua.comsys.kpi.snailboard.column.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.column.model.Columns;

import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Columns, UUID> {
}
