/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author faiez
 */
public interface IDAOFactory {
    public IAuteurDAO createAuteurDAO ();
    public ICategoryDAO createCategoryDAO ();
    public IDemandeLivreDAO createDemandeLivreDAO ();
    public ILivreDAO createLivreDAO ();
    public INoteDAO createNoteDAO ();
    public IUtilisateurDAO createUtilisateurDAO ();
}
