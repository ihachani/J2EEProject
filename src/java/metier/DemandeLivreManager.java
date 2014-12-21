package metier;

import DAO.DAOFactory;
import DAO.ICategoryDAO;
import DAO.IDemandeLivreDAO;
import com.sun.faces.util.CollectionsUtils;
import config.MessageRegistry;
import containers.CategoryContainer;
import containers.UtilisateurContainer;
import exceptions.CreatingStatementException;
import exceptions.EMailCannotBeSent;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import helper.IMailHelper;
import helper.MailHelper;
import helper.URLResolver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.DemandeLivre;
import model.Livre;
import model.Utilisateur;

public class DemandeLivreManager implements IDemandeLivreManager {

    @Override
    public int ajouterDemande(DemandeLivre demande) throws KeyAlreadyExisted, SQLException,CreatingStatementException {
        IDemandeLivreDAO demandeLivreDAO = DAOFactory.getInstance().createDemandeLivreDAO();
        HashMap<String, String> selectors = new CollectionsUtils.ConstMap<String, String>();
        selectors.put("titre", demande.getTitre());
        ArrayList<DemandeLivre> demandes = this.rechercherDemandeLivre (selectors, null);
        if (demandes.size() > 0) {
            throw new KeyAlreadyExisted();
        }
        int state = demandeLivreDAO.inserer(demande);
        demande.setId(state);
        return state;
    }

    @Override
    public ArrayList<DemandeLivre> rechercherDemandeLivre(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException {
        IDemandeLivreDAO demandeLivreDAO = DAOFactory.getInstance().createDemandeLivreDAO();
        ArrayList<IDataset> demandeLivreDataset = demandeLivreDAO.rechercher(selectors, order);
        ArrayList<DemandeLivre> resDemandes = new ArrayList<DemandeLivre>();
        for (int i = 0; i < demandeLivreDataset.size(); i++) {
            IDataset currentDataset = demandeLivreDataset.get(i);
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));
            DemandeLivre demandeLivre = new DemandeLivre(currentDataset.getDate("date"), currentDataset.getInt("demandeID"), currentDataset.getString("titre"), utilisateur);
            resDemandes.add(demandeLivre);
        }
        return resDemandes;
    }

    @Override
    public int repondreDemande(DemandeLivre d, Livre l) throws KeysNotFound, EMailCannotBeSent, CreatingStatementException {
        IDemandeLivreDAO demandeLivreDAO = DAOFactory.getInstance().createDemandeLivreDAO();
        int state = demandeLivreDAO.supprimer(d);
        if (state > 0) {
            IMailHelper mailHelper = MailHelper.getInstance();
            String msg = MessageRegistry.respondDemandeEmailIntro;
            msg += URLResolver.getBookFullURL(l);
            mailHelper.sendMail (d.getUtilisateur().getEmail(), msg);
        }
        return state;
    }
    
}
