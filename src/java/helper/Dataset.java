package helper;

import java.util.Date;
import java.util.HashMap;

public class Dataset implements IDataset{

    private HashMap<String, Object> data = new HashMap<String, Object>();
    private String entity = null;
    
    @Override
    public String getEntity() {
        return entity;
    }
    
    @Override
    public void setEntity (String entity) {
        this.entity = entity;
    }

    @Override
    public void putString(String key, String value) {
        data.put(key, new String(value));
    }

    @Override
    public void putInt(String key, int value) {
        data.put(key, new Integer(value));
    }

    @Override
    public int getInt(String key) {
        Object r = data.get(key);
        if (r instanceof Integer) {
            return ((Integer) r);
        }
        if (r instanceof String) {
            return (Integer.parseInt((String)r));
        }
        return Integer.MIN_VALUE;
    }

    @Override
    public String getString(String key) {
        Object r = data.get(key);
        if (r instanceof String) {
            return ((String) r);
        }
        if (r instanceof Integer) {
            return (((Integer) r).toString());
        }
        return null;
    }

    @Override
    public Date getDate(String key) {
        Object r = data.get(key);
        if (r instanceof Date) {
            return ((Date) r);
        }
        if (r instanceof String) {
            if (! ((String) r).isEmpty()) {
                Date d = new Date();
                d.setTime(Long.parseLong((String) r));
                return (d);
           }
        }
        return null;
    }

    @Override
    public double getDouble(String key) {
        Object r = data.get(key);
        if (r instanceof Double) {
            return ((Double) r);
        }
        if (r instanceof String) {
            return (Double.parseDouble((String)r));
        }
        return Double.MIN_VALUE;
    }
    
}
