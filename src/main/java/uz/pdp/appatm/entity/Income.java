package uz.pdp.appatm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Card toCard;

    @ManyToOne
    private Bankomat fromBankomat;

    @Column(nullable = false)
    private double amount;

    private double oneHundredThousand;                                   // yuz ming so`mlik

    private double fiftyThousand;                                         // ellik ming so`mlik

    private double tenThousand;                                           // o`n ming so`mlik

    @CreationTimestamp
    private Timestamp timestamp;

    public Income(Card toCard, Bankomat fromBankomat, double amount, double oneHundredThousand, double fiftyThousand, double tenThousand) {
        this.toCard = toCard;
        this.fromBankomat = fromBankomat;
        this.amount = amount;
        this.oneHundredThousand = oneHundredThousand;
        this.fiftyThousand = fiftyThousand;
        this.tenThousand = tenThousand;
    }
}
