package controller.user;

import config.MessageRegistry;
import controller.command.UtilisateurUpdateCommand;
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
        ModelAndView modelAndView = new ModelAndView("hello");
        if (session.getAttribute("user") != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            UtilisateurUpdateCommand utilisateurUpdateCommand = new UtilisateurUpdateCommand();
            utilisateurUpdateCommand.setInterests(utilisateur.getInterests());
            utilisateurUpdateCommand.setPays(utilisateur.getPays());
            utilisateurUpdateCommand.setUsername(utilisateur.getUsername());
            modelAndView = new ModelAndView("modifierUtilisateurForm", "command", utilisateurUpdateCommand);
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/modifier-utilisateur", method = RequestMethod.POST)
    public ModelAndView modifiertUtilisateur(@ModelAttribute UtilisateurUpdateCommand utilisateurUpdateCommand, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if (session.getAttribute("user") != null) {
            modelAndView = new ModelAndView("modifierUtilisateurForm", "command", utilisateurUpdateCommand);
            IUtilisateurManager utilisateurManager = MetierRegistry.utilisateurManager;
            try {
                HashMap<String, String> updates = new HashMap<String, String>();
                if (! utilisateurUpdateCommand.getUsername().isEmpty()) {
                    updates.put("username", utilisateurUpdateCommand.getUsername());
                    ((Utilisateur) session.getAttribute("user")).setUsername(utilisateurUpdateCommand.getUsername());
                }
                if (! utilisateurUpdateCommand.getPassword().isEmpty()) {
                    updates.put("password", Cryptography.MD5(utilisateurUpdateCommand.getPassword()));
                    ((Utilisateur) session.getAttribute("user")).setPassword(utilisateurUpdateCommand.getPassword());
                }
                if ((utilisateurUpdateCommand.getInterests() != null) && (! utilisateurUpdateCommand.getInterests().isEmpty())) {
                    updates.put("interests", utilisateurUpdateCommand.getInterestsString());
                    ((Utilisateur) session.getAttribute("user")).setInterests(utilisateurUpdateCommand.getInterests());
                }
                if (! utilisateurUpdateCommand.getPays().isEmpty()) {
                    updates.put("pays", utilisateurUpdateCommand.getPays());
                    ((Utilisateur) session.getAttribute("user")).setPays(utilisateurUpdateCommand.getPays());
                    
                }
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
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
}
