package metier;


import model.Livre;

/**
 * @author faiez
 * @version 1.0
 * @created 23-nov.-2014 11:56:57
 */
public interface IAmazonWSManager {
	public Livre getLivreByISBN(String isbn);
}