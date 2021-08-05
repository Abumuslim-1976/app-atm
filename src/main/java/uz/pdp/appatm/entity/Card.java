package uz.pdp.appatm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appatm.entity.enums.BankName;
import uz.pdp.appatm.entity.enums.Permissions;
import uz.pdp.appatm.entity.enums.PlasticType;
import uz.pdp.appatm.entity.template.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Card extends AbstractEntity implements UserDetails {

    private double balance;                                 // karta ichidagi pul

    @Column(nullable = false, unique = true)
    private String specialNumber;                           // card ning 16 xonalik maxsus raqami

    @Enumerated(value = EnumType.STRING)
    private BankName bankName;                              // qaysi bankka tegishli ekanligi

    @Column(nullable = false)
    private LocalDate localDate;                            // card ni amal qilish muddati

    @Column(nullable = false)
    private String username;                                // card ning usernamesi

    @Column(nullable = false)
    private Integer specialCode;                             // 4 xonali maxsus kod

    @Enumerated(value = EnumType.STRING)
    private PlasticType plasticType;                        // plastik turi (HUMO , UZCARD , VISA)

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;                                  // kartani egasi

    private String cvvCode;                                 // 3 xonali cvv kodi

    private boolean active = true;                          // kartaning holati

    @ManyToOne(fetch = FetchType.LAZY)
    private Roles role;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permissions> permissions = role.getPermissions();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permissions permission : permissions) {
            grantedAuthorities.add((GrantedAuthority) permission::name);
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.specialCode.toString();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    public Card(double balance, String specialNumber, BankName bankName, LocalDate localDate, String username, Integer specialCode, PlasticType plasticType, Client client) {
        this.balance = balance;
        this.specialNumber = specialNumber;
        this.bankName = bankName;
        this.localDate = localDate;
        this.username = username;
        this.specialCode = specialCode;
        this.plasticType = plasticType;
        this.client = client;
    }
}
