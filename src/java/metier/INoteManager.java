package metier;

import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Livre;
import model.Note;


public interface INoteManager {
	public int ajouterNote(Note n) throws KeyAlreadyExisted, CreatingStatementException;
        public ArrayList<Note> rechercherNote(Livre l, HashMap<String, String> order) throws CreatingStatementException, SQLException;
	public int supprimerNote(Note n) throws KeysNotFound, CreatingStatementException;
}