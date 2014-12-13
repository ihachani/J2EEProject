/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import exceptions.EMailCannotBeSent;
import metier.MetierFactory;

/**
 *
 * @author faiez
 */
public class MailHelper implements IMailHelper {

    private static MailHelper mailHelper;
    public static MailHelper getInstance () {
        if (mailHelper == null) {
            mailHelper= new MailHelper();
        }
        return mailHelper;
    }
    
    @Override
    public void sendMail(String email, String msg) throws EMailCannotBeSent {
        
    }
    
}
