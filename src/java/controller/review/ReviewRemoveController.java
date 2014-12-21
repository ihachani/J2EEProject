package controller.review;

import config.MessageRegistry;
import controller.command.NoteCommand;
import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.ILivreManager;
import metier.INoteManager;
import metier.MetierRegistry;
import model.Livre;
import model.Note;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReviewRemoveController {
    @RequestMapping(value = "/supprimer-note-livre", method = RequestMethod.GET)
    public ModelAndView reviewBookRemove (WebRequest webRequest, HttpSession session) {
        String isbn = getISBN (webRequest);
        int noteID = getNodeID (webRequest);
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
                if ((session.getAttribute("user") != null)) {
                    if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                        if (noteID == -1) {
                            String errorMsg = MessageRegistry.fillAllRequiredFields;
                            modelAndView.addObject("errorMsg", errorMsg);
                        } else {
                            int i = 0;
                            for (;i < notes.size(); i++) {
                                if (notes.get(i).getId() == noteID) {
                                    break;
                                }
                            }
                            if (notes.get(i).getId() == noteID) {
                                Note note = notes.get(i);
                                try {
                                    noteManager.supprimerNote(note);
                                    modelAndView = new ModelAndView ("redirect:afficher-livre?isbn="+note.getLivre().getIsbn());
                                } catch (KeysNotFound ex) {
                                    String errorMsg = MessageRegistry.selectedNoteNotFound;
                                    modelAndView.addObject("errorMsg", errorMsg); 
                                }
                                //String validationMsg = MessageRegistry.noteDeletedWithSuccess;
                                //modelAndView.addObject("validationMsg", validationMsg);
                            } else {
                                String errorMsg = MessageRegistry.selectedNoteNotFound;
                                modelAndView.addObject("errorMsg", errorMsg); 
                            }
                        }
                    } else {
                        String errorMsg = MessageRegistry.notPermettedToDeleteReview;
                        modelAndView.addObject("errorMsg", errorMsg);
                    }
                } else {
                    String errorMsg = MessageRegistry.mustLogFirst;
                    modelAndView.addObject("errorMsg", errorMsg);
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
    
    private int getNodeID(WebRequest webRequest) {
        String snoteID = webRequest.getParameter("noteID");
        int noteID = -1;
        if (snoteID != null) {
            snoteID = snoteID.trim();
            try {
                noteID = Integer.parseInt(snoteID);
            } catch (NumberFormatException e) {
                noteID = 0;
            }
        }
        return noteID;
    }
    
    private String getISBN(WebRequest webRequest) {
        String isbn = webRequest.getParameter("isbn");
        if (isbn != null) {
            isbn = isbn.trim();
        }
        return isbn;
    }
}
