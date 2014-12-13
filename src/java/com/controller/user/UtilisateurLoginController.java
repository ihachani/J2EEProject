/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.user;

import config.MessageRegistry;
import exceptions.CreatingStatementException;
import exceptions.UtilisateurNotFound;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import metier.IUtilisateurManager;
import metier.MetierRegistry;
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
public class UtilisateurLoginController {
    
    // to be changed to POST
    //
    @RequestMapping(value = "/login-utilisateur", method = RequestMethod.GET)
    public String loginUtilisateur(WebRequest webRequest, ModelMap model, HttpSession session) {
        String username = getUsername(webRequest);
        String password = getPassword(webRequest);
        int rememberMe = getRememberMe(webRequest);
        if ((username == null) || (password == null)) {
            String errorMsg = MessageRegistry.fillAllRequiredFields;
            model.addAttribute("errorMsg", errorMsg);
        } else {
            IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;
            try {
                Utilisateur utilisateur = utilisateurManager.authentifier(username, password);
                if (utilisateur != null) {
                    if (session.getAttribute("user") == null) {
                        session.setAttribute("user", utilisateur);
                        //rememberMe handling here
                    } else {
                        String notificationMsg = MessageRegistry.alreadyLogged;
                        model.addAttribute("notificationMsg", notificationMsg);
                    }
                } else {
                    throw new UtilisateurNotFound();
                }
            } catch (UtilisateurNotFound ex) {
                String errorMsg = MessageRegistry.userNotRegistered;
                model.addAttribute("errorMsg", errorMsg);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                model.addAttribute("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                model.addAttribute("errorMsg", errorMsg);
            }
        }
        return "hello";
    }    

    @RequestMapping(value = "/deconnect-utilisateur", method = RequestMethod.GET)
    public String deconnectUtilisateur(WebRequest webRequest, ModelMap model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            session.invalidate();
            String validationMsg = MessageRegistry.deconnectionWithSuccess;
            model.addAttribute("validationMsg", validationMsg);
        }
        return "hello";
    }

    protected String getUsername(WebRequest webRequest) {
        String username = webRequest.getParameter("username");
        if (username != null) {
            username = username.trim();
        }
        return username;
    }

    protected String getPassword(WebRequest webRequest) {
        String password = webRequest.getParameter("password");
        if (password != null) {
            password = password.trim();
        }
        return password;
    }

    protected int getRememberMe(WebRequest webRequest) {
        String sRememberMe = webRequest.getParameter("rememberMe");
        int rememberMe = 0;
        if (sRememberMe != null) {
            try {
                rememberMe = Integer.parseInt(sRememberMe);
            } catch (NumberFormatException e) {
                rememberMe = 0;
            }
        }
        return rememberMe;
    }
    
}
