package com.wallet.wallet.models;

import com.wallet.wallet.enums.TypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wallet_items")
public class WalletItem implements Serializable {

    private static final long serialVersionUID = 1125904030607085325L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal value;

    @JoinColumn(name = "wallet", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    public WalletItem(Long id, Date date, TypeEnum type, String description, BigDecimal value, Wallet wallet) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.value = value;
        this.wallet = wallet;
    }

    public WalletItem(Date date, TypeEnum type, String description, BigDecimal value, Wallet wallet) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.value = value;
        this.wallet = wallet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WalletItem that = (WalletItem) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
