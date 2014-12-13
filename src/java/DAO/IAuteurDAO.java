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
import model.Auteur;

/**
 *
 * @author faiez
 */
public interface IAuteurDAO {
    public ArrayList<IDataset> rechercher (HashMap<String, String> selectors,HashMap<String, String>  order) throws CreatingStatementException, SQLException;
    public int inserer (Auteur a) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Auteur a, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public int supprimer(Auteur a) throws KeysNotFound, CreatingStatementException;
}
