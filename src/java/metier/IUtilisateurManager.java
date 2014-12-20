package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Utilisateur;
import model.Category;


public interface IUtilisateurManager {
    public Utilisateur authentifier(String username, String passwd) throws CreatingStatementException, SQLException;
    public int inscrireCategory(Utilisateur u, Category c) throws KeyAlreadyExisted, CreatingStatementException;
    public int isncrire(Utilisateur user) throws KeyAlreadyExisted, UserEmailExisted, UserUsernameExisted, CreatingStatementException, SQLException;
    public int modifier(Utilisateur u, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public ArrayList<Utilisateur> rechercher(HashMap<String, String> selectors) throws  CreatingStatementException, SQLException ;
    public int supprimer(Utilisateur u) throws KeysNotFound,CreatingStatementException;
    public ArrayList<Utilisateur> rechercherInscription(Category category) throws CreatingStatementException, SQLException;
}