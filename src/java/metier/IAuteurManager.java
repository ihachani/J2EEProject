/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Auteur;
import model.Category;

/**
 *
 * @author faiez
 */
public interface IAuteurManager {
    public int ajouter(Auteur a) throws KeyAlreadyExisted, CreatingStatementException;
    public int modifier(Auteur a, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
    public ArrayList<Auteur> rechercher(HashMap<String, String> selectors) throws AuteurNotFound, CreatingStatementException, SQLException;
    public int supprimer(Auteur a) throws KeysNotFound, CreatingStatementException;
}
