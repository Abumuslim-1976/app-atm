package uz.pdp.appatm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appatm.entity.enums.PlasticType;
import uz.pdp.appatm.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bankomat extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    private PlasticType plasticType;                                // qaysi turdagi plastik kartochka(HUMO,UZCARD,VISA)

    @Column(nullable = false)
    private long maxMoney;                                          // bankamatdan max pul yechish miqdori

    @Column(nullable = false)
    private long readyMoney;                                        // bankdagi naqd pul miqdori

    private Double plasticMoney = 0.0;                          // card dan yechib olingan pul saqlanadigan joy

    private Integer oneHundredThousandCount;                         // yuz ming so`mlik

    private Integer fiftyThousandCount;                              // ellik ming so`mlik

    private Integer tenThousandCount;                                // o`n ming so`mlik


    public Bankomat(Integer oneHundredThousandCount, Integer fiftyThousandCount, Integer tenThousandCount) {
        this.oneHundredThousandCount = oneHundredThousandCount;
        this.fiftyThousandCount = fiftyThousandCount;
        this.tenThousandCount = tenThousandCount;
    }
}
