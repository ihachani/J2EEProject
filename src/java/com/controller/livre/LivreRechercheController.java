/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.livre;


import config.MessageRegistry;
import exceptions.AuteurNotFound;
import exceptions.CategoryNotFound;
import exceptions.CreatingStatementException;
import exceptions.UtilisateurNotFound;
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

/**
 *
 * @author faiez
 */
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
        } catch (CategoryNotFound ex) {
            //Logger.getLogger(LivreRechercheController.class.getName()).log(Level.SEVERE, null, ex);
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
        // order options here
        String order = null;
        // end order handling
        String search = getTitle(webRequest);
        String where = null;
        if ((search != null) && (! search.isEmpty())) {
            where = " titre LIKE '%" + search + "%'";
        }
        try {
            result = livreManager.rechercherLivre(where, order, page, booksPerPage);
            nbPages = livreManager.getNBPages(" titre LIKE '%" + search + "%'", booksPerPage);
        } catch (CreatingStatementException ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        } catch (SQLException ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        } catch (Exception ex) {
            String errorMsg = MessageRegistry.databaseConnectionError;
            model.addAttribute("errorMsg", errorMsg);
            return "hello";
        }
        model.addAttribute("livres", result);
        model.addAttribute("nbPages", nbPages);
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
}
