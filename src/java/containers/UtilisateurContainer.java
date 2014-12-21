package containers;

import DAO.DAOFactory;
import DAO.IUtilisateurDAO;
import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import model.Admin;
import model.Category;
import model.Utilisateur;


public class UtilisateurContainer implements IUtilisateurContainer {
    
    private HashMap<Integer, Utilisateur> utilisateurs = new HashMap<Integer, Utilisateur> ();
    
    @Override
    public Utilisateur getUtilisateur (int key) throws CreatingStatementException, SQLException {
        Utilisateur utilisateur = null;
        if((utilisateur = utilisateurs.get(key)) != null) {
            return utilisateur;
        }
       
       utilisateur = this.loadUtilisateur(key);
        if (utilisateur == null)  {
            return null;
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
        return createUtilisateur (key, cureentUtilisateur);
    }
    
    @Override
    public Utilisateur createUtilisateur (int key, IDataset dataset) {
        Utilisateur utilisateur = null;       
        
        if (dataset.getEntity().equals("admin")) {
            utilisateur = new Admin(key, dataset.getDate("dateInscription"), dataset.getDate("dateNaissance"), 
                 null, dataset.getString("password"), dataset.getString("pays"), dataset.getString("username"), 
                dataset.getString("email"));
        } else {
            utilisateur = new Utilisateur(key, dataset.getDate("dateInscription"), dataset.getDate("dateNaissance"), 
                 null, dataset.getString("password"), dataset.getString("pays"), dataset.getString("username"), 
                dataset.getString("email"));
        }
        utilisateur.setInterests(dataset.getString("interests"));
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
