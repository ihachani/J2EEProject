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

/**
 *
 * @author faiez
 */
public class CategoryDAO implements ICategoryDAO {

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `category`";
        
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
            dataset.putInt("categoryID", rs.getInt("categoryID"));
            dataset.putString("titre", rs.getString("titre"));
            result.add(dataset);
        }
        return result;
    }

    @Override
    public int inserer(Category cat) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int categoryID = stmt. executeUpdate("INSERT INTO `category` (`categoryID`, `titre`) VALUES (NULL, '"+cat.getTitre()+"');", Statement.RETURN_GENERATED_KEYS);
            cat.setId(categoryID);
            return categoryID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }

    @Override
    public int modifier(Category c, HashMap<String, String> updates) throws CreatingStatementException, SQLException{
        Statement stmt = DatabaseManager.getStatement();
        if (updates.isEmpty()) return -1;
        String query = "UPDATE `category` SET ";
        for(Map.Entry<String, String> entry : updates.entrySet()) {
                query += "`"+entry.getKey()+"`="+entry.getValue()+", ";
        }
        query = query.substring(0, query.length()-2);
        query += "WHERE `categoryID` = "+c.getId()+";";
        int rs = stmt.executeUpdate(query);
        return rs;
    }

    @Override 
    public int supprimer(Category c) throws KeysNotFound, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `category` WHERE `categoryID` = "+c.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }
    
}
