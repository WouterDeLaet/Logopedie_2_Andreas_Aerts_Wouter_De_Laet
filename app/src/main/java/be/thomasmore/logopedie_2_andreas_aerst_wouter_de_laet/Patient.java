package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import java.util.Date;

public class Patient {
    private long id;
    private String naam;
    private Date geboortedatum;
    private Date testdatum;
    private Date chronologischeLeeftijd;
    private String geslacht;
    private String soortAfasie;

    public Patient() {
    }

    public Patient(long id, String naam, Date testdatum, Date chronologischeLeeftijd, String geslacht, String soortAfasie, Date geboortedatum) {
        this.id = id;
        this.naam = naam;
        this.geboortedatum = geboortedatum;
        this.testdatum = testdatum;
        this.chronologischeLeeftijd = chronologischeLeeftijd;
        this.geslacht = geslacht;
        this.soortAfasie = soortAfasie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Date getTestdatum() {
        return testdatum;
    }

    public void setTestdatum(Date testdatum) {
        this.testdatum = testdatum;
    }

    public Date getChronologischeLeeftijd() {
        return chronologischeLeeftijd;
    }

    public void setChronologischeLeeftijd(Date chronologischeLeeftijd) {
        this.chronologischeLeeftijd = chronologischeLeeftijd;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getSoortAfasie() {
        return soortAfasie;
    }

    public void setSoortAfasie(String soortAfasie) {
        this.soortAfasie = soortAfasie;
    }

    @Override
    public String toString() {
        return naam;
    }
}
