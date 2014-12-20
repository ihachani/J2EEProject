package model;

public class Category {

    private int id;
    private String titre;

    public Category() {
        this.id = 0;
        this.titre = null;
    }
    
    public Category(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Category) {
            return (((Category) o).getId() == getId());
        }
        return false;
    }


}