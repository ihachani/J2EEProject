/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.category;

import config.MessageRegistry;
import exceptions.CategoryAlreayExisted;
import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.ICategoryManager;
import metier.MetierRegistry;
import model.Category;
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
public class CategoryAddController {

    @RequestMapping(value = "/ajouter-category", method = RequestMethod.GET)
    public ModelAndView ajouterCategoryForm (HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                modelAndView = new ModelAndView("ajouterCategoryForm", "command", new Category());
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteUser;
                modelAndView.addObject("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);;
            
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/ajouter-category", method = RequestMethod.POST)
    public ModelAndView ajouterCategoryForm (@ModelAttribute Category category, ModelMap model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("ajouterCategoryForm");
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                if (category.getTitre().isEmpty()) {
                    String errorMsg = MessageRegistry.fillAllRequiredFields;
                    model.addAttribute("errorMsg", errorMsg);
                } else {
                    ICategoryManager categoryManager = MetierRegistry.categoryManager;
                    try {
                        categoryManager.ajouter(category);
                        modelAndView = new ModelAndView("hello");
                        String validationMsg = MessageRegistry.categoryAddedWithSuccess;
                        model.addAttribute("validationMsg", validationMsg);
                    } catch (CategoryAlreayExisted ex) {
                        String errorMsg = MessageRegistry.categoryAlreadyExisted;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (KeyAlreadyExisted ex) {
                        String errorMsg = MessageRegistry.categoryAlreadyExisted;
                        model.addAttribute("errorMsg", errorMsg);
                    }  catch (CreatingStatementException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (SQLException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    }
                }
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteUser;
                modelAndView.addObject("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);;
            
        }
        return modelAndView;
    }
}
