package uz.pdp.appatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatm.entity.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);
}
