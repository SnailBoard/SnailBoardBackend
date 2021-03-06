package ua.comsys.kpi.snailboard.statistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, UUID> {
}
