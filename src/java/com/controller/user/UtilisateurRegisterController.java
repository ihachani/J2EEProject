/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.user;

import config.MessageRegistry;
import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.UserEmailExisted;
import exceptions.UserUsernameExisted;
import helper.Cryptography;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
/**
 *
 * @author faiez
 */
@Controller
public class UtilisateurRegisterController {

    @RequestMapping(value = "/register-utilisateur", method = RequestMethod.GET)
    public ModelAndView registerForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return new ModelAndView("registerForm", "command", new Utilisateur());
        } else {
            ModelAndView modelAndView = new ModelAndView("hello");
            String notificationMsg = MessageRegistry.alreadyLogged;
            modelAndView.addObject("notificationMsg", notificationMsg);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/register-utilisateur", method = RequestMethod.POST)
    public String registerForm(@ModelAttribute Utilisateur utilisateur, ModelMap model, HttpSession session) {
            if (session.getAttribute("user") != null) {
                String notificationMsg = MessageRegistry.alreadyLogged;
                model.addAttribute("notificationMsg", notificationMsg);
                return "hello";
            }
            if ((utilisateur.getUsername().isEmpty()) || (utilisateur.getPassword().isEmpty()) || (utilisateur.getEmail().isEmpty())) {
                String errorMsg = MessageRegistry.fillAllRequiredFields;
                model.addAttribute("errorMsg", errorMsg);
            } else {
                try {
                    utilisateur.setPassword(Cryptography.MD5(utilisateur.getPassword()));
                    utilisateur.setDateInscription(new Date());
                    IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;
                    utilisateurManager.isncrire(utilisateur);
                    String validationMsg = MessageRegistry.registerWithSuccess;
                    model.addAttribute("validationMsg", validationMsg);
                } catch (UserEmailExisted ex) {
                    String errorMsg = MessageRegistry.userEmailAlreadyExisted;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                } catch (UserUsernameExisted ex) {
                    String errorMsg = MessageRegistry.userUsernameAlreadyExisted;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                } catch (KeyAlreadyExisted ex) {
                    String errorMsg = MessageRegistry.userEmailAlreadyExisted;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                } catch (UnsupportedEncodingException ex) {
                    String errorMsg = MessageRegistry.internalError;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                } catch (NoSuchAlgorithmException ex) {
                    String errorMsg = MessageRegistry.internalError;
                    model.addAttribute("errorMsg", errorMsg);
                    return "registerForm";
                }
            }
            return "hello";
    }
}
