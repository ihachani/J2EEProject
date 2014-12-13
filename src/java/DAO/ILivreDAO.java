/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.Livre;
import model.Note;
import model.Auteur;

/**
 *
 * @author faiez
 */
public interface ILivreDAO {
    public ArrayList<IDataset> rechercher (HashMap<String, String> selectors, HashMap<String, String>  order) throws CreatingStatementException, SQLException;
    public ArrayList<IDataset> rechercher (String selectors, String  order) throws CreatingStatementException, SQLException;
    public int inserer (Livre livre) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Livre livre, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public int supprimer(Livre livre)  throws KeysNotFound,CreatingStatementException;
    public int evaluerLivre(Note n) throws CreatingStatementException, KeyAlreadyExisted;
    public ArrayList<IDataset> rechercherAuteur (Livre livre) throws CreatingStatementException, SQLException;
    public int insererAuteur (Livre livre, Auteur auth) throws KeyAlreadyExisted, CreatingStatementException;
    public int supprimerAuteur (Livre livre, Auteur auth)  throws KeysNotFound, CreatingStatementException;
}
