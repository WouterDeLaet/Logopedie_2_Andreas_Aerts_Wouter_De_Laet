package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartScherm extends AppCompatActivity {
    private DatabaseHelper db;
    private long currentPatientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scherm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHelper(this);
        leesPatienten();

    }

    public void goToSpeechToText_onClick(MenuItem m)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToWritingTest_onClick(MenuItem m)
    {
        Intent intent = new Intent(this, writing_test.class);
        intent.putExtra("currentPatientId", currentPatientId);
        startActivity(intent);
    }

    public void goToStartScreen_onClick(MenuItem m)
    {
        Intent intent = new Intent(this, StartScherm.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.dialog_signin, null);
        builder.setTitle(R.string.dialog_signin)
                .setIcon(R.drawable.login)
                .setView(viewInflater)
                .setPositiveButton(R.string.dialog_aanmelden, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText editUsername = (EditText) viewInflater.findViewById(R.id.username);
                        EditText editPassword = (EditText)viewInflater.findViewById(R.id.password);
                        checkLogin(editUsername.getText().toString(), editPassword.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_annuleer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void checkLogin(String login, String passwoord) {
        Logopedist logopedist = db.getLogopedist(login, passwoord);
        Button loginButton = (Button)findViewById(R.id.loginBtn);
        TextView textViewUser = (TextView)findViewById(R.id.user);
        long id = logopedist.getId();
        if (logopedist != null && logopedist.getEmail() != null && logopedist.getWachtwoord() != null && id != 0) {
            loginButton.setVisibility(View.INVISIBLE);
            toon("Welkom " + logopedist.getEmail() + "!");
        } else {
            Logopedist logo = new Logopedist();
            logo.setEmail(login);
            logo.setWachtwoord(passwoord);
            db.insertLogopedist(logo);
            logopedist = db.getLogopedist(login, passwoord);
        }

        textViewUser.setText("Ingelogde gebruiker: " + logopedist.getEmail());
    }

    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClick(View v) {
        showCustomDialog();
    }

    public void onButtonPatientClick(View v) {
        Patient patient = new Patient();
        EditText naam = (EditText)findViewById(R.id.name);
        EditText geboortedatum = (EditText)findViewById(R.id.geboortedatum);
        EditText testdatum = (EditText)findViewById(R.id.testdatum);
        EditText chronologischeLeeftijd = (EditText)findViewById(R.id.chronologischeLeeftijd);
        EditText geslacht = (EditText)findViewById(R.id.geslacht);
        EditText afasie = (EditText)findViewById(R.id.soortAfasie);
        EditText productiviteit = (EditText)findViewById(R.id.scoreProduct);
        EditText efficientie = (EditText)findViewById(R.id.scoreEfficientie);
        EditText substitutiegedrag = (EditText)findViewById(R.id.scoreSubstitutie);
        EditText coherentie = (EditText)findViewById(R.id.scoreCoherentie);
        String stringGeboortedatum = geboortedatum.getText().toString();
        String stringTestdatum = testdatum.getText().toString();
        String naamString = naam.getText().toString();
        String afasieString = afasie.getText().toString();
        String geslachtString = geslacht.getText().toString();
        String productiviteitString = productiviteit.getText().toString();
        String efficientieString = efficientie.getText().toString();
        String substitutieString = substitutiegedrag.getText().toString();
        String coherentieString = coherentie.getText().toString();

        int daysBetween = 0;

        if (testdatum.getText().toString().matches("")) {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date today = Calendar.getInstance().getTime();
            String date = format.format(today);
            patient.setTestdatum(date);
            stringTestdatum = date;
        } else {
            patient.setTestdatum(stringTestdatum);
        }

        if (chronologischeLeeftijd.getText().toString().matches("") || chronologischeLeeftijd.getText().toString().matches("0")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date geboortedatumT = formatter.parse(stringGeboortedatum);
                Date testdatumOmgezet = formatter.parse(stringTestdatum);
                daysBetween = getDifferenceDays(geboortedatumT, testdatumOmgezet);
            } catch (ParseException e) {
                toon("Gelieve een juiste datum op te geven van het formaat dd/mm/yyyy");
            }
        }

        if (productiviteitString.matches("") || productiviteitString == null) {
            patient.setScoreProductiviteit(0);
        } else {
            patient.setScoreProductiviteit(Integer.parseInt(productiviteitString));
        }

        if (efficientieString.matches("") || productiviteitString == null) {
            patient.setScoreEfficientie(0);
        } else {
            patient.setScoreEfficientie(Integer.parseInt(efficientieString));
        }

        if (substitutieString.matches("") || substitutieString == null) {
            patient.setScoreSubstitutie(0);
        } else {
            patient.setScoreSubstitutie(Integer.parseInt(substitutieString));
        }

        if (coherentieString.matches("") || coherentieString == null) {
            patient.setScoreCoherentie(0);
        }else {
            patient.setScoreCoherentie(Integer.parseInt(coherentieString));
        }

        patient.setNaam(naamString);
        patient.setGeboortedatum(stringGeboortedatum);
        patient.setChronologischeLeeftijd(daysBetween);
        patient.setSoortAfasie(afasieString);
        patient.setGeslacht(geslachtString);

        db.insertPatient(patient);

        leesPatienten();
    }

    private void leesPatienten() {
        final List<Patient> patients = db.getPatienten();
        ArrayAdapter<Patient> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);
        final ListView listViewPatienten = (ListView)findViewById(R.id.listViewPatienten);

        listViewPatienten.setAdapter(adapter);
        listViewPatienten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long patientID = patients.get(position).getId();
                Patient patient = db.getPatient(patientID);
                EditText naam = (EditText)findViewById(R.id.name);
                EditText geboortedatum = (EditText)findViewById(R.id.geboortedatum);
                EditText testdatum = (EditText)findViewById(R.id.testdatum);
                EditText chronologischeLeeftijd = (EditText)findViewById(R.id.chronologischeLeeftijd);
                EditText geslacht = (EditText)findViewById(R.id.geslacht);
                EditText afasie = (EditText)findViewById(R.id.soortAfasie);
                EditText productiviteit = (EditText)findViewById(R.id.scoreProduct);
                EditText efficientie = (EditText)findViewById(R.id.scoreEfficientie);
                EditText substitutiegedrag = (EditText)findViewById(R.id.scoreSubstitutie);
                EditText coherentie = (EditText)findViewById(R.id.scoreCoherentie);

                naam.setText(patient.getNaam());
                geboortedatum.setText(patient.getGeboortedatum());
                testdatum.setText(patient.getTestdatum());
                chronologischeLeeftijd.setText(patient.getChronologischeLeeftijd() + "");
                geslacht.setText(patient.getGeslacht());
                afasie.setText(patient.getSoortAfasie());
                productiviteit.setText(patient.getScoreProductiviteit() + "");
                efficientie.setText(patient.getScoreEfficientie() + "");
                substitutiegedrag.setText(patient.getScoreSubstitutie() + "");
                coherentie.setText(patient.getScoreCoherentie() + "");
                currentPatientId = patientID;
            }
        });
    }

    public void onRemoveClick(View v) {
        db.deletePatient(currentPatientId);
        clearAllTextfields();
        leesPatienten();
    }

    private void clearAllTextfields() {
        EditText naam = (EditText)findViewById(R.id.name);
        EditText geboortedatum = (EditText)findViewById(R.id.geboortedatum);
        EditText testdatum = (EditText)findViewById(R.id.testdatum);
        EditText chronologischeLeeftijd = (EditText)findViewById(R.id.chronologischeLeeftijd);
        EditText geslacht = (EditText)findViewById(R.id.geslacht);
        EditText afasie = (EditText)findViewById(R.id.soortAfasie);
        EditText productiviteit = (EditText)findViewById(R.id.scoreProduct);
        EditText efficientie = (EditText)findViewById(R.id.scoreEfficientie);
        EditText substitutiegedrag = (EditText)findViewById(R.id.scoreSubstitutie);
        EditText coherentie = (EditText)findViewById(R.id.scoreCoherentie);

        naam.setText("");
        geboortedatum.setText("");
        testdatum.setText("");
        chronologischeLeeftijd.setText("");
        geslacht.setText("");
        afasie.setText("");
        productiviteit.setText("");
        efficientie.setText("");
        substitutiegedrag.setText("");
        coherentie.setText("");
    }

    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }
}
