package model;

import java.util.ArrayList;
import java.util.Date;

public class Livre {

    private String cover;
    private Date dateAjout;
    private int dayPublication;
    private int monthPublication;
    private int yearPublication;
    private int views;
    private String description;
    private String isbn;
    private String langue;
    private String titre;
    private Category category;
    private ArrayList<Auteur> auteurs;
    private Utilisateur utilisateur;
    private int state;
    
    public Livre(String cover, Date dateAjout, Date datePublication, String description, String isbn, String langue, String titre, Category category, ArrayList<Auteur> auteurs, Utilisateur utilisateur, int state, int views) {
        this.cover = cover;
        this.dateAjout = dateAjout;
        this.dayPublication = datePublication.getDay();
        this.monthPublication = datePublication.getMonth();
        this.yearPublication = datePublication.getYear();
        this.description = description;
        this.isbn = isbn;
        this.langue = langue;
        this.titre = titre;
        this.category = category;
        this.auteurs = auteurs;
        this.utilisateur = utilisateur;
        this.state = state;
        this.views = views;
    }

    public ArrayList<Auteur> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(ArrayList<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Date getDatePublication() {
        return new Date(getYearPublication(), getMonthPublication(), getDayPublication());
    }

    public void setDatePublication(Date datePublication) {
        this.dayPublication = datePublication.getDay();
        this.monthPublication = datePublication.getMonth();
        this.yearPublication = datePublication.getYear();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDayPublication() {
        return dayPublication;
    }

    public void setDayPublication(int dayPublication) {
        this.dayPublication = dayPublication;
    }

    public int getMonthPublication() {
        return monthPublication;
    }

    public void setMonthPublication(int monthPublication) {
        this.monthPublication = monthPublication;
    }

    public int getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(int yearPublication) {
        this.yearPublication = yearPublication;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void addView () {
        this.views = this.views + 1;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Livre) {
            return (((Livre) o).getIsbn().equals( getIsbn()));
        }
        return false;
    }

}