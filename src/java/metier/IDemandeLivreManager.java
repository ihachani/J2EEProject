package metier;

import exceptions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.DemandeLivre;
import model.Livre;

public interface IDemandeLivreManager {
	public int ajouterDemande(DemandeLivre demande) throws KeyAlreadyExisted, SQLException,CreatingStatementException;
	public ArrayList<DemandeLivre> rechercherDemandeLivre(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException;
	public int repondreDemande(DemandeLivre d, Livre l) throws KeysNotFound, EMailCannotBeSent, CreatingStatementException;
}