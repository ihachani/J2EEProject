/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import DAO.DAOFactory;
import DAO.ICategoryDAO;
import DAO.IUtilisateurDAO;
import containers.UtilisateurContainer;
import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import exceptions.UserEmailExisted;
import exceptions.UserUsernameExisted;
import exceptions.UtilisateurNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.Utilisateur;

/**
 *
 * @author faiez
 */
public class UtilisateurManager implements IUtilisateurManager {

    @Override
    public Utilisateur authentifier(String username, String passwd) throws UtilisateurNotFound,CreatingStatementException, SQLException  {
        HashMap<String, String> selectors = new HashMap<String, String> ();
        ArrayList<Utilisateur> utilisateurs = this.rechercher(null);
        return utilisateurs.get(0);
    }

    @Override
    public int inscrireCategory(Utilisateur u, Category c) throws KeyAlreadyExisted, CreatingStatementException {
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        int state = utilisateurDAO.inscrireUtilisateurCategory(u, c);
        return state;
    }

    @Override
    public int isncrire(Utilisateur u) throws KeyAlreadyExisted, UserEmailExisted, UserUsernameExisted, CreatingStatementException, SQLException {
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        HashMap<String, String> selectors = new HashMap<String, String>();
        selectors.put("email", u.getEmail());
        ArrayList<IDataset> utilisateursDataset = utilisateurDAO.rechercher(selectors, null);
        if (utilisateursDataset.size() > 0) {
            throw new UserEmailExisted();
        }
        for (int i = 0; i < utilisateursDataset.size(); i++) {
            if (utilisateursDataset.get(i).getString("username").equals(u.getUsername())) {
                throw new UserUsernameExisted();
            }
        }
        int state = utilisateurDAO.inserer(u);
        u.setId(state);
        return state;
    }

    @Override
    public int modifier(Utilisateur u, HashMap<String, String> updates) throws CreatingStatementException, SQLException {
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        int state = utilisateurDAO.modifier(u, updates);
        return state;
    }

    @Override
    public ArrayList<Utilisateur> rechercher(HashMap<String, String> selectors) throws UtilisateurNotFound, CreatingStatementException, SQLException {
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        ArrayList<IDataset> utilisateursDataset = utilisateurDAO.rechercher(selectors, null);
        ArrayList<Utilisateur> resUtilisateurs = new ArrayList<Utilisateur>();
        for (int i = 0; i < utilisateursDataset.size(); i++) {
            IDataset currentDataset = utilisateursDataset.get(i);
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));
            resUtilisateurs.add(utilisateur);
        }
        return resUtilisateurs;
    }

    @Override
    public int supprimer(Utilisateur u) throws KeysNotFound,CreatingStatementException  {
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        int state = utilisateurDAO.supprimer(u);
        return state;
    }

    @Override
    public ArrayList<Utilisateur> rechercherInscription(Category category) throws CreatingStatementException, SQLException, UtilisateurNotFound {
        IUtilisateurDAO utilisateurDAO = DAOFactory.getInstance().createUtilisateurDAO();
        ArrayList<IDataset> utilisateursDataset = utilisateurDAO.rechercherUtilisateurByCategory(category);
        ArrayList<Utilisateur> resUtilisateurs = new ArrayList<Utilisateur>();
        for (int i = 0; i < utilisateursDataset.size(); i++) {
            IDataset currentDataset = utilisateursDataset.get(i);
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));
            resUtilisateurs.add(utilisateur);
        }
        return resUtilisateurs;
    }
    
}
