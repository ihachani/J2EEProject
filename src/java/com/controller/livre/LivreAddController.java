/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.livre;

import config.MessageRegistry;
import containers.CategoryContainer;
import exceptions.*;
import helper.PDFToPicture;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import metier.IAuteurManager;
import metier.ILivreManager;
import metier.MetierRegistry;
import model.Auteur;
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
public class LivreAddController {
    
    @RequestMapping(value = "/ajouter-livre", method = RequestMethod.GET)
    public ModelAndView ajouterLivreForm(HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            return new ModelAndView("ajouterLivreForm", "command", new Livre());
        } else {
            ModelAndView modelAndView = new ModelAndView("hello");
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);;
            return modelAndView;
        }
    }

    @RequestMapping(value = "/ajouter-livre", method = RequestMethod.POST)
    public String ajouterLivre(@ModelAttribute Livre livre, WebRequest myWebRequest, ModelMap model, HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            String cover = PDFToPicture.getPictureFromPDF(livre.getIsbn());
            livre.setDateAjout(new Date());
            int categoryID = getCategoryID(myWebRequest);
            String file = null;
            /*
             *
             * THE FILE MUST BE HANDELED HERE
             *
             */
            try {
                if ((livre.getIsbn().isEmpty()) || (livre.getTitre().isEmpty()) || (file.isEmpty())) {
                    String errorMsg = MessageRegistry.fillAllRequiredFields;
                    model.addAttribute("errorMsg", errorMsg);
                } else {
                    Category category = CategoryContainer.GetInstance().getCategory(categoryID);
                    livre.setCategory(category);
                    Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
                    int userID = ((Utilisateur) session.getAttribute("user")).getId();
                    ArrayList<String> RequestAuteurs = getAuteurs(myWebRequest);
                    try {
                        ArrayList<Auteur> LivreAuteurs = extractAuteurs(RequestAuteurs);
                        livre.setAuteurs(LivreAuteurs);
                    } catch (KeyAlreadyExisted ex1) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (CreatingStatementException ex1) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (SQLException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    }
                    /*
                     *
                     *
                     * ADD FILE TO required PATH
                     *
                     *
                     */
                    int state = utilisateur.isAdmin();
                    ILivreManager livreManager = MetierRegistry.livreManager;
                    try {
                        livreManager.ajouterLivre(livre);
                        String validationMsg = MessageRegistry.bookAddedWithSuccess;
                        model.addAttribute("validationMsg", validationMsg);
                        model.addAttribute("livre", livre);
                        return "livre";
                    } catch (AuteurNotFound ex) {
                        String errorMsg = MessageRegistry.invalidAuteursEntry;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (KeyAlreadyExisted ex) {
                        String errorMsg = MessageRegistry.bookAlreadyExisted;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (UtilisateurNotFound ex) {
                        String errorMsg = MessageRegistry.mustLogFirst;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (EMailCannotBeSent ex) {
                        String errorMsg = MessageRegistry.eMailCannotBeSent;
                        model.addAttribute("errorMsg", errorMsg);
                    }
                }
            } catch (CategoryNotFound ex) {
                String errorMsg = MessageRegistry.falseParameters;
                model.addAttribute("errorMsg", errorMsg);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                model.addAttribute("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                model.addAttribute("errorMsg", errorMsg);
            }
            return "ajouterLivreForm";
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            model.addAttribute("errorMsg", errorMsg);
        }
        return "hello";
    }
    
    protected ArrayList<Auteur> extractAuteurs(ArrayList<String> RequestAuteurs) throws CreatingStatementException, SQLException, KeyAlreadyExisted {
        IAuteurManager auteurManager = MetierRegistry.auteurManager;
        ArrayList<Auteur> livreAuteurs = new ArrayList<Auteur>();
        for (int i = 0; i < RequestAuteurs.size(); i++) {
            HashMap<String, String> selectors = new HashMap<String, String>();
            selectors.put("nom", RequestAuteurs.get(i));
            Auteur currentAuteur = null;
            try {
                ArrayList<Auteur> auteur = auteurManager.rechercher(selectors);
                currentAuteur = auteur.get(0);
            } catch (AuteurNotFound ex) {
                currentAuteur = new Auteur(-1, RequestAuteurs.get(i));
                auteurManager.ajouter(currentAuteur);
            }
            livreAuteurs.add(currentAuteur);
        }
        return livreAuteurs;
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

    private ArrayList<String> getAuteurs(WebRequest webRequest) {
        String sAuteurs = webRequest.getParameter("auteurs");
        ArrayList<String> auteurs = new ArrayList<String>();
        if (sAuteurs != null) {
            sAuteurs = sAuteurs.trim();
            String[] auteursParts = sAuteurs.split(",");
            for (int i = 0; i < auteursParts.length; i++) {
                auteurs.add(auteursParts[i]);
            }
        }
        return auteurs;
    }
}
