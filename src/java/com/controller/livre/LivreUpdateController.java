/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.livre;

import config.MessageRegistry;
import containers.CategoryContainer;
import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import metier.ILivreManager;
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
public class LivreUpdateController {

    @RequestMapping(value = "/modifier-livre", method = RequestMethod.GET)
    public ModelAndView modifierLivreForm(WebRequest webRequest, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                String isbn = getISBN(webRequest);
                if (isbn == null) {
                    String errorMsg = MessageRegistry.fillAllRequiredFields;
                    modelAndView.addObject("errorMsg", errorMsg);
                } else {
                    HashMap<String, String> selectors = new HashMap<String, String>();
                    selectors.put("isbn", isbn);
                    ArrayList<Livre> livresRes;
                    try {
                        ILivreManager livreManager = MetierRegistry.livreManager;
                        livresRes = livreManager.rechercherLivre(selectors, null, 0, 1);
                        if ((livresRes == null) || (livresRes.isEmpty())) {
                            throw new KeysNotFound();
                        }
                        Livre livre = livresRes.get(0);
                        modelAndView = new ModelAndView("modifierLivreForm", "command", livre);
                        return modelAndView;
                    } catch (CategoryNotFound ex) {
                        String errorMsg = MessageRegistry.invalidCategoryEntry;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (KeysNotFound ex) {
                        String errorMsg = MessageRegistry.falseParameters;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (AuteurNotFound ex) {
                        String errorMsg = MessageRegistry.invalidAuteursEntry;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (UtilisateurNotFound ex) {
                        String errorMsg = MessageRegistry.invalidUtilisateurEntry;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (CreatingStatementException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (SQLException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        modelAndView.addObject("errorMsg", errorMsg);
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

    @RequestMapping(value = "/modifier-livre", method = RequestMethod.POST)
    public ModelAndView modifierLivre(@ModelAttribute Livre reqLivre, WebRequest myWebRequest, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                if (reqLivre.getIsbn().isEmpty()) {
                    String errorMsg = MessageRegistry.fillAllRequiredFields;
                    modelAndView.addObject("errorMsg", errorMsg);
                } else {
                    try {
                        HashMap<String, String> selectors = new HashMap<String, String>();
                        selectors.put("isbn", reqLivre.getIsbn());
                        ILivreManager livreManager = MetierRegistry.livreManager;
                        ArrayList<Livre> livresRes = livreManager.rechercherLivre(selectors, null, 0, 1);
                        if ((livresRes == null) || (livresRes.isEmpty())) {
                            throw new KeysNotFound();
                        }
                        Livre livre = livresRes.get(0);
                        String titre = reqLivre.getTitre();
                        String description = reqLivre.getDescription();
                        String langue = reqLivre.getLangue();
                        int categoryID = getCategoryID(myWebRequest);
                        HashMap<String, String> updates = new HashMap<String, String>();
                        modelAndView = new ModelAndView("modifierLivreForm", "command", livre);
                        if ((titre != null) || (description != null) || (langue != null) || (categoryID != -1)) {
                            if (!titre.isEmpty()) {
                                updates.put("titre", titre);
                            }
                            if (!description.isEmpty()) {
                                updates.put("description", titre);
                            }
                            if (!langue.isEmpty()) {
                                updates.put("langue", titre);
                            }
                            if (categoryID != -1) {
                                Category category = CategoryContainer.GetInstance().getCategory(categoryID);
                                reqLivre.setCategory(category);
                                updates.put("categoryID", new Integer(categoryID).toString());
                            }
                            livreManager.modifer(livre, updates);
                            String validationMsg = MessageRegistry.bookInformationsModifiedWithSuccess;
                            modelAndView.addObject("validationMsg", validationMsg);
                        }
                    } catch (CategoryNotFound ex) {
                        String errorMsg = MessageRegistry.falseParameters;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (KeysNotFound ex) {
                        String errorMsg = MessageRegistry.falseParameters;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (AuteurNotFound ex) {
                        String errorMsg = MessageRegistry.invalidAuteursEntry;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (UtilisateurNotFound ex) {
                        String errorMsg = MessageRegistry.invalidUtilisateurEntry;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (CreatingStatementException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } catch (SQLException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        modelAndView.addObject("errorMsg", errorMsg);
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

    private String getISBN(WebRequest webRequest) {
        String isbn = webRequest.getParameter("isbn");
        if (isbn != null) {
            isbn = isbn.trim();
        }
        return isbn;
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
