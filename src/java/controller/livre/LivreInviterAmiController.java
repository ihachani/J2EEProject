package controller.livre;

import config.MessageRegistry;
import controller.command.NoteCommand;
import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.ILivreManager;
import metier.INoteManager;
import metier.MetierRegistry;
import model.Livre;
import model.Note;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LivreInviterAmiController {
    @RequestMapping(value = "/inviter-ami-livre", method = RequestMethod.POST)
    public ModelAndView reviewBookFormHandler (WebRequest webRequest, HttpSession session) {
        String isbn = getISBN (webRequest);
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((isbn == null) || (isbn.isEmpty())) {
            String errorMsg = MessageRegistry.fillAllRequiredFields;
            modelAndView.addObject("errorMsg", errorMsg);
        } else {
            modelAndView = new ModelAndView("bookView", "command", new NoteCommand());
            HashMap<String, String> selectors = new HashMap<String, String>();
            selectors.put("isbn", isbn);
            ILivreManager livreManager = MetierRegistry.livreManager;
            INoteManager noteManager = MetierRegistry.noteManager;
            ArrayList<Livre> resLivres = null;
            try {
                resLivres = livreManager.rechercherLivre(selectors, null);
                Livre livre = resLivres.get(0);
                modelAndView.addObject("livre", livre);
                HashMap<String, String> order = new HashMap<String, String>();
                order.put("date", "DESC");
                ArrayList<Note> notes = noteManager.rechercherNote(livre, order);
                modelAndView.addObject("notes", notes);
                String friendEmail = getEmail (webRequest);
                if (friendEmail == null) {
                    String errorMsg = MessageRegistry.fillAllRequiredFields;
                    modelAndView.addObject("errorMsg", errorMsg);
                } else {
                    try {
                        livreManager.inviterAmi(livre, friendEmail);
                    } catch (EMailCannotBeSent ex) {
                        String errorMsg = MessageRegistry.eMailCannotBeSent;
                        modelAndView.addObject("errorMsg", errorMsg);
                    }
                }
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
                        
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

    private String getEmail(WebRequest webRequest) {
        String email = webRequest.getParameter("email");
        if (email != null) {
            email = email.trim();
        }
        return email;
    }
}
