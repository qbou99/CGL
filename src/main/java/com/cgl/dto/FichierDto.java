package com.cgl.dto;

import com.cgl.model.Fichier;
import com.cgl.model.Type;

import javax.validation.constraints.NotBlank;

public class FichierDto {

    @NotBlank
    private String nom;

    private String chemin;

    private Type type;


    public FichierDto() {
        this.nom = "";
        this.chemin = "";
        this.type = new Type("Inconnu");
    }

    public FichierDto(String chemin, String nom, Type type) {
        this.nom = nom;
        this.chemin = chemin;
        this.type = type;
    }

    public Fichier dtoToEntity() {
        Fichier fichier = new Fichier();
        fichier.setNom(this.getNom());
        fichier.setChemin(this.getChemin());
        fichier.setTypeFichier(this.getType());
        return fichier;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
