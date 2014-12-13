package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Livre;
import model.Note;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 10:47:35
 */
public interface ILivreManager {
	public int accepter(Livre l) throws CreatingStatementException, SQLException;
	public int ajouterLivre(Livre l) throws AuteurNotFound, CategoryNotFound, KeyAlreadyExisted, UtilisateurNotFound, EMailCannotBeSent, CreatingStatementException, SQLException;
	public int evaluerLivre(Note n) throws CreatingStatementException, KeyAlreadyExisted;
	public void inviterAmi(Livre l, String email) throws EMailCannotBeSent;
	public int modifer(Livre l, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
	public ArrayList<Livre> rechercherLivre(HashMap<String, String> selectors, HashMap<String, String> order, int page, int booksPerPage)
                throws AuteurNotFound, CategoryNotFound, UtilisateurNotFound, CreatingStatementException, SQLException;
        public ArrayList<Livre> rechercherLivre(String selectors, String order, int page, int booksPerPage)
                throws AuteurNotFound, CategoryNotFound, UtilisateurNotFound, CreatingStatementException, SQLException;
	public int supprimerLivre(Livre l) throws KeysNotFound, CreatingStatementException;
        public Integer getNBPages(String selectors, int booksPerPage)  throws CreatingStatementException, SQLException;
}