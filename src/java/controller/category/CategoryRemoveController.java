package controller.category;

import config.MessageRegistry;
import exceptions.CreatingStatementException;
import exceptions.KeysNotFound;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.ICategoryManager;
import metier.MetierRegistry;
import model.Category;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;


@Controller
public class CategoryRemoveController {
    @RequestMapping(value = "/supprimer-category-list", method = RequestMethod.GET)
    public String supprimerCategory(WebRequest webRequest, ModelMap model, HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                int categoryID = getCategoryID(webRequest);
                ICategoryManager categoryManager = MetierRegistry.categoryManager;
                HashMap<String, String> selectors = new HashMap<String, String>();
                try {
                    if (categoryID != -1) {
                        if (categoryID == 0) {
                            String errorMsg = MessageRegistry.categoryIDInvalid;
                            model.addAttribute("errorMsg", errorMsg);
                        } else {
                            selectors.put("categoryID", new Integer(categoryID).toString());
                            ArrayList<Category> categoriesRes = categoryManager.rechercher(selectors);
                            try {
                                if ((categoriesRes == null) || (categoriesRes.isEmpty())) {
                                throw new KeysNotFound();
                            }
                            categoryManager.supprimer(categoriesRes.get(0));
                            String validationMsg = MessageRegistry.categoryDeletedWithSuccess;
                            model.addAttribute("validationMsg", validationMsg);
                            } catch (KeysNotFound ex) {
                                    String errorMsg = MessageRegistry.selectedCategoryNotFound;
                                    model.addAttribute("errorMsg", errorMsg);
                            }
                            
                        }
                    }
                    ArrayList<Category> categoriesList = categoryManager.rechercher(null);
                    model.addAttribute("categories", categoriesList);
                    return "categoriesList";
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                }
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteCategory;
                model.addAttribute("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            model.addAttribute("errorMsg", errorMsg);
        }
        return "hello";
    }

    private int getCategoryID(WebRequest webRequest) {
        String scategoryID = webRequest.getParameter("categoryID");
        int categoryID = -1;
        if (scategoryID != null) {
            scategoryID = scategoryID.trim();
            try {
                categoryID = Integer.parseInt(scategoryID);
            } catch (NumberFormatException e) {
                categoryID = 0;
            }
        }
        return categoryID;
    }
    
}
