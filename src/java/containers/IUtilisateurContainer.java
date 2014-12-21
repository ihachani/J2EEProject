package containers;

import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import model.Utilisateur;

public interface IUtilisateurContainer {
    public Utilisateur getUtilisateur (int key) throws CreatingStatementException, SQLException;
    public Utilisateur createUtilisateur (int key, IDataset dataset);
}
