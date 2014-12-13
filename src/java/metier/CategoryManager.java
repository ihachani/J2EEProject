/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import DAO.DAOFactory;
import DAO.ICategoryDAO;
import DAO.IDemandeLivreDAO;
import com.sun.faces.util.CollectionsUtils;
import containers.CategoryContainer;
import exceptions.CategoryAlreayExisted;
import exceptions.CategoryNotFound;
import exceptions.CreatingStatementException;
import exceptions.DemandeAlreayExisted;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import exceptions.UtilisateurNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.DemandeLivre;

/**
 *
 * @author faiez
 */
public class CategoryManager implements ICategoryManager {

    @Override
    public int ajouter(Category c) throws KeyAlreadyExisted, CategoryAlreayExisted, CreatingStatementException, SQLException  {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        HashMap<String, String> selectors = new HashMap<String, String>();
        selectors.put("titre", c.getTitre());
        ArrayList<Category> categories = null;
        try {
            categories = this.rechercher (selectors);
            if (categories.size() > 0) {
                throw new CategoryAlreayExisted();
            }
        } catch (CategoryNotFound e){
            int state = categoryDAO.inserer(c);
            c.setId(state);
            return state;
        }
        return 0;
    }

    @Override
    public int modifier(Category c, HashMap<String, String> updates) throws CreatingStatementException, SQLException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        int state = categoryDAO.modifier(c, updates);
        return state;
    }

    @Override
    public ArrayList<Category> rechercher(HashMap<String, String> selectors) throws CategoryNotFound, CreatingStatementException, SQLException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        ArrayList<IDataset> categoriesDataset = categoryDAO.rechercher(selectors, null);
        ArrayList<Category> resCategories = new ArrayList<Category>();
        for (int i = 0; i < categoriesDataset.size(); i++) {
            IDataset currentDataset = categoriesDataset.get(i);
            Category category = CategoryContainer.GetInstance().getCategory(currentDataset.getInt("categoryID"));
            resCategories.add(category);
        }
        return resCategories;
    }

    @Override
    public int supprimer(Category c) throws KeysNotFound, CreatingStatementException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        int state = categoryDAO.supprimer(c);
        return state;
    }
    
}
