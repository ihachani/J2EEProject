package controller.command;

import java.util.Date;
import model.Utilisateur;

public class DemandeLivreCommand {
    private String titre;
    
    public DemandeLivreCommand() {
        this.titre = null;
    }
    
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

}
