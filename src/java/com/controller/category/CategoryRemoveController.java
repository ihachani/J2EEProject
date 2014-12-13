/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.category;

import config.MessageRegistry;
import exceptions.CategoryNotFound;
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

/**
 *
 * @author faiez
 */
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
                            if ((categoriesRes == null) || (categoriesRes.isEmpty())) {
                                throw new CategoryNotFound();
                            }
                            categoryManager.supprimer(categoriesRes.get(0));
                            String validationMsg = MessageRegistry.categoryDeletedWithSuccess;
                            model.addAttribute("validationMsg", validationMsg);
                        }
                    }
                    selectors.clear();
                    ArrayList<Category> categoriesList = categoryManager.rechercher(selectors);
                    model.addAttribute("categories", categoriesList);
                } catch (CategoryNotFound ex) {
                    String errorMsg = MessageRegistry.selectedCategoryNotFound;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (KeysNotFound ex) {
                        Logger.getLogger(CategoryRemoveController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                }
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteUser;
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
