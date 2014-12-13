/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.user;

import config.MessageRegistry;
import exceptions.CreatingStatementException;
import helper.Cryptography;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import metier.IUtilisateurManager;
import metier.MetierRegistry;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class UtilisateurUpdateController {
    
    @RequestMapping(value = "/modifier-utilisateur", method = RequestMethod.GET)
    public ModelAndView modifiertUtilisateurform(HttpSession session) {
        if (session.getAttribute("user") != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            return new ModelAndView("modifierUtilisateurForm", "command", utilisateur);
        } else {
            ModelAndView modelAndView = new ModelAndView("hello");
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/modifier-utilisateur", method = RequestMethod.POST)
    public ModelAndView modifiertUtilisateur(@ModelAttribute Utilisateur utilisateur, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if (session.getAttribute("user") != null) {
            modelAndView = new ModelAndView("modifierUtilisateurForm", "command", session.getAttribute("user"));
            IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;
            if ((utilisateur.getUsername().isEmpty()) || (utilisateur.getEmail().isEmpty())) {
                String errorMsg = MessageRegistry.fillAllRequiredFields;
                modelAndView.addObject("errorMsg", errorMsg);
            } else {
                try {
                    HashMap<String, String> updates = new HashMap<String, String>();
                    updates.put("username", utilisateur.getUsername());
                    if (! utilisateur.getPassword().isEmpty()) {
                        updates.put("password", Cryptography.MD5(utilisateur.getPassword()));
                    }
                    updates.put("dateNaissance", new Long(utilisateur.getDateNaissance().getTime()).toString());
                    updates.put("interests", utilisateur.getInterestsString());
                    updates.put("pays", utilisateur.getPays());
                    utilisateurManager.modifier((Utilisateur) session.getAttribute("user"), updates);
                    String validationMsg = MessageRegistry.userInformationsModifiedWithSuccess;
                    modelAndView.addObject("validationMsg", validationMsg);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (UnsupportedEncodingException ex) {
                    String errorMsg = MessageRegistry.internalError;
                        modelAndView.addObject("errorMsg", errorMsg);
                } catch (NoSuchAlgorithmException ex) {
                    String errorMsg = MessageRegistry.internalError;
                    modelAndView.addObject("errorMsg", errorMsg);
                }
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
}
