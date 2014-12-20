package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Auteur;
import model.Category;

public interface IAuteurManager {
    public int ajouter(Auteur a) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Auteur a, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public ArrayList<Auteur> rechercher(HashMap<String, String> selectors) throws CreatingStatementException, SQLException;
    public int supprimer(Auteur a) throws KeysNotFound, CreatingStatementException;
    public ArrayList<Auteur> extractAuteurs(ArrayList<String> RequestAuteurs) throws CreatingStatementException, SQLException, KeyAlreadyExisted;
}
