package containers;

import DAO.DAOFactory;
import DAO.ICategoryDAO;
import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;

public class CategoryContainer implements ICategoryContainer {
    
    private HashMap<Integer, Category> categories = new HashMap<Integer, Category> ();
    
    @Override
    public Category getCategory (int key) throws CreatingStatementException, SQLException {
        Category category = categories.get(key);
        if(category != null) {
            return category;
        }
       
       category = this.loadCategory(key);
       
        if (category == null)  {
            return null;
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
        Category category  = createCategory(categoriesData.get(0)); 
        return category;
    }
    
    @Override
    public Category createCategory (IDataset dataset) {
        return new Category(dataset.getInt ("categoryID"), dataset.getString ("titre"));
    }

    public HashMap<Integer, Category> getCategories() {
        return categories;
    }
    
    
    
    private static CategoryContainer categoryContainer;
    public static CategoryContainer GetInstance () {
        if (categoryContainer == null) {
            categoryContainer = new CategoryContainer();
        }
        return categoryContainer;
    }
}
