package metier;


public class MetierRegistry {
    public static ILivreManager livreManager = new LivreManager();
    public static ICategoryManager categoryManager = new CategoryManager();
    public static IUtilisateurManager utilisateurManager = new UtilisateurManager();
    public static IAuteurManager auteurManager = new AuteurManager();
    public static IDemandeLivreManager demandeLivreManager = new DemandeLivreManager();
    public static INoteManager noteManager = new NoteManager();
}
