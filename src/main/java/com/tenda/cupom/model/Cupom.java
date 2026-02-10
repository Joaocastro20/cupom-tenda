package com.tenda.cupom.model;

import com.tenda.cupom.enums.CupomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cupom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true, length = 6)
    private String code;

    @NotNull
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    @DecimalMin("0.5")
    private BigDecimal discountValue;

    @NotNull
    @Column(nullable = false)
    @Future
    private OffsetDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column
    private CupomStatus status;

    @Column
    private boolean published;

    @Column
    private boolean redeemed;

    public Cupom(String code, String description, BigDecimal discountValue, OffsetDateTime expirationDate) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.status = CupomStatus.ACTIVE;
        this.published = false;
        this.redeemed = false;
    }
}
