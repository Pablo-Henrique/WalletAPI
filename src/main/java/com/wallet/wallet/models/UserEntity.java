package com.wallet.wallet.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "USER")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3613421546601364180L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = true)
    private UUID id;

    @Column(name = "NAME", length = 30, nullable = false)
    private String name;

    @Column(name = "PASSWORD", length = 15, nullable = false)
    private String password;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 30)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
