package model;

import java.util.Date;

public class DemandeLivre {

    private Date date;
    private int id;
    private String titre;
    private Utilisateur utilisateur;
    
    public DemandeLivre(Date date, int id, String titre, Utilisateur utilisateur) {
        this.date = date;
        this.id = id;
        this.titre = titre;
        this.utilisateur = utilisateur;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DemandeLivre) {
            return (((DemandeLivre) o).getId() == getId());
        }
        return false;
    }

}