/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import DAO.DAOFactory;
import DAO.IUtilisateurDAO;
import exceptions.CreatingStatementException;
import exceptions.UtilisateurNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import model.Admin;
import model.Utilisateur;

/**
 *
 * @author faiez
 */
public class UtilisateurContainer {
    private HashMap<Integer, Utilisateur> utilisateurs = new HashMap<Integer, Utilisateur> ();
    
    public Utilisateur getUtilisateur (int key) throws UtilisateurNotFound, CreatingStatementException, SQLException {
        Utilisateur utilisateur = null;
        if((utilisateur = utilisateurs.get(key)) != null) {
            return utilisateur;
        }
       
       utilisateur = this.loadUtilisateur(key);
        if (utilisateur == null)  {
            throw new UtilisateurNotFound();
        }
        
        utilisateurs.put(new Integer(utilisateur.getId()), utilisateur);
        return utilisateur;
    }
    
    protected Utilisateur loadUtilisateur (int key) throws CreatingStatementException, SQLException {
        
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        HashMap<String, String> selectors = new HashMap<String, String> ();
        selectors.put("userID", Integer.toString(key));
        
        ArrayList<IDataset> utilisateursData = utilisateurDAO.rechercher(selectors, null);
        
        if (utilisateursData.size() == 0) return null;
        IDataset cureentUtilisateur = utilisateursData.get(0);
        Utilisateur utilisateur = null;
        
        ArrayList<String> interests = new ArrayList<String> ();
        String[] interestsParts = cureentUtilisateur.getString("interests").split(",");
        for (int i = 0; i < interestsParts.length; i++) {
            interests.add(interestsParts[i]);
        }        
        
        if (cureentUtilisateur.getEntity().equals("admin")) {
            utilisateur = new Admin(key, cureentUtilisateur.getDate("dateInscription"), cureentUtilisateur.getDate("dateNaissance"), 
                 interests, cureentUtilisateur.getString("password"), cureentUtilisateur.getString("pays"), cureentUtilisateur.getString("username"), 
                cureentUtilisateur.getString("email"));
        } else {
            utilisateur = new Utilisateur(key, cureentUtilisateur.getDate("dateInscription"), cureentUtilisateur.getDate("dateNaissance"), 
                 interests, cureentUtilisateur.getString("password"), cureentUtilisateur.getString("pays"), cureentUtilisateur.getString("username"), 
                cureentUtilisateur.getString("email"));
        }
        
        return utilisateur;
    }
    
    private static UtilisateurContainer utilisateurContainer;
    public static UtilisateurContainer GetInstance () {
        if (utilisateurContainer == null) {
            utilisateurContainer = new UtilisateurContainer();
        }
        return utilisateurContainer;
    }

}
