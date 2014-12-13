package metier;

import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import exceptions.UtilisateurNotFound;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Livre;
import model.Note;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 10:47:36
 */
public interface INoteManager {
	public int ajouterNote(Note n) throws KeyAlreadyExisted, CreatingStatementException;
        public ArrayList<Note> rechercherNote(Livre l, HashMap<String, String> order) throws UtilisateurNotFound, CreatingStatementException, SQLException;
	public int supprimerNote(Note n) throws KeysNotFound, CreatingStatementException;
}