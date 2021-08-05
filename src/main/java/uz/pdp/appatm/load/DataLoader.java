package uz.pdp.appatm.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.appatm.entity.Roles;
import uz.pdp.appatm.entity.User;
import uz.pdp.appatm.entity.enums.Permissions;
import uz.pdp.appatm.entity.enums.RoleName;
import uz.pdp.appatm.repository.RoleRepository;
import uz.pdp.appatm.repository.UserRepository;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;


    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;


    @Override
    public void run(String... args) throws Exception {

        if (initialMode.equals("always")) {
            Permissions[] values = Permissions.values();

            Roles admin = roleRepository.save(new Roles(
                    RoleName.DIRECTOR.name(),
                    Arrays.asList(values)
            ));

            Roles worker = roleRepository.save(new Roles(
                    RoleName.CARD_WORKER.name(),
                    Arrays.asList(Permissions.ADD_CARD, Permissions.EDIT_CARD, Permissions.VIEW_CARD)
            ));

            userRepository.save(new User(
                    "Akbarov",
                    "Islom",
                    passwordEncoder.encode("1234"),
                    "akbarjon@gmail.com",
                    roleRepository.findByName(admin.getName()).orElseThrow(() -> new NullPointerException("Role name topilmadi")))
            );

            userRepository.save(new User(
                    "Ismatov",
                    "Nurali",
                    passwordEncoder.encode("222333"),
                    "nurali123@gmail.com",
                    roleRepository.findByName(worker.getName()).orElseThrow(() -> new NullPointerException("Role name topilmadi")))
            );

        }

    }


}
