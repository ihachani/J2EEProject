package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.DemandeLivre;
import model.Livre;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 10:47:33
 */
public interface IDemandeLivreManager {
	public int ajouterDemande(DemandeLivre demande) throws KeyAlreadyExisted, DemandeAlreayExisted, UtilisateurNotFound, SQLException,CreatingStatementException;
	public ArrayList<DemandeLivre> rechercherDemandeLivre(HashMap<String, String> selectors, HashMap<String, String> order) throws UtilisateurNotFound, CreatingStatementException, SQLException;
	public int repondreDemande(DemandeLivre d, Livre l) throws KeysNotFound, EMailCannotBeSent, CreatingStatementException;
}