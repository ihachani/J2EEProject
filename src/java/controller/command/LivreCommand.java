package controller.command;

import java.util.ArrayList;
import java.util.Date;
import model.Auteur;
import model.Category;
import model.Utilisateur;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class LivreCommand {
    private CommonsMultipartFile file;
    private String dayPublication;
    private String monthPublication;
    private String yearPublication;
    private String description;
    private String isbn;
    private String langue;
    private String titre;
    private String categoryID;
    private String auteurs;

    public ArrayList<String> getAuteurs() {
        String sAuteurs = auteurs;
        ArrayList<String> auteurs = new ArrayList<String>();
        if (sAuteurs != null) {
            sAuteurs = sAuteurs.trim();
            String[] auteursParts = sAuteurs.split(",");
            for (int i = 0; i < auteursParts.length; i++) {
                auteurs.add(auteursParts[i]);
            }
        }
        return auteurs;
    }

    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public Date getDatePublication() {
        return new Date(Integer.parseInt(getYearPublication()), Integer.parseInt(getMonthPublication()), Integer.parseInt(getDayPublication()));
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

    public String getDayPublication() {
        return dayPublication;
    }

    public void setDayPublication(String dayPublication) {
        this.dayPublication = dayPublication;
    }

    public String getMonthPublication() {
        return monthPublication;
    }

    public void setMonthPublication(String monthPublication) {
        this.monthPublication = monthPublication;
    }

    public String getYearPublication() {
        return yearPublication;
    }

    public void setYearPublication(String yearPublication) {
        this.yearPublication = yearPublication;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
    
}
