package containers;

import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import model.Auteur;

public interface IAuteurContainer {
    public Auteur getAuteur (int key) throws CreatingStatementException, SQLException;
    public Auteur createAuteur (IDataset dataset);
}
