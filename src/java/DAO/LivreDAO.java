/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import exceptions.CreatingStatementException;
import exceptions.KeyAlreadyExisted;
import exceptions.KeysNotFound;
import helper.Dataset;
import helper.IDataset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Auteur;
import model.Livre;
import model.Note;

/**
 *
 * @author faiez
 */
public class LivreDAO implements ILivreDAO {

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `livre`";
        if (selectors != null) {
            query += " WHERE ";
            for(Map.Entry<String, String> entry : selectors.entrySet()) {
                query += "`"+entry.getKey()+"`="+entry.getValue()+" AND ";
            }
            query = query.substring(0, query.length()-4);
        }
        
        if (order != null) {
            query += " ORDER BY ";
            for(Map.Entry<String, String> entry : selectors.entrySet()) {
                query += "`"+entry.getKey()+"` "+entry.getValue()+", ";
            }
            query = query.substring(0, query.length()-2);
        }

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            IDataset dataset = new Dataset();
            dataset.putString("isbn", rs.getString("isbn"));
            dataset.putString("titre", rs.getString("titre"));
            dataset.putString("cover", rs.getString("cover"));
            dataset.putString("dateAjout", rs.getString("dateAjout"));
            dataset.putString("datePublication", rs.getString("datePublication"));
            dataset.putString("description", rs.getString("description"));
            dataset.putString("langue", rs.getString("langue"));
            dataset.putInt("categoryID", rs.getInt("categoryID"));
            dataset.putInt("userID", rs.getInt("userID"));
            result.add(dataset);
        }
        return result;
    }

    @Override
    public ArrayList<IDataset> rechercher(String selectors, String order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `livre`";
        if (selectors != null) {
            query += " WHERE " + selectors;
        }
        
        if (order != null) {
            query += " ORDER BY " + order;
        }
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            IDataset dataset = new Dataset();
            dataset.putString("isbn", rs.getString("isbn"));
            dataset.putString("titre", rs.getString("titre"));
            dataset.putString("cover", rs.getString("cover"));
            dataset.putString("dateAjout", rs.getString("dateAjout"));
            dataset.putString("datePublication", rs.getString("datePublication"));
            dataset.putString("description", rs.getString("description"));
            dataset.putString("langue", rs.getString("langue"));
            dataset.putInt("categoryID", rs.getInt("categoryID"));
            dataset.putInt("userID", rs.getInt("userID"));
            result.add(dataset);
        }
        
        return result;
    }
    
    @Override
    public int inserer(Livre livre) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int livreID = stmt. executeUpdate("INSERT INTO `livre` (`isbn`, `titre`, `cover`, `dateAjout`, `datePublication`, `description`, `langue`, `categoryID`, `userID`) "
                    + "VALUES ('"+livre.getIsbn()+"', '"+livre.getTitre()+"', '"+livre.getCover()+"', '"+livre.getDateAjout().getTime()+"', '"+livre.getDatePublication().getTime()+"', '"+livre.getDescription()+"', '"+livre.getLangue()+"', '"+livre.getCategory().getId()+"', '"+livre.getUtilisateur().getId()+"');", Statement.RETURN_GENERATED_KEYS);
            return livreID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }

    @Override
    public int modifier(Livre livre, HashMap<String, String> updates) throws CreatingStatementException, SQLException{
        Statement stmt = DatabaseManager.getStatement();
        if (updates.isEmpty()) return -1;
        String query = "UPDATE `livre` SET ";
        for(Map.Entry<String, String> entry : updates.entrySet()) {
                query += "`"+entry.getKey()+"`="+entry.getValue()+", ";
        }
        query = query.substring(0, query.length()-2);
        query += "WHERE `isbn` = "+livre.getIsbn()+";";
        int rs = stmt.executeUpdate(query);
        return rs;
    }

    @Override
    public int supprimer(Livre livre) throws KeysNotFound,CreatingStatementException  {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `livre` WHERE `isbn` = "+livre.getIsbn()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }

    
    @Override
    public int evaluerLivre(Note n) throws CreatingStatementException, KeyAlreadyExisted {
        INoteDAO noteDAO = DAOFactory.getInstance().createNoteDAO();
        return noteDAO.inserer(n);
    }

    @Override
    public ArrayList<IDataset> rechercherAuteur(Livre livre) throws CreatingStatementException, SQLException {
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `livre_auteur`";
        query += " WHERE `livreISBN`='"+livre.getIsbn()+"';";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            IDataset dataset = new Dataset();
            dataset.putInt("livreID", rs.getInt("livreISBN"));
            dataset.putInt("auteurID", rs.getInt("auteurID"));
        }
        return result;
    }
    
    @Override
    public int insererAuteur (Livre livre, Auteur auth) throws KeyAlreadyExisted, CreatingStatementException  {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int ID = stmt. executeUpdate("INSERT INTO `livre_auteur` (`livreISBN`, `auteurID`) VALUES ("+livre.getIsbn()+", '"+auth.getId()+"');", Statement.RETURN_GENERATED_KEYS);
            return ID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }
    
    public int supprimerAuteur (Livre livre, Auteur auth)  throws KeysNotFound, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `livre_auteur` WHERE `livreISBN` = "+livre.getIsbn()+" AND `auteurID`= "+auth.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }

}
