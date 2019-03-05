package vecirky.models;

public class Promoter extends Person {

    private String bankAccount;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Promoter)) return false;
        if (getId() == null) return false;
        return getId().equals(((Promoter) o).getId());
    }
}
