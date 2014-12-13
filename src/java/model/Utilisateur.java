package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 10:50:56
 */
public class Utilisateur {

    private Date dateInscription;
    private int id;
    private ArrayList<String> interests;
    private String password;
    private String pays;
    private String username;
    private String email;

    public Utilisateur () {
        this.dateInscription = null;
        this.dayNaissance = 0;
        this.monthNaissance = 0;
        this.yearNaissance = 0;
        this.id = -1;
        this.interests = new ArrayList<String>();;
        this.password = null;
        this.pays = null;
        this.username = null;
        this.email = null; 
    }
    
    public Utilisateur(int id, Date dateInscription, Date dateNaissance, ArrayList<String> interests, String password, String pays, String username, String email) {
        this.dateInscription = dateInscription;
        this.dayNaissance = dateNaissance.getDay();
        this.monthNaissance = dateNaissance.getMonth();
        this.yearNaissance = dateNaissance.getYear();
        this.id = id;
        this.interests = interests;
        this.password = password;
        this.pays = pays;
        this.username = username;
        this.email = email;
    }
    
    public Utilisateur(Date dateInscription, Date dateNaissance, ArrayList<String> interests, String password, String pays, String username, String email) {
        this.dateInscription = dateInscription;
        this.dayNaissance = dateNaissance.getDay();
        this.monthNaissance = dateNaissance.getMonth();
        this.yearNaissance = dateNaissance.getYear();
        this.id = id;
        this.interests = interests;
        this.password = password;
        this.pays = pays;
        this.username = username;
        this.email = email;
    }

    public int isAdmin(){
            return 0;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Date getDateNaissance() {
        return new Date(getYearNaissance(), getMonthNaissance(), getDayNaissance());
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dayNaissance = dateNaissance.getDay();
        this.monthNaissance = dateNaissance.getMonth();
        this.yearNaissance = dateNaissance.getYear();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) email = email.trim();
        this.email = email;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Utilisateur) {
            return (((Utilisateur) o).getId() == getId());
        }
        return false;
    }
    
        private int dayNaissance;
    private int monthNaissance;
    private int yearNaissance;

    public int getDayNaissance() {
        return dayNaissance;
    }

    public void setDayNaissance(int dayNaissance) {
        this.dayNaissance = dayNaissance;
    }

    public int getMonthNaissance() {
        return monthNaissance;
    }

    public void setMonthNaissance(int monthNaissance) {
        this.monthNaissance = monthNaissance;
    }

    public int getYearNaissance() {
        return yearNaissance;
    }

    public void setYearNaissance(int yearNaissance) {
        this.yearNaissance = yearNaissance;
    }

    

    
}//end Utilisateur