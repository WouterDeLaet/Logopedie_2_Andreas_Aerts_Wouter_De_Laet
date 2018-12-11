package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

public class Logopedist {
    private long id;
    private String email;
    private String wachtwoord;

    public Logopedist() {
    }

    public Logopedist(long id, String email, String wachtwoord) {
        this.id = id;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
