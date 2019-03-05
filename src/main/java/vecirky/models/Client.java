package vecirky.models;

public class Client extends Person {

    private Integer id;

    private String description;

    private String varSymbol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVarSymbol() {
        return varSymbol;
    }

    public void setVarSymbol(String varSymbol) {
        this.varSymbol = varSymbol;
    }

}
