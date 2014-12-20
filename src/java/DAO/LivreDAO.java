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

public class LivreDAO implements ILivreDAO {
    
    protected static String entity = "livre";
    protected static String auteurREntity = "livre_auteur";

    @Override
    public ArrayList<IDataset> rechercher(HashMap<String, String> selectors, HashMap<String, String> order, int from, int to) throws CreatingStatementException, SQLException{
        Statement stmt = DatabaseManager.getStatement();
        String query = createQuery (selectors, order, from, to, null);
        System.out.println("queryhere"+query);
        ResultSet rs = stmt.executeQuery(query);
        return getListFromResultSet(rs);
    }

    @Override
    public ArrayList<IDataset> rechercher(String selectors, String order, int from, int to) throws CreatingStatementException, SQLException{
        Statement stmt = DatabaseManager.getStatement();
        String query = createQuery (selectors, order, from, to, null);
        
        ResultSet rs = stmt.executeQuery(query);
        return getListFromResultSet(rs);
    }
    
    private ArrayList<IDataset> getListFromResultSet (ResultSet rs) throws SQLException {
        ArrayList<IDataset> result = new ArrayList<IDataset>();
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
            dataset.putInt("views", rs.getInt("views"));
            dataset.putInt("state", rs.getInt("state"));
            dataset.setEntity(entity);
            result.add(dataset);
        }
        return result;
    }
    
    @Override
    public int inserer(Livre livre) throws KeyAlreadyExisted, CreatingStatementException {
        try {
            Statement stmt = DatabaseManager.getStatement();
            int livreID = stmt. executeUpdate("INSERT INTO `livre` (`isbn`, `titre`, `cover`, `dateAjout`, `datePublication`, `description`, `langue`, `categoryID`, `userID`, `views`, `state`) "
                    + "VALUES ('"+livre.getIsbn()+"', '"+livre.getTitre()+"', '"+livre.getCover()+"', '"+livre.getDateAjout().getTime()+"', '"+livre.getDatePublication().getTime()+"', '"+livre.getDescription()+"', '"+livre.getLangue()+"', '"+livre.getCategory().getId()+"', '"+livre.getUtilisateur().getId()+"', '"+livre.getViews()+"', '"+livre.getState()+"');", Statement.RETURN_GENERATED_KEYS);
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
                query += "`"+entry.getKey()+"`=\""+entry.getValue()+"\", ";
        }
        query = query.substring(0, query.length()-2);
        query += " WHERE `isbn` = '"+livre.getIsbn()+"';";
        System.out.println("update here"+query);
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
            dataset.setEntity(auteurREntity);
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
            int rs = stmt.executeUpdate("DELETE FROM `livre_auteur` WHERE `livreISBN` = \""+livre.getIsbn()+"\" AND `auteurID`= \""+auth.getId()+"\";");
            return rs;
        } catch (SQLException e) {
            throw new KeysNotFound();
        }
    }

    @Override
    public int getSetsNumber(HashMap<String, String> selectors) throws CreatingStatementException, SQLException {
        Statement stmt = DatabaseManager.getStatement();
        String query = createQuery (selectors, null, -1, -1, "COUNT");
        ResultSet rs = stmt.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        return count;
    }

    @Override
    public int getSetsNumber(String selectors) throws CreatingStatementException, SQLException {
        Statement stmt = DatabaseManager.getStatement();
        String query = createQuery (selectors, null, -1, -1, "COUNT");
        System.out.println("COUNT"+query);
        ResultSet rs = stmt.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        return count;
    }

    private String createQuery(HashMap<String, String> selectors, HashMap<String, String> order, int from, int to, String function) {
        String query = "SELECT "+((function == null)?"":(function+"("))+"*"+((function == null)?"":(")"))+" FROM `livre`";
        if (selectors != null) {
            query += " WHERE ";
            for(Map.Entry<String, String> entry : selectors.entrySet()) {
                query += "`"+entry.getKey()+"`=\""+entry.getValue()+"\" AND ";
            }
            query = query.substring(0, query.length()-4);
        }
        if (order != null) {
            query += " ORDER BY ";
            for(Map.Entry<String, String> entry : order.entrySet()) {
                query += "`"+entry.getKey()+"` "+entry.getValue()+", ";
            }
            query = query.substring(0, query.length()-2);
        }
        if ((from != -1) && (to != -1)) {
            query += " LIMIT "+from+", "+to;
        }
        query +=";";
        return query;
    }

    private String createQuery(String selectors, String order, int from, int to, String function) {
        String query = "SELECT "+((function == null)?"":(function+"("))+"*"+((function == null)?"":(")"))+" FROM `livre`";
        if (selectors != null) {
            query += " WHERE " + selectors;
        }
        
        if (order != null) {
            query += " ORDER BY " + order;
        }
        if ((from != -1) && (to != -1)) {
            query += " LIMIT "+from+", "+to;
        }
        query +=";";
        return query;
    }

}
