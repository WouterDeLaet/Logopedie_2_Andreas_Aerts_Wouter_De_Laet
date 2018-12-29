package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import java.util.Date;

public class Patient {
    private long id;
    private String naam;
    private String geboortedatum;
    private String testdatum;
    private long chronologischeLeeftijd;
    private String geslacht;
    private String soortAfasie;
    private int scoreProductiviteit;
    private int scoreEfficientie;
    private int scoreSubstitutie;
    private int scoreCoherentie;

    public Patient() {
    }

    public Patient(long id, String naam, String testdatum, long chronologischeLeeftijd, String geslacht, String soortAfasie, String geboortedatum, int scoreProductiviteit, int scoreEfficientie, int scoreSubstitutie, int scoreCoherentie) {
        this.id = id;
        this.naam = naam;
        this.geboortedatum = geboortedatum;
        this.testdatum = testdatum;
        this.chronologischeLeeftijd = chronologischeLeeftijd;
        this.geslacht = geslacht;
        this.soortAfasie = soortAfasie;
        this.scoreProductiviteit = scoreProductiviteit;
        this.scoreEfficientie = scoreEfficientie;
        this.scoreSubstitutie = scoreSubstitutie;
        this.scoreCoherentie = scoreCoherentie;
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

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getTestdatum() {
        return testdatum;
    }

    public void setTestdatum(String testdatum) {
        this.testdatum = testdatum;
    }

    public long getChronologischeLeeftijd() {
        return chronologischeLeeftijd;
    }

    public void setChronologischeLeeftijd(long chronologischeLeeftijd) {
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

    public int getScoreProductiviteit() {
        return scoreProductiviteit;
    }

    public void setScoreProductiviteit(int scoreProductiviteit) {
        this.scoreProductiviteit = scoreProductiviteit;
    }

    public int getScoreEfficientie() {
        return scoreEfficientie;
    }

    public void setScoreEfficientie(int scoreEfficientie) {
        this.scoreEfficientie = scoreEfficientie;
    }

    public int getScoreSubstitutie() {
        return scoreSubstitutie;
    }

    public void setScoreSubstitutie(int scoreSubstitutie) {
        this.scoreSubstitutie = scoreSubstitutie;
    }

    public int getScoreCoherentie() {
        return scoreCoherentie;
    }

    public void setScoreCoherentie(int scoreCoherentie) {
        this.scoreCoherentie = scoreCoherentie;
    }

    @Override
    public String toString() {
        return naam;
    }
}
