package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.DemandeLivre;
import model.Livre;

public interface ICategoryManager {
	public int ajouter(Category c) throws KeyAlreadyExisted, CreatingStatementException, SQLException;
	public int modifier(Category c, HashMap<String, String> updates) throws CreatingStatementException, SQLException;
	public ArrayList<Category> rechercher(HashMap<String, String> selectors) throws CreatingStatementException, SQLException;
	public int supprimer(Category c) throws KeysNotFound, CreatingStatementException ;
}