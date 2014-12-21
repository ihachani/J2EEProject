package controller.bookrequest;

import config.MessageRegistry;
import controller.command.LivreResondCommand;
import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import metier.IDemandeLivreManager;
import metier.ILivreManager;
import metier.MetierRegistry;
import model.DemandeLivre;
import model.Livre;
import model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookRequestRespondController {
    
    @RequestMapping(value = "/demande-livre-list", method = RequestMethod.GET)
    public String respondBookRequestList (ModelMap model, HttpSession session) {
        if ((session.getAttribute("user") != null)) {
            IDemandeLivreManager demandeLivreManager = MetierRegistry.demandeLivreManager;
            HashMap<String, String> order = new HashMap<String, String>();
            order.put("date", "DESC");
            try {
                ArrayList<DemandeLivre> demandesLivres = demandeLivreManager.rechercherDemandeLivre(null, order);
                if ((demandesLivres == null) || (demandesLivres.isEmpty())) throw new KeysNotFound();
                model.addAttribute("demandesLivres", demandesLivres);
                return "demandesLivres";
            } catch (KeysNotFound ex) {
                String errorMsg = MessageRegistry.noDemandesFound;
                model.addAttribute("errorMsg", errorMsg);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                model.addAttribute("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                model.addAttribute("errorMsg", errorMsg);
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            model.addAttribute("errorMsg", errorMsg);
        }
        return "hello";
    }
    
    @RequestMapping(value = "/demande-livre-reponse", method = RequestMethod.GET)
    public ModelAndView respondBookRequestForm (WebRequest webRequest, HttpSession session) {
        IDemandeLivreManager demandeLivreManager = MetierRegistry.demandeLivreManager;
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            modelAndView = new ModelAndView("demandesLivres");
            HashMap<String, String> order = new HashMap<String, String>();
            order.put("date", "DESC");
            try {
                ArrayList<DemandeLivre> demandesLivres = demandeLivreManager.rechercherDemandeLivre(null, order);
                if ((demandesLivres == null) || (demandesLivres.isEmpty())) throw new KeysNotFound();
                modelAndView.addObject("demandesLivres", demandesLivres);
            } catch (KeysNotFound ex) {
                String errorMsg = MessageRegistry.noDemandesFound;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
            int demandeID = getDemandeID (webRequest);
            if (demandeID == -1) {
                String errorMsg = MessageRegistry.fillAllRequiredFields;
                modelAndView.addObject("errorMsg", errorMsg);
            } else {
                HashMap<String, String> selectors = new HashMap<String, String>();
                selectors.put("demandeID", new Integer(demandeID).toString());
                try {
                    ArrayList<DemandeLivre> demandesLivres = demandeLivreManager.rechercherDemandeLivre(selectors, null);
                    if ((demandesLivres == null) || (demandesLivres.isEmpty())) throw new KeysNotFound();
                    modelAndView = new ModelAndView("respondBookRequestForm", "command", new LivreResondCommand());
                    modelAndView.addObject("demande", demandesLivres.get(0));
                } catch (KeysNotFound ex) {
                    String errorMsg = MessageRegistry.demandeLivreNotFound;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    modelAndView.addObject("errorMsg", errorMsg);
                }
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/demande-livre-reponse", method = RequestMethod.POST)
    public ModelAndView respondBookRequestFormHandler (@ModelAttribute LivreResondCommand livreResondCommand, WebRequest webRequest, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if ((session.getAttribute("user") != null)) {
            IDemandeLivreManager demandeLivreManager = MetierRegistry.demandeLivreManager;
            modelAndView = new ModelAndView("demandesLivres");
            HashMap<String, String> order = new HashMap<String, String>();
            order.put("date", "DESC");
            ArrayList<DemandeLivre> demandesLivresList = null;
            try {
                demandesLivresList = demandeLivreManager.rechercherDemandeLivre(null, order);
                if ((demandesLivresList == null) || (demandesLivresList.isEmpty())) throw new KeysNotFound();
                modelAndView.addObject("demandesLivres", demandesLivresList);
            } catch (KeysNotFound ex) {
                String errorMsg = MessageRegistry.noDemandesFound;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (CreatingStatementException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            } catch (SQLException ex) {
                String errorMsg = MessageRegistry.databaseConnectionError;
                modelAndView.addObject("errorMsg", errorMsg);
            }
            int demandeID = getDemandeID (webRequest);
            if (demandeID == -1) {
                String errorMsg = MessageRegistry.fillAllRequiredFields;
                modelAndView.addObject("errorMsg", errorMsg);
            } else {
                
                HashMap<String, String> selectors = new HashMap<String, String>();
                selectors.put("demandeID", new Integer(demandeID).toString());
                try {
                    ArrayList<DemandeLivre> demandesLivres = demandeLivreManager.rechercherDemandeLivre(selectors, null);
                    if ((demandesLivres == null) || (demandesLivres.isEmpty())) throw new KeysNotFound();
                    modelAndView = new ModelAndView("respondBookRequestForm", "command", livreResondCommand);
                    DemandeLivre demande = demandesLivres.get(0);
                    modelAndView.addObject("demande", demande);
                    if (livreResondCommand.getIsbn().isEmpty()) {
                        String errorMsg = MessageRegistry.fillAllRequiredFields;
                        modelAndView.addObject("errorMsg", errorMsg);
                    } else {
                        selectors.clear();
                        ILivreManager livreManager = MetierRegistry.livreManager;
                        selectors.put("isbn", livreResondCommand.getIsbn());
                        ArrayList<Livre> resLivres = null;
                        resLivres = livreManager.rechercherLivre(selectors, null); 
                        if (resLivres == null) {
                            String errorMsg = MessageRegistry.selectedBookNotFound;
                            modelAndView.addObject("errorMsg", errorMsg);
                        } else {
                            Livre livre = resLivres.get(0);
                            try {
                                demandeLivreManager.repondreDemande(demande, livre);
                                modelAndView = new ModelAndView("demandesLivres");
                                String validationMsg = MessageRegistry.demandeLivreRespondedWithSuccess;
                                try {
                                    demandesLivresList = demandeLivreManager.rechercherDemandeLivre(null, order);
                                    if ((demandesLivresList == null) || (demandesLivresList.isEmpty())) throw new KeysNotFound();
                                    modelAndView.addObject("demandesLivres", demandesLivresList);
                                } catch (KeysNotFound ex) {
                                    String errorMsg = MessageRegistry.noDemandesFound;
                                    modelAndView.addObject("errorMsg", errorMsg);
                                } catch (CreatingStatementException ex) {
                                    String errorMsg = MessageRegistry.databaseConnectionError;
                                    modelAndView.addObject("errorMsg", errorMsg);
                                } catch (SQLException ex) {
                                    String errorMsg = MessageRegistry.databaseConnectionError;
                                    modelAndView.addObject("errorMsg", errorMsg);
                                }
                                modelAndView.addObject("demandesLivres", demandesLivresList);
                                modelAndView.addObject("validationMsg", validationMsg);
                            } catch (KeysNotFound ex) {
                                String errorMsg = MessageRegistry.selectedBookNotFound;
                                modelAndView.addObject("errorMsg", errorMsg);
                            } catch (EMailCannotBeSent ex) {
                                String errorMsg = MessageRegistry.eMailCannotBeSent;
                                modelAndView.addObject("errorMsg", errorMsg);
                            }
                        }
                    }
                } catch (KeysNotFound ex) {
                    String errorMsg = MessageRegistry.demandeLivreNotFound;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (CreatingStatementException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    modelAndView.addObject("errorMsg", errorMsg);
                } catch (SQLException ex) {
                    String errorMsg = MessageRegistry.databaseConnectionError;
                    modelAndView.addObject("errorMsg", errorMsg);
                }
            }
        } else {
            String errorMsg = MessageRegistry.mustLogFirst;
            modelAndView.addObject("errorMsg", errorMsg);
        }
        return modelAndView;
    }

    private int getDemandeID(WebRequest webRequest) {
        String sdemandeID = webRequest.getParameter("demandeID");
        int demandeID = -1;
        if (sdemandeID != null) {
            sdemandeID = sdemandeID.trim();
            try {
                demandeID = Integer.parseInt(sdemandeID);
            } catch (NumberFormatException e) {
                demandeID = 0;
            }
        }
        return demandeID;
    }
}
