package metier;

import DAO.DAOFactory;
import DAO.INoteDAO;
import containers.UtilisateurContainer;
import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Livre;
import model.Note;
import model.Utilisateur;

public class NoteManager implements INoteManager {

    @Override
    public int ajouterNote(Note n) throws KeyAlreadyExisted, CreatingStatementException{
        INoteDAO noteDAO = DAOFactory.getInstance().createNoteDAO();
        int state = noteDAO.inserer(n);
        n.setId(state);
        return state;
    }

    @Override
    public ArrayList<Note> rechercherNote(Livre livre, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        INoteDAO noteDAO = DAOFactory.getInstance().createNoteDAO();
        HashMap<String, String> selectors = new HashMap<String, String>();
        selectors.put("livreISBN", livre.getIsbn());
        ArrayList<IDataset> noteDataset = noteDAO.rechercher(selectors, order);
        ArrayList<Note> resNotes = new ArrayList<Note>();
        for (int i = 0; i < noteDataset.size(); i++) {
            IDataset currentDataset = noteDataset.get(i);
            Utilisateur utilisateur = UtilisateurContainer.GetInstance().getUtilisateur(currentDataset.getInt("userID"));
            Note note = new Note(livre, currentDataset.getDate("date"), currentDataset.getInt("noteID"), currentDataset.getInt("rate"), currentDataset.getString("review"), utilisateur);
            resNotes.add(note);
        }
        return resNotes;
    }

    @Override
    public int supprimerNote(Note n) throws KeysNotFound, CreatingStatementException  {
        INoteDAO noteDAO = DAOFactory.getInstance().createNoteDAO();
        int state = noteDAO.supprimer(n);
        return state;
    }
    
}
