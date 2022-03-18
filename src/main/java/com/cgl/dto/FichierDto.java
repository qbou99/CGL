package com.cgl.dto;

import com.cgl.model.Fichier;
import com.cgl.model.TypeFichier;
import org.springframework.core.io.InputStreamSource;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class FichierDto {

    @NotBlank
    private String nom;

    private String chemin;

    @NotNull
    private TypeFichier typeFichier;

    public FichierDto() {
        this.nom = "";
        this.chemin = "";
    }

    public FichierDto(String chemin, String nom, TypeFichier type) {
        this.nom = nom;
        this.chemin = chemin;
        this.typeFichier = type;
    }

    public Fichier dtoToEntity() {
        Fichier fichier = new Fichier();
        fichier.setNom(this.getNom());
        fichier.setChemin(this.getChemin());
        fichier.setTypeFichier(this.getTypeFichier());
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

    public TypeFichier getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(TypeFichier typeFichier) {
        this.typeFichier = typeFichier;
    }
}
