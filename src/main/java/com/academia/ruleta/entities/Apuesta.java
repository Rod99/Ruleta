package com.academia.ruleta.entities;

import com.academia.ruleta.enums.Color;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apuestas")
public class Apuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    @Max(value = 36, message = "La cantidad debe ser menor o igual a 36")
    private Integer numero;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    @Max(value = 10000, message = "La cantidad debe ser menor o igual a 10000")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Apuesta apuesta = (Apuesta) o;
        return id != null && Objects.equals(id, apuesta.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private static final long serialVersionUID = 7720236069843615207L;

}
