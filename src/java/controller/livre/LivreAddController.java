package controller.livre;

import config.MessageRegistry;
import containers.CategoryContainer;
import controller.command.LivreCommand;
import controller.command.NoteCommand;
import exceptions.*;
import helper.PDFToPicture;
import helper.URLResolver;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.IAuteurManager;
import metier.ICategoryManager;
import metier.ILivreManager;
import metier.MetierRegistry;
import model.Auteur;
import model.Category;
import model.Livre;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LivreAddController {
    
    @RequestMapping(value = "/ajouter-livre", method = RequestMethod.GET)
    public ModelAndView ajouterLivreForm(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            modelAndView = new ModelAndView("ajouterLivreForm", "command", new LivreCommand());
            ArrayList<Category> categories = null;
            ICategoryManager categoryManager = MetierRegistry.categoryManager;
            try {
                categories = categoryManager.rechercher(null);
                modelAndView.addObject("categories", categories);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
            try {
                ArrayList<Category> categoriesList = categoryManager.rechercher(null);
                modelAndView.addObject("categories", categoriesList);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);;
        }
        return modelAndView;
    }

    @RequestMapping(value = "/ajouter-livre", method = RequestMethod.POST)
    public ModelAndView ajouterLivre(@ModelAttribute LivreCommand livreCommand, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            modelAndView = new ModelAndView("ajouterLivreForm", "command", livreCommand);
            ArrayList<Category> categories = null;
            ICategoryManager categoryManager = MetierRegistry.categoryManager;
            try {
                categories = categoryManager.rechercher(null);
                modelAndView.addObject("categories", categories);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
            try {
                ArrayList<Category> categoriesList = categoryManager.rechercher(null);
                modelAndView.addObject("categories", categoriesList);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
            try {
                if ((livreCommand.getIsbn().isEmpty()) || (livreCommand.getTitre().isEmpty())) {
                    String errorMsg = MessageRegistry.fillAllRequiredFields;
                    modelAndView.addObject("errorMsg", errorMsg);
                } else {
                    String cover = PDFToPicture.getPictureFromPDF(livreCommand.getIsbn());
                    int categoryID = Integer.parseInt(livreCommand.getCategoryID());
                    CommonsMultipartFile multipartFile = livreCommand.getFile();
                    //if(multipartFile.getSize()==0){
			//String errorMsg = MessageRegistry.selectAValidFile;
                        //modelAndView.addObject("errorMsg", errorMsg);
                        //return modelAndView;
                    //}
                    //String fileName = URLResolver.getBookURI(livreCommand.getIsbn());
                    //byte[] bytes = multipartFile.getBytes();
                    //BufferedOutputStream stream =  new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                    //stream.write(bytes);
                    //stream.close();
                    Category category = CategoryContainer.GetInstance().getCategory(categoryID);
                    if (category == null) {
                        String errorMsg = MessageRegistry.falseParameters;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } else {
                        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
                        ArrayList<String> RequestAuteurs = livreCommand.getAuteurs();
                        ArrayList<Auteur> LivreAuteurs = null;
                        try {
                            IAuteurManager auteurManager = MetierRegistry.auteurManager;
                            LivreAuteurs = auteurManager.extractAuteurs(RequestAuteurs);
                        } catch (KeyAlreadyExisted ex1) {
                            String errorMsg = MessageRegistry.databaseConnectionError;
                            modelAndView.addObject("errorMsg", errorMsg);
                        } catch (CreatingStatementException ex1) {
                            String errorMsg = MessageRegistry.databaseConnectionError;
                            modelAndView.addObject("errorMsg", errorMsg);
                        } catch (SQLException ex) {
                            String errorMsg = MessageRegistry.databaseConnectionError;
                            modelAndView.addObject("errorMsg", errorMsg);
                        }
                        int state = utilisateur.isAdmin();
                        ILivreManager livreManager = MetierRegistry.livreManager;
                        try {
                            Livre livre = new Livre(cover, new Date(), livreCommand.getDatePublication(), livreCommand.getDescription(), livreCommand.getIsbn(), livreCommand.getLangue(), livreCommand.getTitre(), category, LivreAuteurs, (Utilisateur) session.getAttribute("user"), state, 0);
                            livreManager.ajouterLivre(livre);
                            modelAndView = new ModelAndView("redirect:afficher-livre?isbn="+livre.getIsbn());
                        } catch (KeyAlreadyExisted ex) {
                            String errorMsg = MessageRegistry.bookAlreadyExisted;
                            modelAndView.addObject("errorMsg", errorMsg);
                        } catch (EMailCannotBeSent ex) {
                            String errorMsg = MessageRegistry.eMailCannotBeSent;
                            modelAndView.addObject("errorMsg", errorMsg);
                        }
                    }
                }
            //} catch (IOException ex) {
               // String errorMsg = MessageRegistry.fileCannotBeUploaded;
                //modelAndView.addObject("errorMsg", errorMsg);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
}
