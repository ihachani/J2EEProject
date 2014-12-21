/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.Date;

/**
 *
 * @author faiez
 */
public interface IDataset {
    public String getEntity ();
    public void setEntity (String entity);
    public void putString(String key, String value);
    public void putInt(String key, int value);
    public int getInt (String key);
    public String getString (String key);
    public Date getDate (String key);
    public double getDouble(String key);
}
