/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import DAO.DAOFactory;
import DAO.ILivreDAO;
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

/**
 *
 * @author faiez
 */
public class LivreManager implements ILivreManager {

    @Override
    public int accepter(Livre l) throws CreatingStatementException, SQLException{
        HashMap<String, String> updates = new HashMap<String, String>();
        updates.put("state", "1");
        return this.modifer(l, updates);
    }

    @Override
    public int ajouterLivre(Livre l) throws AuteurNotFound, CategoryNotFound, KeyAlreadyExisted, UtilisateurNotFound, EMailCannotBeSent, CreatingStatementException, SQLException  {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        HashMap<String, String> selectors = new HashMap<String, String>();
        selectors.put("isbn", l.getIsbn());
        ArrayList<Livre> livres = this.rechercherLivre(selectors, null, 0, 1);
        if (livres.size() > 0) {
            throw new LivreISBNAlreayExisted();
        }
        int state = livreDAO.inserer(l);
        if (state == 1) {
            IMetierFactory metierFactory = MetierFactory.getInstance();
            IUtilisateurManager utilisateurManager = metierFactory.createIUtilisateurManager();            
            ArrayList<Utilisateur> inscriptions = utilisateurManager.rechercherInscription(l.getCategory());
            IMailHelper mailHelper = MailHelper.getInstance();
            for (int i = 0; i < inscriptions.size(); i++) {
                String msg = URLResolver.getBookFullURL(l);
                mailHelper.sendMail (inscriptions.get(i).getEmail(), msg);
            }
            /*
             * notifier les utilisateurs inscrits
             */
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
        String msg = URLResolver.getBookFullURL(l);
        mailHelper.sendMail (email, msg);
        /*
        * HERE send an email to the friend's email with the requested book and message
        */
    }

    @Override
    public int modifer(Livre l, HashMap<String, String> updates) throws CreatingStatementException, SQLException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        int state = livreDAO.modifier(l, updates);
        return state;
    }

    @Override
    public ArrayList<Livre> rechercherLivre(HashMap<String, String> selectors, HashMap<String, String> order, int page, int booksPerPage) throws AuteurNotFound, CategoryNotFound, UtilisateurNotFound, CreatingStatementException, SQLException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        ArrayList<IDataset> livresDataset = livreDAO.rechercher(selectors, order);
        ArrayList<Livre> resLivres = new ArrayList<Livre>();
        for (int i = 0; i < livresDataset.size(); i++) {
            IDataset currentDataset = livresDataset.get(i);
            ArrayList<Auteur> auteurs = new ArrayList<Auteur>();
            Category category = CategoryContainer.GetInstance().getCategory(currentDataset.getInt("categoryID"));
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));
            Livre livre = new Livre(currentDataset.getString("cover"), currentDataset.getDate("dateAjout"), currentDataset.getDate("datePublication"),
                    currentDataset.getString("description"), currentDataset.getString("isbn"), currentDataset.getString("langue"), 
                    currentDataset.getString("titre"), category, auteurs, utilisateur, currentDataset.getInt("state"));
            
            ArrayList<IDataset> auteursDataset = livreDAO.rechercherAuteur(livre);
            for (int j = 0; j < auteursDataset.size(); j++) {
                Auteur currentAuteur = AuteurContainer.GetInstance().getAuteur(auteursDataset.get(j).getInt("auteurID"));
                auteurs.add(currentAuteur);
            }
            livre.setAuteurs(auteurs);
            resLivres.add(livre);
        }
        return new ArrayList<Livre>(resLivres.subList(page*booksPerPage, booksPerPage*(page+1)-1));
    }

    @Override
    public int supprimerLivre(Livre l) throws KeysNotFound, CreatingStatementException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        int state = livreDAO.supprimer(l);
        return state;
    }

    @Override
    public ArrayList<Livre> rechercherLivre(String selectors, String order, int page, int booksPerPage) throws AuteurNotFound, CategoryNotFound, UtilisateurNotFound, CreatingStatementException, SQLException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        ArrayList<IDataset> livresDataset = livreDAO.rechercher(selectors, order);
        ArrayList<Livre> resLivres = new ArrayList<Livre>();
        
        for (int i = 0; i < livresDataset.size(); i++) {
            IDataset currentDataset = livresDataset.get(i);
            ArrayList<Auteur> auteurs = new ArrayList<Auteur>();
            Category category = CategoryContainer.GetInstance().getCategory(currentDataset.getInt("categoryID"));
            
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));
            
            Livre livre = new Livre(currentDataset.getString("cover"), currentDataset.getDate("dateAjout"), currentDataset.getDate("datePublication"),
                    currentDataset.getString("description"), currentDataset.getString("isbn"), currentDataset.getString("langue"), 
                    currentDataset.getString("titre"), category, auteurs, utilisateur, currentDataset.getInt("state"));
            
            ArrayList<IDataset> auteursDataset = livreDAO.rechercherAuteur(livre);
            for (int j = 0; j < auteursDataset.size(); j++) {
                Auteur currentAuteur = AuteurContainer.GetInstance().getAuteur(auteursDataset.get(j).getInt("auteurID"));
                auteurs.add(currentAuteur);
            }
            livre.setAuteurs(auteurs);
            resLivres.add(livre);
        }
        ArrayList<Livre> finalRes = new ArrayList<Livre>();
        do {
            int inf = page*booksPerPage, sup = inf+booksPerPage;
            try {
                finalRes = new ArrayList<Livre>(resLivres.subList(inf, sup));
            } catch (java.lang.IndexOutOfBoundsException e) {
                if (inf >= resLivres.size()) {
                    page = page -1;
                } else {
                    booksPerPage = resLivres.size() - inf;
                }
            }
        } while (finalRes.isEmpty());
        return finalRes;
    }

    @Override
    public Integer getNBPages(String selectors, int booksPerPage) throws CreatingStatementException, SQLException {
        ILivreDAO livreDAO = DAOFactory.getInstance().createLivreDAO();
        ArrayList<IDataset> livresDataset = livreDAO.rechercher(selectors, null);
        return new Integer((new Double((double) livresDataset.size()/booksPerPage)).intValue());
    }
}
