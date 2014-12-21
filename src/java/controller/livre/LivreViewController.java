package controller.livre;

import config.MessageRegistry;
import controller.command.NoteCommand;
import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class LivreViewController {
    @RequestMapping(value = "/afficher-livre", method = RequestMethod.GET)
    public ModelAndView reviewBookFormHandler (WebRequest webRequest, HttpSession session) {
        String isbn = getISBN (webRequest);
        ModelAndView modelAndView = new ModelAndView("bookView", "command", new NoteCommand());
        if ((isbn == null) || (isbn.isEmpty())) {
            modelAndView = new ModelAndView ("redirect:recherche-livre");
        } else {
            HashMap<String, String> selectors = new HashMap<String, String>();
            selectors.put("isbn", isbn);
            ILivreManager livreManager = MetierRegistry.livreManager;
            INoteManager noteManager = MetierRegistry.noteManager;
            ArrayList<Livre> resLivres = null;
            try {
                resLivres = livreManager.rechercherLivre(selectors, null);
                if ((resLivres == null) || (resLivres.isEmpty())) throw new KeysNotFound();
                Livre livre = resLivres.get(0);
                modelAndView.addObject("livre", livre);
                livre.addView();
                HashMap<String, String> updates = new HashMap<String, String>();
                updates.put("views", new Integer(livre.getViews()).toString());
                livreManager.modifer(livre, updates);
                HashMap<String, String> order = new HashMap<String, String>();
                order.put("date", "DESC");
                ArrayList<Note> notes = noteManager.rechercherNote(livre, order);
                modelAndView.addObject("notes", notes);
            } catch (KeysNotFound ex) {
                modelAndView = new ModelAndView ("redirect:recherche-livre");
                String errorMsg = MessageRegistry.livreNotFound;
                modelAndView.addObject("errorMsg", errorMsg);
            }  catch (CreatingStatementException ex) {
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
}
