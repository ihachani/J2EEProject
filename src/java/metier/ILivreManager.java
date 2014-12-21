package metier;

import DAO.ILivreDAO;
import exceptions.*;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.Livre;
import model.Note;
import model.Utilisateur;


public interface ILivreManager {
	public int accepter(Livre l) throws CreatingStatementException, SQLException;
	public int ajouterLivre(Livre l) throws  KeyAlreadyExisted, EMailCannotBeSent, CreatingStatementException, SQLException;
	public int evaluerLivre(Note n) throws CreatingStatementException, KeyAlreadyExisted;
	public void inviterAmi(Livre l, String email) throws EMailCannotBeSent;
	public int modifer(Livre l, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
	public ArrayList<Livre> rechercherLivre(HashMap<String, String> selectors, HashMap<String, String> order, int page, int booksPerPage)
                throws CreatingStatementException, SQLException;
        public ArrayList<Livre> rechercherLivre(HashMap<String, String> selectors, HashMap<String, String> order)
                throws CreatingStatementException, SQLException;
        public ArrayList<Livre> rechercherLivre(String selectors, String order, int page, int booksPerPage)
                throws CreatingStatementException, SQLException;
        public ArrayList<Livre> rechercherLivre(String selectors, String order)
                throws CreatingStatementException, SQLException;
	public int supprimerLivre(Livre l) throws KeysNotFound, CreatingStatementException;
        public Integer getNBPages(String selectors, int booksPerPage)  throws CreatingStatementException, SQLException;
        public Livre setLivreAuteurs (Livre livre, ILivreDAO livreDAO) throws CreatingStatementException, SQLException;
        public Livre createLivre (IDataset currentDataset, Category category, Utilisateur utilisateur);
}