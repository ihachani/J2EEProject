/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.user;

import config.MessageRegistry;
import exceptions.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.IUtilisateurManager;
import metier.MetierRegistry;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurRemoveController {

    @RequestMapping(value = "/supprimer-utilisateur", method = RequestMethod.GET)
    public String supprimerUtilisateur(WebRequest webRequest, ModelMap model, HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;
                try {
                    ArrayList<Utilisateur> allUtilisateursList = utilisateurManager.rechercher(null);
                    model.addAttribute("users", allUtilisateursList);
                    int userID = getUserID(webRequest);
                    if (userID == -1) {
                        String errorMsg = MessageRegistry.falseParameters;
                        model.addAttribute("errorMsg", errorMsg);
                    } else if (userID != 0) {
                        HashMap<String, String> selectors = new HashMap<String, String>();
                        selectors.put("userID", new Integer(userID).toString());
                        ArrayList<Utilisateur> utilisateursList = utilisateurManager.rechercher(selectors);
                        Utilisateur utilisateur = utilisateursList.get(0);
                        utilisateurManager.supprimer(utilisateur);
                        String validationMsg = MessageRegistry.userDeletedWithSuccess;
                        model.addAttribute("validationMsg", validationMsg);
                   }
                } catch (KeysNotFound ex) {
                    String errorMsg = MessageRegistry.falseParameters;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (UtilisateurNotFound ex) {
                    String errorMsg = MessageRegistry.falseParameters;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                }
                return "supprimerUtilisateurList";
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

    private int getUserID(WebRequest webRequest) {
        String sUserID = webRequest.getParameter("userID");
        int UserID = 0;
        if (sUserID != null) {
            try {
                UserID = Integer.parseInt(sUserID);
            } catch (NumberFormatException e) {
                UserID = -1;
            }
        }
        return UserID;
    }
}
