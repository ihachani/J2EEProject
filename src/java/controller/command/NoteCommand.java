package controller.command;

import java.util.Date;
import model.Livre;
import model.Utilisateur;

public class NoteCommand {
    private String rate;
    private String review;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
