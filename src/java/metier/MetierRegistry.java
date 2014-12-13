/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

/**
 *
 * @author faiez
 */
public class MetierRegistry {
    public static ILivreManager livreManager = new LivreManager();
    public static ICategoryManager categoryManager = new CategoryManager();
    public static IUtilisateurManager utilisateurManager = new UtilisateurManager();
    public static IAuteurManager auteurManager = new AuteurManager();
}
