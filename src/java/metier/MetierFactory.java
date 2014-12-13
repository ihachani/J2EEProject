/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

/**
 *
 * @author faiez
 */
public class MetierFactory implements IMetierFactory {
    
    
    
    @Override
    public IUtilisateurManager createIUtilisateurManager() {
        return (new UtilisateurManager());
    }
    
    
    private static MetierFactory metierFactory;
    public static MetierFactory getInstance () {
        if (metierFactory == null) {
            metierFactory= new MetierFactory();
        }
        return metierFactory;
    }
}
