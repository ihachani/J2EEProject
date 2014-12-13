package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 10:50:59
 */
public class Admin extends Utilisateur {

    public Admin( int id, Date dateInscription, Date dateNaissance, ArrayList<String> interests, String password, String pays, String username, String email) {
        super(id, dateInscription, dateNaissance, interests, password, pays, username, email);
    }

    public int isAdmin(){
            return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Admin) {
            return (((Admin) o).getId() == getId());
        }
        return false;
    }
    
}//end Admin