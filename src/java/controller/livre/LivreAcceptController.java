package controller.livre;

import config.MessageRegistry;
import exceptions.CreatingStatementException;
import exceptions.KeysNotFound;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import metier.ILivreManager;
import metier.MetierRegistry;
import model.Livre;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LivreAcceptController {
    
    @RequestMapping(value = "/accepter-livre-list", method = RequestMethod.GET)
    public String accepterLivreList(WebRequest webRequest, ModelMap model, HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            if (((Utilisateur) session.getAttribute("user")).isAdmin() == 1) {
                ILivreManager livreManager = MetierRegistry.livreManager;
                ArrayList<Livre> result = null;
                int page = getPage(webRequest);
                int booksPerPage = getBooksPerPage(webRequest);
                int nbPages = 1;
                
                try {
                    String isbn = getISBN(webRequest);
                    if ((isbn != null) && (! isbn.isEmpty())) {
                        HashMap<String, String> selectors2 = new HashMap<String, String>();
                        selectors2.put("isbn", isbn);
                        ArrayList<Livre> livresRes;
                        livresRes = livreManager.rechercherLivre(selectors2, null, -1, -1);
                        if ((livresRes == null) || (livresRes.isEmpty())) {
                            throw new KeysNotFound();
                        }
                        Livre livre = livresRes.get(0);
                        if (livre.getState() == 1) {
                            String errorMsg = MessageRegistry.bookAlreadyAccepted;
                            model.addAttribute("errorMsg", errorMsg);
                        } else {
                            HashMap<String, String> updates = new HashMap<String, String>();
                            updates.put("state", "1");
                            livreManager.modifer(livre, updates);
                            String validationMsg = MessageRegistry.bookAcceptedWithSuccess;
                            model.addAttribute("validationMsg", validationMsg);
                        }
                        
                    }
                    HashMap<String, String> selectors = new HashMap<String, String>();
                    selectors.put("state", "0");
                    result = livreManager.rechercherLivre(selectors, null, -1, -1);
                    if ((result == null) || (result.isEmpty())) throw new KeysNotFound();
                    model.addAttribute("livres", result);
                    return "accepterLivreList";
                } catch (KeysNotFound ex) {
                    String errorMsg = MessageRegistry.noRecentBookAdded;
                    model.addAttribute("validationMsg", errorMsg);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    model.addAttribute("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.noRecentBookAdded;
                    model.addAttribute("validationMsg", errorMsg);
                }
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
    
    
    private String getISBN(WebRequest webRequest) {
        String isbn = webRequest.getParameter("isbn");
        if (isbn != null) {
            isbn = isbn.trim();
        }
        return isbn;
    }
    
    protected int getPage (WebRequest webRequest) {
      String sPage = webRequest.getParameter("page");
      int page = 0;
       if (sPage != null) {
           try {
                page = Integer.parseInt(sPage);
           }catch (NumberFormatException e) {
               page = 0;
           }
       }
       return page;
   }
   
   protected int getBooksPerPage (WebRequest webRequest) {
      String sBooksPerPage = webRequest.getParameter("booksPerPage");
      int booksPerPage = config.ConfigRegistry.booksPerPage;
       if (sBooksPerPage != null) {
           try {
               booksPerPage = Integer.parseInt(sBooksPerPage);
           }catch (NumberFormatException e) {
               booksPerPage = config.ConfigRegistry.booksPerPage;
           }
       }
       return booksPerPage;
   }
}
