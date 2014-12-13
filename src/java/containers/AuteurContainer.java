/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import DAO.DAOFactory;
import DAO.IAuteurDAO;
import exceptions.AuteurNotFound;
import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Auteur;

/**
 *
 * @author faiez
 */
public class AuteurContainer {
    private HashMap<Integer, Auteur> auteurs = new HashMap<Integer, Auteur> ();
    
    public Auteur getAuteur (int key) throws AuteurNotFound, CreatingStatementException, SQLException {
        Auteur auteur = auteurs.get(key);
        if(auteur != null) {
            return auteur;
        }
       auteur = this.loadAuteur(key);
        if (auteur == null)  {
            throw new AuteurNotFound();
        }
        auteurs.put(new Integer(auteur.getId()), auteur);
        return auteur;
    }
    
    protected Auteur loadAuteur (int key) throws CreatingStatementException, SQLException {
        IAuteurDAO auteurDAO = DAOFactory.getInstance().createAuteurDAO();
        HashMap<String, String> selectors = new HashMap<String, String> ();
        selectors.put("auteurID", Integer.toString(key));
        ArrayList<IDataset> auteursData = auteurDAO.rechercher(selectors, null);
        if (auteursData.size() == 0) return null;
        Auteur auteur = new Auteur(auteursData.get(0).getInt ("auteurID"), auteursData.get(0).getString ("nom"));
        return auteur;
    }
    
    private static AuteurContainer auteurContainer;
    public static AuteurContainer GetInstance () {
        if (auteurContainer == null) {
            auteurContainer = new AuteurContainer();
        }
        return auteurContainer;
    }
}
