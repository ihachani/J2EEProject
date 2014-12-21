package containers;

import exceptions.CreatingStatementException;
import helper.IDataset;
import java.sql.SQLException;
import model.Category;

public interface ICategoryContainer {
    public Category getCategory (int key) throws CreatingStatementException, SQLException;
    public Category createCategory (IDataset dataset);
}
