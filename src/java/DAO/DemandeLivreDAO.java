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
import model.DemandeLivre;

/**
 *
 * @author faiez
 */
public class DemandeLivreDAO implements IDemandeLivreDAO{

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order) throws CreatingStatementException, SQLException{
        ArrayList<IDataset> result = new ArrayList<IDataset>();
        Statement stmt = DatabaseManager.getStatement();
        String query = "SELECT * FROM `demandelivre`";
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
            dataset.putInt("demandeID", rs.getInt("demandeID"));
            dataset.putInt("userID", rs.getInt("userID"));
            dataset.putString("titre", rs.getString("titre"));
            dataset.putString("date", rs.getString("date"));
            result.add(dataset);
        }
        return result;
    }

    @Override
    public int inserer(DemandeLivre demande) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int demandeID = stmt. executeUpdate("INSERT INTO `demandelivre` (`demandeID`, `userID`, `titre`, `date`) VALUES (NULL, '"+demande.getUtilisateur().getId()+"', '"+demande.getTitre()+"', '"+demande.getDate().getTime()+"');", Statement.RETURN_GENERATED_KEYS);
            demande.setId(demandeID);
            return demandeID;
        } catch (SQLException e) {
            throw new KeyAlreadyExisted();
        }
    }

    @Override
    public int supprimer(DemandeLivre demande) throws KeysNotFound, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int rs = stmt.executeUpdate("DELETE FROM `demandelivre` WHERE `demandeID` = "+demande.getId()+";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }
    
}
