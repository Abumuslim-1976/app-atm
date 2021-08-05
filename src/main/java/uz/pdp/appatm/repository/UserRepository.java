package uz.pdp.appatm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appatm.entity.User;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(@Email String email);

    boolean existsByEmail(@Email String email);
}
