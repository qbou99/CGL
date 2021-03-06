package com.cgl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "date",
        allowGetters = true)
public class Fichier {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String nom;

    private String chemin;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private Type typeFichier;

    public Fichier() {
        this.nom = "";
        this.date = new Date();
    }

    public Fichier(String chemin, String nom, Type typeFichier) {
        this.chemin = chemin;
        this.typeFichier = typeFichier;
        this.nom = nom;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(Type typeFichier) {
        this.typeFichier = typeFichier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fichier fichier = (Fichier) o;
        return Objects.equals(id, fichier.id) &&
                Objects.equals(nom, fichier.nom) &&
                Objects.equals(chemin, fichier.chemin) &&
                Objects.equals(date, fichier.date) &&
                typeFichier == fichier.typeFichier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, chemin, date, typeFichier);
    }

    @Override
    public String toString() {
        return "Fichier{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", chemin='" + chemin + '\'' +
                ", date=" + date +
                ", typeFichier=" + typeFichier +
                '}';
    }
}
