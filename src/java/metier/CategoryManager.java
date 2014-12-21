package metier;

import DAO.DAOFactory;
import DAO.ICategoryDAO;
import DAO.IDemandeLivreDAO;
import com.sun.faces.util.CollectionsUtils;
import containers.CategoryContainer;
import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.IDataset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Category;
import model.DemandeLivre;


public class CategoryManager implements ICategoryManager {

    @Override
    public int ajouter(Category c) throws KeyAlreadyExisted, CreatingStatementException, SQLException  {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        HashMap<String, String> selectors = new HashMap<String, String>();
        selectors.put("titre", c.getTitre());
        ArrayList<Category> categories = null;
        categories = this.rechercher (selectors);
        if (categories != null) {
            throw new KeyAlreadyExisted();
        } else {
            System.out.println("addingCat"+"INSERT INTO `category` (`categoryID`, `titre`) VALUES (NULL, '"+c.getTitre()+"');");
            int livreID = categoryDAO.inserer(c);
            c.setId(livreID);
            return livreID;
        }
    }

    @Override
    public int modifier(Category c, HashMap<String, String> updates) throws CreatingStatementException, SQLException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        int state = categoryDAO.modifier(c, updates);
        c.setTitre(updates.get("titre"));
        return state;
    }

    @Override
    public ArrayList<Category> rechercher(HashMap<String, String> selectors) throws CreatingStatementException, SQLException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        ArrayList<IDataset> categoriesDataset = categoryDAO.rechercher(selectors, null);
        ArrayList<Category> resCategories = new ArrayList<Category>();
        for (int i = 0; i < categoriesDataset.size(); i++) {
            IDataset currentDataset = categoriesDataset.get(i);
            Category category = CategoryContainer.GetInstance().getCategory(currentDataset.getInt("categoryID"));
            if (category != null) resCategories.add(category);
        }
        if (resCategories.size() > 0) return resCategories;
        return null;
    }

    @Override
    public int supprimer(Category c) throws KeysNotFound, CreatingStatementException {
        ICategoryDAO categoryDAO = DAOFactory.getInstance().createCategoryDAO();
        int state = categoryDAO.supprimer(c);
        return state;
    }
    
}
