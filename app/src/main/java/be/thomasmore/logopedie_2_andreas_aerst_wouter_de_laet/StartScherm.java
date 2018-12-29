package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.Date;
import java.util.List;

public class StartScherm extends AppCompatActivity {
    private DatabaseHelper db;
    private int year, month, day;
    private long currentPatientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_scherm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        DrawerUtil.getDrawer(this,toolbar);

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

        long daysBetween = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date geboortedatumT = formatter.parse(stringGeboortedatum);
            Date testdatumOmgezet = formatter.parse(stringTestdatum);
            formatter.format(geboortedatumT);
            formatter.format(testdatumOmgezet);
            daysBetween = (testdatumOmgezet.getTime()-geboortedatumT.getTime())/86400000;
            daysBetween = Math.abs(daysBetween);
            patient.setGeboortedatum(geboortedatumT);
            patient.setTestdatum(testdatumOmgezet);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        patient.setNaam(naamString);
        patient.setChronologischeLeeftijd(daysBetween);
        patient.setSoortAfasie(afasieString);
        patient.setGeslacht(geslachtString);
        patient.setScoreProductiviteit(Integer.parseInt(productiviteitString));
        patient.setScoreEfficientie(Integer.parseInt(efficientieString));
        patient.setScoreSubstitutie(Integer.parseInt(substitutieString));
        patient.setScoreCoherentie(Integer.parseInt(coherentieString));
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

                naam.setText(patient.getNaam(), TextView.BufferType.EDITABLE);
                geboortedatum.setText(patient.getGeboortedatum().toString(), TextView.BufferType.EDITABLE);
                testdatum.setText(patient.getTestdatum().toString(), TextView.BufferType.EDITABLE);
                chronologischeLeeftijd.setText(patient.getChronologischeLeeftijd() + "", TextView.BufferType.EDITABLE);
                geslacht.setText(patient.getGeslacht(), TextView.BufferType.EDITABLE);
                afasie.setText(patient.getSoortAfasie(), TextView.BufferType.EDITABLE);
                currentPatientId = patientID;
            }
        });
    }
}
