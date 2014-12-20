package controller.command;

import java.util.ArrayList;
import java.util.Date;
import model.Utilisateur;

public class UtilisateurCommand {
    private ArrayList<String> interests;
    private String password;
    private String pays;
    private String username;
    private String email;
    private String dayNaissance;
    private String monthNaissance;
    private String yearNaissance;
    
    public Date getDateNaissance() {
        return new Date(Integer.parseInt(getYearNaissance()), Integer.parseInt(getMonthNaissance()), Integer.parseInt(getDayNaissance()));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) email = email.trim();
        this.email = email;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }
    
    public String getInterestsString() {
        String res = "";
        ArrayList<String> interestsArray = getInterests();      
        for (int i = 0; i < interestsArray.size(); i++) {
            res += interestsArray.get(i) + ((i == interestsArray.size() -1)?"":",");
        }
        return res;
    }

    public void setInterests(String sInterests) {
        ArrayList<String> interests = new ArrayList<String>();
        if ((sInterests != null) && (sInterests.isEmpty())) {
            sInterests = sInterests.trim();
            if (sInterests != null) {
                sInterests = sInterests.trim();
                String[] interestsParts = sInterests.split(",");
                for (int i = 0; i < interestsParts.length; i++) {
                    interests.add(interestsParts[i]);
                }
            }
        }
        this.interests = interests;
    }
    
    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) password = password.trim();
        this.password = password;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        if (pays != null) pays = pays.trim();
        this.pays = pays;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null) username = username.trim();
        this.username = username;
    }
    
    public String getDayNaissance() {
        return dayNaissance;
    }

    public void setDayNaissance(String dayNaissance) {
        this.dayNaissance = dayNaissance;
    }

    public String getMonthNaissance() {
        return monthNaissance;
    }

    public void setMonthNaissance(String monthNaissance) {
        this.monthNaissance = monthNaissance;
    }

    public String getYearNaissance() {
        return yearNaissance;
    }

    public void setYearNaissance(String yearNaissance) {
        this.yearNaissance = yearNaissance;
    }
}
