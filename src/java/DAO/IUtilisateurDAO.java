package DAO;

import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.Utilisateur;

public interface IUtilisateurDAO {
    public ArrayList<IDataset> rechercher (HashMap<String, String> selectors,HashMap<String, String>  order) throws CreatingStatementException, SQLException;
    public int inserer (Utilisateur utilisateur) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Utilisateur utilisateur, HashMap<String, String> updates)throws CreatingStatementException,SQLException;
    public int supprimer(Utilisateur utilisateur)  throws KeysNotFound, CreatingStatementException;
    public int inscrireUtilisateurCategory(Utilisateur u, Category c) throws KeyAlreadyExisted, CreatingStatementException;
    public int supprimerUtilisateurCategory(Utilisateur u, Category c) throws CreatingStatementException, KeysNotFound;
    public ArrayList<IDataset> rechercherUtilisateurInscruptionsByCategory(Category c) throws CreatingStatementException, SQLException;
    public ArrayList<IDataset> rechercherCategoryInscruptionsByUtilisateur(Utilisateur u) throws CreatingStatementException, SQLException;
}
