package controller.command;

import java.util.ArrayList;
import java.util.Date;

public class UtilisateurUpdateCommand {
    private ArrayList<String> interests;
    private String password;
    private String pays;
    private String username;


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
        ArrayList<String> interests = null;
        if ((sInterests != null) && (sInterests.isEmpty())) {
            interests = new ArrayList<String>();
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
    
}
