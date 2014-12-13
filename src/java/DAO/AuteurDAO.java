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
import java.util.Map.Entry;
import model.Auteur;

/**
 *
 * @author faiez
 */
public class AuteurDAO implements IAuteurDAO {

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `auteur`";
        if (selectors != null) {
            query += " WHERE ";
            for(Entry<String, String> entry : selectors.entrySet()) {
                query += "`"+entry.getKey()+"`="+entry.getValue()+" AND ";
            }
            query = query.substring(0, query.length()-4);
        }
        
        if (order != null) {
            query += " ORDER BY ";
            for(Entry<String, String> entry : selectors.entrySet()) {
                query += "`"+entry.getKey()+"` "+entry.getValue()+", ";
            }
            query = query.substring(0, query.length()-2);
        }

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            IDataset dataset = new Dataset();
            dataset.putInt("auteurID", rs.getInt("auteurID"));
            dataset.putString("nom", rs.getString("nom"));
            result.add(dataset);
        }
        return result;
    }

    @Override
    public int inserer(Auteur a) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int auteurID = stmt. executeUpdate("INSERT INTO `auteur` (`auteurID`, `nom`) VALUES (NULL, '"+a.getNom()+"');", Statement.RETURN_GENERATED_KEYS);
            a.setId(auteurID);
            return auteurID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }

    @Override
    public int modifier(Auteur a, HashMap<String, String> updates) throws CreatingStatementException, SQLException{
        Statement stmt = DatabaseManager.getStatement();
        if (updates.isEmpty()) return -1;
        String query = "UPDATE `auteur` SET ";
        for(Entry<String, String> entry : updates.entrySet()) {
                query += "`"+entry.getKey()+"`="+entry.getValue()+", ";
        }
        query = query.substring(0, query.length()-2);
        query += "WHERE `auteurID` = "+a.getId()+";";
        int rs = stmt.executeUpdate(query);
        return rs;
    }

    @Override 
    public int supprimer(Auteur a) throws KeysNotFound, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `auteur` WHERE `auteurID` = "+a.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }
    
}
