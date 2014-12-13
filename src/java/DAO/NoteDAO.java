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
import model.Note;

/**
 *
 * @author faiez
 */
public class NoteDAO implements INoteDAO{

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `note`";
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
            dataset.putInt("noteID", rs.getInt("noteID"));
            dataset.putInt("userID", rs.getInt("userID"));
            dataset.putString("livreISBN", rs.getString("livreISBN"));
            dataset.putString("date", rs.getString("date"));
            dataset.putInt("rate", rs.getInt("rate"));
            dataset.putString("review", rs.getString("review"));
            result.add(dataset);
        }
        return result;
    }

    @Override
    public int inserer(Note note) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int noteID = stmt. executeUpdate("INSERT INTO `note` (`noteID`, `userID`, `livreISBN`, `date`, `rate`, `review`) VALUES (NULL, '"+note.getUtilisateur().getId()+"', '"+note.getLivre().getIsbn()+"', '"+note.getDate().getTime()+"', '"+note.getRate()+"', '"+note.getReview()+"');", Statement.RETURN_GENERATED_KEYS);
            note.setId(noteID);
            return noteID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }

    @Override
    public int supprimer(Note note) throws KeysNotFound, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `note` WHERE `noteID` = "+note.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }
    
}
