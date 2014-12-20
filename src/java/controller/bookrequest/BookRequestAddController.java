
package controller.bookrequest;

import config.MessageRegistry;
import controller.command.DemandeLivreCommand;
import exceptions.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.ICategoryManager;
import metier.IDemandeLivreManager;
import metier.MetierRegistry;
import model.Category;
import model.DemandeLivre;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookRequestAddController {
    
    @RequestMapping(value = "/ajouter-demande-livre", method = RequestMethod.GET)
    public ModelAndView addBookRequestForm (HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            modelAndView = new ModelAndView("addBookRequestForm", "command", new DemandeLivreCommand());
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/ajouter-demande-livre", method = RequestMethod.POST)
    public ModelAndView addBookRequestFormHandler(@ModelAttribute DemandeLivreCommand demandeLivreCommand, ModelMap model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            modelAndView = new ModelAndView("addBookRequestForm", "command", demandeLivreCommand);
            if (demandeLivreCommand.getTitre().isEmpty()) {
                String errorMsg = MessageRegistry.fillAllRequiredFields;
                model.addAttribute("errorMsg", errorMsg);
            } else {
                DemandeLivre demandeLivre = new DemandeLivre(new Date(), 0, demandeLivreCommand.getTitre(), (Utilisateur) session.getAttribute("user"));
                IDemandeLivreManager demandeLivreManager = MetierRegistry.demandeLivreManager;
                try {
                    demandeLivreManager.ajouterDemande(demandeLivre);
                    modelAndView = new ModelAndView("hello");
                    String validationMsg = MessageRegistry.demandeLivreAddedWithSuccess;
                    model.addAttribute("validationMsg", validationMsg);
                } catch (KeyAlreadyExisted ex) {
                    String errorMsg = MessageRegistry.demandeAlreadyExisted;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                }
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);;
        }
        return modelAndView;
    }
}
