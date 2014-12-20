package controller.livre;

import config.MessageRegistry;
import containers.CategoryContainer;
import containers.UtilisateurContainer;
import exceptions.*;
import helper.PDFToPicture;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.IAuteurManager;
import metier.ILivreManager;
import metier.IUtilisateurManager;
import metier.MetierRegistry;
import model.Auteur;
import model.Category;
import model.Livre;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LivreRemoveController {

    @RequestMapping(value = "/supprimer-livre", method = RequestMethod.GET)
    public String supprimerLivre(WebRequest webRequest, ModelMap model, HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                ILivreManager livreManager = MetierRegistry.livreManager;
                String isbn = getISBN(webRequest);
                if (isbn != null) {
                    HashMap<String, String> selectors = new HashMap<String, String>();
                    selectors.put("isbn", isbn);
                    try {
                        ArrayList<Livre> livresRes = livreManager.rechercherLivre(selectors, null, 0, 1);
                        if ((livresRes == null) || (livresRes.isEmpty())) {
                            throw new KeysNotFound();
                        }
                        livreManager.supprimerLivre(livresRes.get(0));
                        String validationMsg = MessageRegistry.bookDeletedWithSuccess;
                        model.addAttribute("validationMsg", validationMsg);
                    } catch (KeysNotFound ex) {
                        String errorMsg = MessageRegistry.falseParameters;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (CreatingStatementException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    } catch (SQLException ex) {
                        String errorMsg = MessageRegistry.databaseConnectionError;
                        model.addAttribute("errorMsg", errorMsg);
                    }
                } else {
                    String errorMsg = MessageRegistry.falseParameters;
                    model.addAttribute("errorMsg", errorMsg);
                }
            } else {
                String errorMsg = MessageRegistry.notPermettedToDeleteUser;
                model.addAttribute("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            model.addAttribute("errorMsg", errorMsg);
        }
        return "redirect:recherche-livre";
    }

    private String getISBN(WebRequest webRequest) {
        String isbn = webRequest.getParameter("isbn");
        if (isbn != null) {
            isbn = isbn.trim();
        }
        return isbn;
    }

}
