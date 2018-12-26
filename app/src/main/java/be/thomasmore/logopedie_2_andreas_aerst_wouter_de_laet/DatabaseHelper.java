package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "logopedie";

    // uitgevoerd bij instantiatie van DatabaseHelper
    // -> als database nog niet bestaat, dan creëren (call onCreate)
    // -> als de DATABASE_VERSION hoger is dan de huidige versie,
    //    dan upgraden (call onUpgrade)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren en opvullen met gegevens
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_LOGOPEDIST = "CREATE TABLE logopedist (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "wachtwoord TEXT)";
        db.execSQL(CREATE_TABLE_LOGOPEDIST);

        String CREATE_TABLE_PATIENT = "CREATE TABLE patient (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "naam TEXT," +
                "testdatum DATE," +
                "chronologischeLeeftijd DATE,"+
                "geslacht TEXT,"+
                "soortAfasie TEXT,"+
                "geboortedatum DATE)";
        db.execSQL(CREATE_TABLE_PATIENT);

        insertLogopedists(db);
    }

    private void insertLogopedists(SQLiteDatabase db) {
        db.execSQL("INSERT INTO logopedist (email, wachtwoord) VALUES ('andreas_aerts@hotmail.com', 'test123');");
    }

    private void insertPatients(SQLiteDatabase db) {
        db.execSQL("INSERT INTO patient (naam, testdatum, chronologischeLeeftijd, geslacht, soortAfasie, geboortedatum) VALUES ('Andreas Aerts', 26/12/2018, (26/12/2018) - (25/02/1998), 'man', 'Afasie1', 25/02/1998);");
    }

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS logopedist");
        db.execSQL("DROP TABLE IF EXISTS patient");

        // Create tables again
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    // insert-methode met ContentValues
    public long insertLogopedist(Logopedist logopedist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", logopedist.getEmail());
        values.put("wachtwoord", logopedist.getWachtwoord());

        long id = db.insert("logopedist", null, values);
        db.close();
        return id;
    }

    // update-methode met ContentValues
    public boolean updateLogopedist(Logopedist logopedist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", logopedist.getEmail());
        values.put("wachtwoord", logopedist.getWachtwoord());

        int numrows = db.update(
                "logopedist",
                values,
                "id = ?",
                new String[] { String.valueOf(logopedist.getId()) });

        db.close();
        return numrows > 0;
    }

    // delete-methode
    public boolean deleteLogopedist(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "logopedist",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    public Logopedist getLogopedist(String naam, String passwoord) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "logopedist",      // tabelnaam
                new String[] { "id", "email", "wachtwoord" }, // kolommen
                "email = ? AND wachtwoord = ?",  // selectie
                new String[] { String.valueOf(naam), String.valueOf(passwoord) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Logopedist logopedist = new Logopedist();

        if (cursor.moveToFirst()) {
            logopedist = new Logopedist(cursor.getLong(0),
                    cursor.getString(1), cursor.getString(2));
        }
        cursor.close();
        db.close();
        return logopedist;
    }

    // insert-methode met ContentValues
    public long insertPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", patient.getNaam());
        values.put("testdatum", patient.getTestdatum().toString());
        values.put("geboortedatum", patient.getGeboortedatum().toString());
        values.put("chronologische_leeftijd", patient.getChronologischeLeeftijd().toString());
        values.put("geslacht", patient.getGeslacht());
        values.put("soortAfasie", patient.getSoortAfasie());

        long id = db.insert("patient", null, values);
        db.close();
        return id;
    }

    // update-methode met ContentValues
    public boolean updatePatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", patient.getNaam());
        values.put("testdatum", patient.getTestdatum().toString());
        values.put("geboortedatum", patient.getGeboortedatum().toString());
        values.put("chronologischeLeeftijd", patient.getChronologischeLeeftijd().toString());
        values.put("geslacht", patient.getGeslacht());
        values.put("soortAfasie", patient.getSoortAfasie());

        int numrows = db.update(
                "patient",
                values,
                "id = ?",
                new String[] { String.valueOf(patient.getId()) });

        db.close();
        return numrows > 0;
    }

    // delete-methode
    public boolean deletePatient(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "patient",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    /*public Logopedist getPatient(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "patient",      // tabelnaam
                new String[] { "id", "naam", "testdatum", "geboortedatum","chronologischeLeeftijd","geslacht","soortAfasie" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id)}, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Patient patient = new Patient();

        if (cursor.moveToFirst()) {
            patient = new Patient(cursor.getLong(0),
                    cursor.getString(1), cursor.getLong(2)), Date.parse(cursor.getString(3)),
                    Date.parse(cursor.getString(4)), cursor.getString(5), cursor.getString(6));
        }
        cursor.close();
        db.close();
        return patient;
    }*/
}
