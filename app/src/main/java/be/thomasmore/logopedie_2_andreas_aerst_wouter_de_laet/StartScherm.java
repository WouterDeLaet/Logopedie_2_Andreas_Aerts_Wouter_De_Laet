package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

    private void showPatientDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.dialog_patient, null);
        builder.setTitle(R.string.dialog_signin)
                .setIcon(R.drawable.login)
                .setView(viewInflater)
                .setPositiveButton(R.string.dialog_aanmelden, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        save();
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
        showPatientDialog();
    }

    public void save() {
        EditText naam = (EditText)findViewById(R.id.name);
        EditText geboortedatum = (EditText)findViewById(R.id.geboortedatum);
        EditText testdatum = (EditText)findViewById(R.id.testdatum);
        EditText chronologischeLeeftijd = (EditText)findViewById(R.id.chronologischeLeeftijd);
        EditText geslacht = (EditText)findViewById(R.id.geslacht);
        EditText afasie = (EditText)findViewById(R.id.soortAfasie);

        Patient patient = new Patient();
        patient.setNaam(naam.getText().toString());
        patient.setGeboortedatum((Date) geboortedatum.getText());
        patient.setTestdatum((Date) testdatum.getText());
        patient.setChronologischeLeeftijd((Date) chronologischeLeeftijd.getText());
        patient.setSoortAfasie(afasie.getText().toString());
        patient.setGeslacht(geslacht.getText().toString());
        db.insertPatient(patient);

        leesPatienten();
    }

    private void leesPatienten() {
        final List<Patient> patients = db.getPatienten();
        String selectedItems = "";

        ArrayAdapter<Patient> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);

        final ListView listViewPatienten = (ListView)findViewById(R.id.listViewPatienten);
        listViewPatienten.setAdapter(adapter);
        listViewPatienten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Log.i("lijst", patients.toString());
    }
}
