package helper;

import model.Livre;

public class URLResolver {

    public static String rootURL = "http://localhost:8080/livre?isbn=";
    
    public static String getBookFullURL (Livre l) {
        return (rootURL + l.getIsbn());
    }
    
    public static String getBookURI (String isbn) {
        return ("books/"+isbn+".pdf");
    }
}
