package uz.pdp.appatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatm.entity.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Integer> {

    Optional<Roles> findByName(String name);
}
