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

/**
 *
 * @author faiez
 */
public interface ICategoryDAO {
    public ArrayList<IDataset> rechercher (HashMap<String, String> selectors, HashMap<String, String>  order) throws CreatingStatementException, SQLException;
    public int inserer (Category cat) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Category c, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public int supprimer(Category c) throws KeysNotFound, CreatingStatementException;
}