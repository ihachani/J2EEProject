package metier;

import DAO.DAOFactory;
import DAO.ILivreDAO;
import config.MessageRegistry;
import containers.AuteurContainer;
import containers.CategoryContainer;
import containers.UtilisateurContainer;
import exceptions.*;
import helper.IDataset;
import helper.IMailHelper;
import helper.MailHelper;
import helper.URLResolver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.*;

public class LivreManager implements ILivreManager {

    @Override
    public int accepter(Livre l) throws CreatingStatementException, SQLException{
        HashMap<String, String> updates = new HashMap<String, String>();
        updates.put("state", "1");
        return this.modifer(l, updates);
    }

    @Override
    public int ajouterLivre(Livre l) throws KeyAlreadyExisted, EMailCannotBeSent, CreatingStatementException, SQLException  {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        HashMap<String, String> selectors = new HashMap<String, String>();
        selectors.put("isbn", l.getIsbn());
        ArrayList<Livre> livres = this.rechercherLivre(selectors, null);
        if ((livres != null) && (livres.size() > 0)) {
            throw new KeyAlreadyExisted();
        }
        int state = livreDAO.inserer(l);
        if (state == 1) {
            IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;            
            ArrayList<Utilisateur> inscriptions = utilisateurManager.rechercherInscription(l.getCategory());
            if ((inscriptions != null) && (inscriptions.size() > 0)) {
                IMailHelper mailHelper = MailHelper.getInstance();
            for (int i = 0; i < inscriptions.size(); i++) {
                    String msg = MessageRegistry.inscriptionCategoryEmailIntro;
                    msg += URLResolver.getBookFullURL(l);
                    mailHelper.sendMail (inscriptions.get(i).getEmail(), msg);
                }
            }
        }
        return state;
    }

    @Override
    public int evaluerLivre(Note n) throws CreatingStatementException, KeyAlreadyExisted {
        ILivreDAO livreDAO = null;
        int state = livreDAO.evaluerLivre(n);
        return state;
    }

    @Override
    public void inviterAmi(Livre l, String email) throws EMailCannotBeSent {
        IMailHelper mailHelper = MailHelper.getInstance();
        String msg = MessageRegistry.inviterAmiEmailIntro;
        msg += URLResolver.getBookFullURL(l);
        mailHelper.sendMail (email, msg);
    }

    @Override
    public int modifer(Livre l, HashMap<String, String> updates) throws CreatingStatementException, SQLException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        int state = livreDAO.modifier(l, updates);
        return state;
    }

    @Override
    public ArrayList<Livre> rechercherLivre(HashMap<String, String> selectors, HashMap<String, String> order, int page, int booksPerPage) throws CreatingStatementException, SQLException {
        int from = -1;
        int to = -1;
        if ((page != -1) && (booksPerPage != -1)) {
            from = page*booksPerPage;
            to = from+booksPerPage+((from==0)?0:-1);
        }
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        ArrayList<IDataset> livresDataset = livreDAO.rechercher(selectors, order, from, to);
        ArrayList<Livre> resLivres = getLivresFromDatasets (livresDataset, livreDAO);
        return resLivres;
    }

    @Override
    public int supprimerLivre(Livre l) throws KeysNotFound, CreatingStatementException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        int state = livreDAO.supprimer(l);
        return state;
    }

    @Override
    public ArrayList<Livre> rechercherLivre(String selectors, String order, int page, int booksPerPage) throws CreatingStatementException, SQLException {
        int from = -1;
        int to = -1;
        if ((page != -1) && (booksPerPage != -1)) {
            from = page*booksPerPage;
            to = from+booksPerPage+((from==0)?0:-1);
        }
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        ArrayList<IDataset> livresDataset = livreDAO.rechercher(selectors, order, from, to);
        ArrayList<Livre> resLivres = getLivresFromDatasets (livresDataset, livreDAO);
        return resLivres;
    }
    
    protected ArrayList<Livre> getLivresFromDatasets (ArrayList<IDataset> livresDataset, ILivreDAO livreDAO) throws CreatingStatementException, SQLException {
        if ((livresDataset == null) || (livresDataset.isEmpty())) return null;
        ArrayList<Livre> resLivres = new ArrayList<Livre>();
        for (int i = 0; i < livresDataset.size(); i++) {
            IDataset currentDataset = livresDataset.get(i);
            Category category = CategoryContainer.GetInstance().getCategory(currentDataset.getInt("categoryID"));
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));       
            Livre livre = createLivre (currentDataset, category, utilisateur);
            setLivreAuteurs (livre, livreDAO);
            resLivres.add(livre);
        }
        return resLivres;
    }
    
    @Override
    public Livre setLivreAuteurs (Livre livre, ILivreDAO livreDAO) throws CreatingStatementException, SQLException {
        ArrayList<Auteur> auteurs = new ArrayList<Auteur>();
        ArrayList<IDataset> auteursDataset = livreDAO.rechercherAuteur(livre);
        for (int j = 0; j < auteursDataset.size(); j++) {
            Auteur currentAuteur = AuteurContainer.GetInstance().getAuteur(auteursDataset.get(j).getInt("auteurID"));
            if (currentAuteur != null) auteurs.add(currentAuteur);
        }
        if (auteurs.size() > 0) livre.setAuteurs(auteurs);
        return livre;
    }

    @Override
    public Livre createLivre (IDataset currentDataset, Category category, Utilisateur utilisateur) {
        Livre livre =  new Livre(currentDataset.getString("cover"), currentDataset.getDate("dateAjout"), currentDataset.getDate("datePublication"),
                    currentDataset.getString("description"), currentDataset.getString("isbn"), currentDataset.getString("langue"), 
                    currentDataset.getString("titre"), category, null, utilisateur, currentDataset.getInt("state"), 0);
        return livre;
    }
    
    @Override
    public Integer getNBPages(String selectors, int booksPerPage) throws CreatingStatementException, SQLException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        int livresNumber = livreDAO.getSetsNumber(selectors);
        return new Integer((new Double(Math.ceil((double) livresNumber/booksPerPage ))).intValue());
    }

    @Override
    public ArrayList<Livre> rechercherLivre(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException {
        return this.rechercherLivre(selectors, order, 0, 1);
    }

    @Override
    public ArrayList<Livre> rechercherLivre(String selectors, String order) throws CreatingStatementException, SQLException {
        return this.rechercherLivre(selectors, order, 0, 1);
    }
}
