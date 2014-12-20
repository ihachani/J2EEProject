package model;

import java.util.Date;

public class Note {

    private Livre livre;
    private Date date;
    private int id;
    private int rate;
    private String review;
    private Utilisateur utilisateur;

    public Note(Livre livre, Date date, int id, int rate, String review, Utilisateur utilisateur) {
        this.livre = livre;
        this.date = date;
        this.id = id;
        this.rate = rate;
        this.review = review;
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

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public int getRate() {
        return new Integer((new Double(Math.ceil((double) rate ))).intValue()) ;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Note) {
            return (((Note) o).getId() == getId());
        }
        return false;
    }

	
}