package com.cgl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    private String chemin;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    private TypeFichier typeFichier;

    public Fichier() {
        this.nom = "";
        this.chemin = "";
        this.date = new Date();;
        this.typeFichier = null;
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

    public TypeFichier getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(TypeFichier typeFichier) {
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
