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
import model.Livre;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author faiez
 */
@Controller
public class CategoryUpdateController {
    
    @RequestMapping(value = "/modifier-category", method = RequestMethod.GET)
    public ModelAndView modifierCategoryForm(WebRequest webRequest, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                int categoryID = getCategoryID(webRequest);
                if ((categoryID == -1) || (categoryID == 0)) {
                    String errorMsg = MessageRegistry.categoryIDMissing;
                    modelAndView.addObject("errorMsg", errorMsg);
                } else {
                    HashMap<String, String> selectors = new HashMap<String, String>();
                    selectors.put("categoryID", new Integer(categoryID).toString());
                    ICategoryManager categoryManager = MetierRegistry.categoryManager;
                    try {
                        ArrayList<Category> categoriesRes = categoryManager.rechercher(selectors);
                        if ((categoriesRes == null) || (categoriesRes.isEmpty())) {
                            throw new CategoryNotFound();
                        }
                        Category category = categoriesRes.get(0);
                        modelAndView = new ModelAndView("modifierCategoryForm", "command", category);
                    } catch (CategoryNotFound ex) {
                        Logger.getLogger(CategoryUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (CreatingStatementException ex) {
                        Logger.getLogger(CategoryUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CategoryUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteUser;
                modelAndView.addObject("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/modifier-category", method = RequestMethod.POST)
    public ModelAndView modifierCategory(@ModelAttribute Category reqCategory, WebRequest webRequest, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
         if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                int categoryID = getCategoryID(webRequest);
                if ((categoryID == -1) || (categoryID == 0)) {
                    String errorMsg = MessageRegistry.categoryIDMissing;
                    modelAndView.addObject("errorMsg", errorMsg);
                } else {
                    HashMap<String, String> selectors = new HashMap<String, String>();
                    selectors.put("categoryID", new Integer(categoryID).toString());
                    ICategoryManager categoryManager = MetierRegistry.categoryManager;
                    try {
                        ArrayList<Category> categoriesRes = categoryManager.rechercher(selectors);
                        if ((categoriesRes == null) || (categoriesRes.isEmpty())) {
                            throw new CategoryNotFound();
                        }
                        Category category = categoriesRes.get(0);
                        modelAndView = new ModelAndView("modifierCategoryForm", "command", category);
                        if (!reqCategory.getTitre().trim().isEmpty()) {
                            HashMap<String, String> updates = new HashMap<String, String>();
                            updates.put("titre", reqCategory.getTitre().trim());
                            categoryManager.modifier(category, updates);
                            String validationMsg = MessageRegistry.categoryInformationsModifiedWithSuccess;
                            modelAndView.addObject("validationMsg", validationMsg);
                        }
                    } catch (CategoryNotFound ex) {
                        Logger.getLogger(CategoryUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (CreatingStatementException ex) {
                        Logger.getLogger(CategoryUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CategoryUpdateController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteUser;
                modelAndView.addObject("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
    private int getCategoryID(WebRequest webRequest) {
        String sCategoryID = webRequest.getParameter("CategoryID");
        int CategoryID = 0;
        if (sCategoryID != null) {
            try {
                CategoryID = Integer.parseInt(sCategoryID);
            } catch (NumberFormatException e) {
                CategoryID = -1;
            }
        }
        return CategoryID;
    }
    
}
