/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import DAO.DAOFactory;
import DAO.IAuteurDAO;
import DAO.ICategoryDAO;
import DAO.IDAOFactory;
import containers.AuteurContainer;
import containers.CategoryContainer;
import exceptions.AuteurNotFound;
import exceptions.CategoryAlreayExisted;
import exceptions.CategoryNotFound;
import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Auteur;
import model.Category;

/**
 *
 * @author faiez
 */
public class AuteurManager implements IAuteurManager {

    @Override
    public int ajouter(Auteur a) throws KeyAlreadyExisted, CreatingStatementException {
        IAuteurDAO daoAuteur = DAOFactory.getInstance().createAuteurDAO();
        int state = daoAuteur.inserer(a);
        a.setId(state);
        return state;
    }

    @Override
    public int modifier(Auteur a, HashMap<String, String> updates) throws CreatingStatementException, SQLException {
        IAuteurDAO daoAuteur = DAOFactory.getInstance().createAuteurDAO();
        int state = daoAuteur.modifier(a, updates);
        return state;
    }

    @Override
    public ArrayList<Auteur> rechercher(HashMap<String, String> selectors) throws AuteurNotFound, CreatingStatementException, SQLException {
        IAuteurDAO daoAuteur = DAOFactory.getInstance().createAuteurDAO();
        ArrayList<IDataset> auteursDataset = daoAuteur.rechercher(selectors, null);
        ArrayList<Auteur> resAuteurs = new ArrayList<Auteur>();
        for (int i = 0; i < auteursDataset.size(); i++) {
            IDataset currentDataset = auteursDataset.get(i);
            Auteur auteur = AuteurContainer.GetInstance().getAuteur(currentDataset.getInt("auteurID"));
            resAuteurs.add(auteur);
        }
        return resAuteurs;
    }

    @Override
    public int supprimer(Auteur a) throws KeysNotFound, CreatingStatementException {
        IAuteurDAO auteurDAO = DAOFactory.getInstance().createAuteurDAO();
        int state = auteurDAO.supprimer(a);
        return state;
    }
    
}
