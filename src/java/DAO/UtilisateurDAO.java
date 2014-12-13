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
import model.Category;
import model.Utilisateur;

/**
 *
 * @author faiez
 */
public class UtilisateurDAO implements IUtilisateurDAO {

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `utilisateur`";
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
            dataset.putInt("userID", rs.getInt("userID"));
            dataset.putString("dateInscription", rs.getString("dateInscription"));
            dataset.putString("dateNaissance", rs.getString("dateNaissance"));
            dataset.putString("interests", rs.getString("interests"));
            dataset.putString("password", rs.getString("password"));
            dataset.putString("pays", rs.getString("pays"));
            dataset.putString("username", rs.getString("username"));
            dataset.putString("email", rs.getString("email"));
            result.add(dataset);
            
        }
        for (int i =0; i < result.size(); i++) {
            String entity = "utilisateur";
            String queryAdm = "SELECT * FROM `admin` WHERE `userID`='"+result.get(i).getInt("userID") +"'";
            ResultSet rsAdm = stmt.executeQuery(queryAdm);
            if (rsAdm.next() == true) {
                entity = "admin";
            }
            result.get(i).setEntity(entity);
        }
        return result;
    }

    @Override
    public int inserer(Utilisateur utilisateur) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            String interests = "";
            if ((utilisateur.getInterests() != null) && (! utilisateur.getInterests().isEmpty())) {
                for (int i=0; i<utilisateur.getInterests().size(); i++) {
                    interests += utilisateur.getInterests().get(i) + ", ";
                }
                interests = interests.substring(0, interests.length() - 2);
            }
            
            int utilisateurID = stmt. executeUpdate("INSERT INTO `utilisateur` (`userID`, `dateInscription`, `dateNaissance`, `interests`, `password`, `pays`, `username`, `email`) VALUES (NULL, '"+utilisateur.getDateInscription().getTime()+"', '"+utilisateur.getDateNaissance().getTime()+"', '"+interests+"', '"+utilisateur.getPassword()+"', '"+utilisateur.getPays()+"', '"+utilisateur.getUsername()+"', '"+utilisateur.getEmail()+"');", Statement.RETURN_GENERATED_KEYS);
            utilisateur.setId(utilisateurID);
            return utilisateurID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }

    @Override
    public int modifier(Utilisateur utilisateur, HashMap<String, String> updates) throws CreatingStatementException,SQLException {
        Statement stmt = DatabaseManager.getStatement();
        if (updates.isEmpty()) return -1;
        String query = "UPDATE `utilisateur` SET ";
        for(Map.Entry<String, String> entry : updates.entrySet()) {
                query += "`"+entry.getKey()+"`="+entry.getValue()+", ";
        }
        query = query.substring(0, query.length()-2);
        query += "WHERE `userID` = "+utilisateur.getId()+";";
        int rs = stmt.executeUpdate(query);
        return rs;
    }

    @Override
    public int supprimer(Utilisateur utilisateur) throws KeysNotFound, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `utilisateur` WHERE `userID` = "+utilisateur.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }

    @Override
    public int inscrireUtilisateurCategory(Utilisateur u, Category c) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int userID = stmt. executeUpdate("INSERT INTO `utilisateur_category` (`userID`, `categoryID`) VALUES ("+u.getId()+", '"+c.getId()+"');", Statement.RETURN_GENERATED_KEYS);
            return userID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }
    
    @Override
    public int supprimerUtilisateurCategory(Utilisateur u, Category c) throws CreatingStatementException, KeysNotFound {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `utilisateur_category` WHERE `userID` = "+u.getId()+" AND `categoryID` = "+c.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }
    
    @Override
    public ArrayList<IDataset> rechercherUtilisateurByCategory(Category c) throws CreatingStatementException, SQLException {
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `utilisateur_category` WHERE `categoryID` = "+c.getId()+";";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            IDataset dataset = new Dataset();
            dataset.putInt("userID", rs.getInt("userID"));
            dataset.putString("categoryID", rs.getString("categoryID"));
        }
        return result;
    }
    
    @Override
    public ArrayList<IDataset> rechercherCategoryByUtilisateur(Utilisateur u) throws CreatingStatementException, SQLException {
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `utilisateur_category` WHERE `userID` = "+u.getId()+";";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            IDataset dataset = new Dataset();
            dataset.putInt("userID", rs.getInt("userID"));
            dataset.putString("categoryID", rs.getString("categoryID"));
        }
        return result;
    }
    
}
