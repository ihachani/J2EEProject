package DAO;

public interface IDAOFactory {
    public IAuteurDAO createAuteurDAO ();
    public ICategoryDAO createCategoryDAO ();
    public IDemandeLivreDAO createDemandeLivreDAO ();
    public ILivreDAO createLivreDAO ();
    public INoteDAO createNoteDAO ();
    public IUtilisateurDAO createUtilisateurDAO ();
}
