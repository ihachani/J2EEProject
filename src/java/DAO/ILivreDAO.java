package DAO;

import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Livre;
import model.Note;
import model.Auteur;

public interface ILivreDAO {
    public ArrayList<IDataset> rechercher (HashMap<String, String> selectors, HashMap<String, String>  order, int from, int to) throws CreatingStatementException, SQLException;
    public ArrayList<IDataset> rechercher (String selectors, String  order, int from, int to) throws CreatingStatementException, SQLException;
    public int inserer (Livre livre) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Livre livre, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public int supprimer(Livre livre)  throws KeysNotFound,CreatingStatementException;
    public int evaluerLivre(Note n) throws CreatingStatementException, KeyAlreadyExisted;
    public ArrayList<IDataset> rechercherAuteur (Livre livre) throws CreatingStatementException, SQLException;
    public int insererAuteur (Livre livre, Auteur auth) throws KeyAlreadyExisted, CreatingStatementException;
    public int supprimerAuteur (Livre livre, Auteur auth)  throws KeysNotFound, CreatingStatementException;
    public int getSetsNumber (HashMap<String, String> selectors) throws CreatingStatementException, SQLException;
    public int getSetsNumber (String selectors) throws CreatingStatementException, SQLException;
}
