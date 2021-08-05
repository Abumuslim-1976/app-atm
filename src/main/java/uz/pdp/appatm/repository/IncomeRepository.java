package uz.pdp.appatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatm.entity.Income;

public interface IncomeRepository extends JpaRepository<Income,Integer> {
}
