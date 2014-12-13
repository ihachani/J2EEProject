/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import DAO.DAOFactory;
import DAO.ICategoryDAO;
import exceptions.CategoryNotFound;
import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;

/**
 *
 * @author faiez
 */
public class CategoryContainer {
    private HashMap<Integer, Category> categories = new HashMap<Integer, Category> ();
    
    public Category getCategory (int key) throws CategoryNotFound, CreatingStatementException, SQLException {
        Category category = null;
        if((category = categories.get(key)) != null) {
            return category;
        }
       
       category = this.loadCategory(key);
       
        if (category == null)  {
            throw new CategoryNotFound();
        }
        categories.put(category.getId(), category);
        return category;
    }
    
    protected Category loadCategory (int key) throws CreatingStatementException, SQLException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        HashMap<String, String> selectors = new HashMap<String, String> ();
        selectors.put("categoryID", Integer.toString(key));
        ArrayList<IDataset> categoriesData = categoryDAO.rechercher(selectors, null);
        if (categoriesData.size() == 0) return null;
        Category category  = new Category(categoriesData.get(0).getInt ("categoryID"), categoriesData.get(0).getString ("titre"));
        return category;
    }
    
    private static CategoryContainer categoryContainer;
    public static CategoryContainer GetInstance () {
        if (categoryContainer == null) {
            categoryContainer = new CategoryContainer();
        }
        return categoryContainer;
    }
}
