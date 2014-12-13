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
import model.Note;

/**
 *
 * @author faiez
 */
public interface INoteDAO {
    public ArrayList<IDataset> rechercher (HashMap<String, String> selectors,HashMap<String, String>  order) throws CreatingStatementException, SQLException;
    public int inserer (Note note) throws KeyAlreadyExisted, CreatingStatementException;
    public int supprimer(Note note)  throws KeysNotFound, CreatingStatementException;
}
