package com.cgl.dto;

import com.cgl.model.Fichier;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class FichierDto {
    private String nom;

    private String chemin;

    private String date;

    private String typeFichier;

    public Fichier dtoToEntity() {
        return new Fichier();
    }
}
