package containers;

import DAO.DAOFactory;
import DAO.IAuteurDAO;
import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Auteur;

public class AuteurContainer implements IAuteurContainer {
    
    private HashMap<Integer, Auteur> auteurs = new HashMap<Integer, Auteur> ();
    
    @Override
    public Auteur getAuteur (int key) throws CreatingStatementException, SQLException {
        Auteur auteur = auteurs.get(key);
        if(auteur != null) {
            return auteur;
        }
       auteur = this.loadAuteur(key);
        if (auteur == null)  {
            return null;
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
        Auteur auteur = createAuteur(auteursData.get(0));;
        return auteur;
    }
    
    @Override
    public Auteur createAuteur (IDataset dataset) {
        return new Auteur(dataset.getInt ("auteurID"), dataset.getString ("nom"));
    }
    
    private static AuteurContainer auteurContainer;
    public static AuteurContainer GetInstance () {
        if (auteurContainer == null) {
            auteurContainer = new AuteurContainer();
        }
        return auteurContainer;
    }
}
