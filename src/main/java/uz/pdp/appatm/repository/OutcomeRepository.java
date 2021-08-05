package uz.pdp.appatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatm.entity.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
}
