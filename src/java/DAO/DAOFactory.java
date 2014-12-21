package DAO;


public class DAOFactory implements IDAOFactory{

    @Override
    public IAuteurDAO createAuteurDAO() {
        return new AuteurDAO();
    }

    @Override
    public ICategoryDAO createCategoryDAO() {
        return new CategoryDAO();
    }

    @Override
    public IDemandeLivreDAO createDemandeLivreDAO() {
        return new DemandeLivreDAO();
    }

    @Override
    public ILivreDAO createLivreDAO() {
        return new LivreDAO();
    }

    @Override
    public INoteDAO createNoteDAO() {
        return new NoteDAO();
    }
 
    @Override
    public IUtilisateurDAO createUtilisateurDAO() {
        return new UtilisateurDAO();
    }

    private static DAOFactory daoFactory;
    public static DAOFactory getInstance () {
        if (daoFactory == null) {
            daoFactory= new DAOFactory();
        }
        return daoFactory;
    }
}
