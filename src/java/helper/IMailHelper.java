/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import exceptions.EMailCannotBeSent;

/**
 *
 * @author faiez
 */
public interface IMailHelper {

    public void sendMail(String email, String msg) throws EMailCannotBeSent;
    
}
