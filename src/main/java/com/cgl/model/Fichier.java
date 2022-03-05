package com.cgl.model;

import javax.persistence.*;

@Entity
public class Fichier {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String path;

    public Fichier(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Fichier() {
        this.name = "";
        this.path = "";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fichier fichier = (Fichier) o;

        if (!id.equals(fichier.id)) return false;
        if (!name.equals(fichier.name)) return false;
        return path.equals(fichier.path);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + path.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Fichier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
