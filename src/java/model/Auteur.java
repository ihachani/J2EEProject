package model;

public class Auteur {

    private int id;
    private String nom;

    public Auteur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Auteur) {
            return (((Auteur) o).getId() == getId());
        }
        return false;
    }

}