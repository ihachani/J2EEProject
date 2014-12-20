package controller.user;

import config.MessageRegistry;
import controller.command.UtilisateurCommand;
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

@Controller
public class UtilisateurRegisterController {

    @RequestMapping(value = "/register-utilisateur", method = RequestMethod.GET)
    public ModelAndView registerForm(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return new ModelAndView("registerForm", "command", new UtilisateurCommand());
        } else {
            ModelAndView modelAndView = new ModelAndView("hello");
            String notificationMsg = MessageRegistry.alreadyLogged;
            modelAndView.addObject("notificationMsg", notificationMsg);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/register-utilisateur", method = RequestMethod.POST)
    public ModelAndView registerForm(@ModelAttribute UtilisateurCommand utilisateurCommand, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView ("hello");
        if (session.getAttribute("user") != null) {
            String notificationMsg = MessageRegistry.alreadyLogged;
            modelAndView.addObject("notificationMsg", notificationMsg);
        } else {
            modelAndView = new ModelAndView("registerForm", "command", utilisateurCommand);
            if ((utilisateurCommand.getUsername().isEmpty()) || (utilisateurCommand.getPassword().isEmpty()) || (utilisateurCommand.getEmail().isEmpty())) {
                String errorMsg = MessageRegistry.fillAllRequiredFields;
                modelAndView.addObject("errorMsg", errorMsg);
            } else {
                try {
                    String MD5password = Cryptography.MD5(utilisateurCommand.getPassword());
                    Utilisateur utilisateur = new Utilisateur(new Date(), utilisateurCommand.getDateNaissance(), utilisateurCommand.getInterests(), MD5password, utilisateurCommand.getPays(), utilisateurCommand.getUsername(), utilisateurCommand.getEmail());
                    IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;
                    utilisateurManager.isncrire(utilisateur);
                    modelAndView = new ModelAndView ("hello");
                    String validationMsg = MessageRegistry.registerWithSuccess;
                    modelAndView.addObject("validationMsg", validationMsg);
                } catch (UserEmailExisted ex) {
                    String errorMsg = MessageRegistry.userEmailAlreadyExisted;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (UserUsernameExisted ex) {
                    String errorMsg = MessageRegistry.userUsernameAlreadyExisted;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (KeyAlreadyExisted ex) {
                    String errorMsg = MessageRegistry.userAlreadyExisted;
                    modelAndView.addObject("errorMsg", errorMsg);
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
        }
        return modelAndView;
    }
}
