package com.academia.ruleta.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "ruletas")
public class Ruleta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private boolean estado;
    @Column(name = "numero_apuestas")
    private int numeroApuestas;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<Apuesta> apuestas;


    public Ruleta() {
        this.estado = false;
        this.numeroApuestas = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ruleta ruleta = (Ruleta) o;
        return id != null && Objects.equals(id, ruleta.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Ruleta{" +
                "id = " + id +
                ", estado = " + estado +
                ", numeroApuestas = " + numeroApuestas +
                ", apuestas = " + apuestas +
                '}';
    }

    private static final long serialVersionUID = -9177147316639658935L;
}
