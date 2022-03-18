package com.cgl.controller;

import com.cgl.dto.FichierDto;
import com.cgl.exception.ResourceNotFoundException;
import com.cgl.model.Fichier;
import com.cgl.model.Type;
import com.cgl.repository.FichierRepository;
import com.cgl.repository.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FichierControllerTest {

    @InjectMocks
    FichierController fichierController;

    @Mock
    FichierRepository fichierRepository;

    @Mock
    TypeRepository typeRepository;

    @Captor
    ArgumentCaptor<Fichier> fichierArgumentCaptor;

    @Test
    void givenFichiers_whenGetAll_thenFichiers() {
        Fichier f1 = new Fichier("/user/fichier1", "Fichier 1", new Type("Image"));
        Fichier f2 = new Fichier("/user/fichier2", "Fichier 2", new Type("Audio"));
        List<Fichier> fichiers = Arrays.asList(f1, f2);

        when(fichierRepository.findAll()).thenReturn(fichiers);

        List<Fichier> result = fichierController.getAllFichiers();

        assertThat(result).hasSize(fichiers.size());
        assertThat(result.get(0).getChemin()).isEqualTo(f1.getChemin());
        assertThat(result.get(1).getChemin()).isEqualTo(f2.getChemin());
    }

    @Test
    void givenFichier1_whenGet1_thenFichier1() {
        Fichier f1 = new Fichier("/user/fichier1", "Fichier 1", new Type("Image"));

        when(fichierRepository.findById(1L)).thenReturn(Optional.of(f1));

        Fichier result = fichierController.getFichierById(1L);

        assertThat(result.getChemin()).isEqualTo(f1.getChemin());
    }

    @Test
    void givenUnknownFichierId_whenGetById_thenException() {
        when(fichierRepository.findById(1337L)).thenReturn(Optional.empty());

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> fichierController.getFichierById(1337L));

        assertThat(e.getResourceName()).isEqualTo("Fichier");
        assertThat(e.getFieldName()).isEqualTo("id");
        assertThat(e.getFieldValue()).isEqualTo(1337L);
    }

    @Test
    void givenFichier_whenSave_thenFichierSaved() {
        Type t = new Type("Audio");
        FichierDto f = new FichierDto("/user/fichier", "Fichier 1", t);

        when(fichierRepository.save(any(Fichier.class))).thenReturn(f.dtoToEntity());
        when(typeRepository.findByNom("Audio")).thenReturn(Optional.of(t));

        RedirectView result = fichierController.archiveFichier(new File("/user/fichier"), "Fichier 1", "Audio");

        verify(fichierRepository).save(fichierArgumentCaptor.capture());

        assertThat(fichierArgumentCaptor.getValue().getNom()).isEqualTo(f.getNom());
    }

    @Test
    void givenFichier_whenDelete_thenFichierDeleted() {
        Fichier f1 = new Fichier("/user/fichier1", "Fichier 1", new Type("Image"));

        when(fichierRepository.findById(1L)).thenReturn(Optional.of(f1));

        RedirectView result = fichierController.deleteFichier(1L);

        verify(fichierRepository).delete(fichierArgumentCaptor.capture());

        assertThat(fichierArgumentCaptor.getValue().getNom()).isEqualTo(f1.getNom());
    }

}