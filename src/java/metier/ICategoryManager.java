package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.DemandeLivre;
import model.Livre;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 10:47:31
 */
public interface ICategoryManager {
	public int ajouter(Category c) throws KeyAlreadyExisted, CategoryAlreayExisted, CreatingStatementException, SQLException;
	public int modifier(Category c, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
	public ArrayList<Category> rechercher(HashMap<String, String> selectors) throws CategoryNotFound, CreatingStatementException, SQLException;
	public int supprimer(Category c) throws KeysNotFound, CreatingStatementException ;
}