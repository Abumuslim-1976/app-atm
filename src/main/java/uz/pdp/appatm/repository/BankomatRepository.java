package uz.pdp.appatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatm.entity.Bankomat;

import java.util.UUID;

public interface BankomatRepository extends JpaRepository<Bankomat,UUID>{

}
