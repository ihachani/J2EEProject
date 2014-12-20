package model;

import java.util.ArrayList;
import java.util.Date;

public class Admin extends Utilisateur {

    public Admin( int id, Date dateInscription, Date dateNaissance, ArrayList<String> interests, String password, String pays, String username, String email) {
        super(id, dateInscription, dateNaissance, interests, password, pays, username, email);
        setState(1);
    }

    public int isAdmin(){
        return getState();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Admin) {
            return (((Admin) o).getId() == getId());
        }
        return false;
    }
    
}