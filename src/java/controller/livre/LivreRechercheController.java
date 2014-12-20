package controller.livre;


import config.MessageRegistry;
import exceptions.CreatingStatementException;
import exceptions.KeysNotFound;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.ICategoryManager;
import metier.ILivreManager;
import metier.MetierRegistry;
import model.Livre;
import model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;


@Controller
public class LivreRechercheController {
   @RequestMapping(value = "/recherche-livre", method = RequestMethod.GET)
   public String rechercheLivre(WebRequest webRequest, ModelMap model) {
        ILivreManager livreManager = MetierRegistry.livreManager;
        ArrayList<Livre> result = null;
        int page = getPage(webRequest);
        int booksPerPage = getBooksPerPage(webRequest);
        int nbPages = 1;
        ArrayList<Category> categories = null;
        ICategoryManager categoryManager = MetierRegistry.categoryManager;
        try {
            categories = categoryManager.rechercher(null);
        } catch (CreatingStatementException ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        } catch (SQLException ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        }
        model.addAttribute("categories", categories);
        
        String order = null;
        boolean orderByViews = getOrderByViews(webRequest);
        boolean orderByDate = getOrderByDate(webRequest);
        if (orderByViews || orderByDate) {
            order = "";
            if (orderByViews) order += "views ASC";
            if (orderByDate) order += ((orderByViews)?", ":"")+"datePublication DESC";
        }
        String search = getTitle(webRequest);
        String where = "";
        if ((search != null) && (! search.isEmpty())) {
            where = " titre LIKE '%" + search + "%' AND";
        }
        where += "state='1' ";
        
        model.addAttribute("paginationLink", "recherche-livre?booksPerPage="+booksPerPage+((search != null)?"&bookTitle="+search:"")+((orderByViews)?"&orderByViews=1":"")+((orderByDate)?"&orderByDate=1":""));
        model.addAttribute("currentPage", new Integer(page).toString());
        try {
            nbPages = livreManager.getNBPages(" titre LIKE '%" + (((search != null) && (! search.isEmpty()))?search:"") + "%' AND state='1' ", booksPerPage);
            result = livreManager.rechercherLivre(where, order, page-1, booksPerPage);
            if ((result == null) || (result.isEmpty())) throw new KeysNotFound();
        } catch (KeysNotFound ex) {
            String errorMsg = MessageRegistry.livreNotFound;
            model.addAttribute("errorMsg", errorMsg);
        }catch (CreatingStatementException ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        } catch (SQLException ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        }
        model.addAttribute("livres", result);
        model.addAttribute("nbPages", nbPages);
        if (webRequest.getParameter("errorMsg") != null) model.addAttribute("errorMsg", webRequest.getParameter("errorMsg"));
        return "searchResult";
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
   
   protected String getTitle (WebRequest webRequest) {
       String bookTitle = webRequest.getParameter("bookTitle");
       if (bookTitle != null) {
           bookTitle = bookTitle.trim();
       }
       return bookTitle;
   }
   
   protected int getPage (WebRequest webRequest) {
      String sPage = webRequest.getParameter("page");
      int page = 1;
      if (sPage != null) {
           try {
                page = Integer.parseInt(sPage);
           }catch (NumberFormatException e) {
               page = 1;
           }
       }
       return page;
   }

    private boolean getOrderByViews(WebRequest webRequest) {
        String sorderByViews = webRequest.getParameter("orderByViews");
        if (sorderByViews != null) return true;
        return false;
    }

    private boolean getOrderByDate(WebRequest webRequest) {
        String sorderByViews = webRequest.getParameter("orderByDate");
        if (sorderByViews != null) return true;
        return false;
    }
}
