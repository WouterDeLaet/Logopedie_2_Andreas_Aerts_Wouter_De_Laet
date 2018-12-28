package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class writing_test extends AppCompatActivity implements View.OnClickListener {
    private static TextView countdownTimerText;
    private static EditText minutes;
    private static Button startTimer, resetTimer;
    private static CountDownTimer countDownTimer;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;
    private Button submitText;
    private String woordenLijstEfficiëntie[] =
            {
                    "Armstoel",
                    "leunstoel",
                    "sofa",
                    "zetel",
                    "bank",
                    "Beer",
                    "knuffel",
                    "pop",
                    "speelgoed",
                    "Boeken",
                    "Boekenplank",
                    "Splank",
                    "rek",
                    "Bokaal",
                    "visbokaal",
                    "viskom",
                    "De",
                    "het",
                    "een",
                    "Dochter",
                    "kind",
                    "kindje",
                    "meisje",
                    "Duwen",
                    "duwt",
                    "geduwd",
                    "te duwen",
                    "heeft geduwd",
                    "duwde",
                    "Grijpen",
                    "te grijpen",
                    "pakken",
                    "te pakken",
                    "vangen",
                    "te vangen",
                    "Grond",
                    "vloer",
                    "Grootvader",
                    "man",
                    "opa",
                    "papa",
                    "vader",
                    "Haar",
                    "Hoofd",
                    "In",
                    "Kat",
                    "poes",
                    "Met",
                    "Op",
                    "Poot",
                    "Proberen",
                    "probeert",
                    "probeerde",
                    "Slapen",
                    "slaapt",
                    "slapende",
                    "sliep",
                    "Spelen",
                    "speelde",
                    "te spelen",
                    "speelt",
                    "Staart",
                    "Vaas",
                    "Vallen",
                    "vallende",
                    "vielen",
                    "Van",
                    "Vis",
                    "Waarschuwen",
                    "te waarschuwen",
                    "waarschuwt",
                    "gewaarschuwd",
                    "waarschuwde",
                    "wakker maken",
                    "wakker te maken",
                    "maakt wakker",
                    "maakte wakker",
                    "wakker gemaakt",
                    "wekken",
                    "te wekken",
                    "wekt",
                    "gewekt",
                    "wekte",
                    "Zitten",
                    "zit",
                    "zat",
                    "Aan",
                    "Bijna",
                    "Boven",
                    "Dat",
                    "die",
                    "Daar",
                    "Dik",
                    "Dikke",
                    "Gedronken",
                    "Hard",
                    "Hebben",
                    "heeft",
                    "had",
                    "Hier",
                    "Hij",
                    "Ik",
                    "Kast",
                    "Klein",
                    "Kleine",
                    "Kunnen",
                    "kan",
                    "kon",
                    "Lange",
                    "Leeg",
                    "Lege",
                    "Liggen",
                    "ligt",
                    "lag",
                    "Lijken",
                    "lijkt",
                    "Moe",
                    "Moeten",
                    "moet",
                    "Mogen",
                    "Mouw",
                    "Naar",
                    "Naast",
                    "Nu",
                    "Onder",
                    "Oude",
                    "Pijn",
                    "Staan",
                    "staat",
                    "stond",
                    "Toen",
                    "Trekken",
                    "trekt",
                    "getrokken",
                    "trok",
                    "Vest",
                    "Wakker",
                    "Water",
                    "Wijzen",
                    "wijst",
                    "wees",
                    "Willen",
                    "wil",
                    "wou",
                    "Worden",
                    "wordt",
                    "werd",
                    "Ze",
                    "Zij",
                    "Zie",
                    "Zijn",
                    "is",
                    "was",
                    "waren",
                    "Zullen",
                    "zal"


            };
    private String woordenlijstSubstitutiegedrag[] =
            {
                "Cd’s",
                "Cd-rek",
                "Deur",
                "Deuren",
                "Fles",
                "Glas",
                "Gordijn",
                "Handen",
                "Living",
                "Luidspreker",
                "Luidsprekers",
                "Plant",
                "Raam",
                "Radio",
                "Rok",
                "Salon",
                "Salontafel",
                "Stereo",
                "Venster",
                "Voeten",
                "Wijn",
                "Wijnfles",
                "Woonkamer",
                "Tegen",
                "Voor",
                "Vouwen"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        startTimer = (Button) findViewById(R.id.startTimer);
        resetTimer = (Button) findViewById(R.id.resetTimer);
        mImageView = (ImageView) findViewById(R.id.situatieplaat);
        mScaleGestureDetector = new ScaleGestureDetector(this, new writing_test.ScaleListener());
        submitText = (Button) findViewById(R.id.btnSubmit);

        setListeners();

        submitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateWordEfficiency();
            }
        });
//        DrawerUtil.getDrawer(this,toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
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
    //Set Listeners over button
    private void setListeners() {
        startTimer.setOnClickListener(this);
        resetTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTimer:
                //If CountDownTimer is null then start timer
                if (countDownTimer == null) {
                    String getMinutes = minutes.getText().toString();//Get minutes from edittexf
                    //Check validation over edittext
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds
                        startTimer(noOfMinutes);//start countdown
                        startTimer.setText(getString(R.string.stop_timer));//Change Text
                    } else
                        Toast.makeText(writing_test.this, "Gelieve het aantal minuten in te geven", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
                } else {
                    //Else stop timer and change text
                    stopCountdown();
                    startTimer.setText(getString(R.string.start_timer));
                }
                break;
            case R.id.resetTimer:
                stopCountdown();//stop count down
                startTimer.setText(getString(R.string.start_timer));//Change text to Start Timer
                countdownTimerText.setText(getString(R.string.timer));//Change Timer text
                break;
        }
    }

    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {
                countdownTimerText.setText("TIJD IS OP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Geef uw beschrijving op.");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);
            return true;
        }
    }

    public void calculateWordEfficiency()
    {
        int numberOfWordsUsed = 0;
        int numberOfWordsUsedSubstitutie = 0;
        float efficiëntie = 0;
        float numberOfUnUsedWords = 0;
        float substitutiegedrag = 0;

        EditText descriptionImage = (EditText) findViewById(R.id.descriptionImage);
        TextView result = (TextView) findViewById(R.id.test);

        String description = descriptionImage.getText().toString();
        description = description.toLowerCase();

        String descriptionSplitted [] = description.split(" ");

         for(int i = 0; i < woordenLijstEfficiëntie.length; i++)
         {
             if(description.contains(woordenLijstEfficiëntie[i].toLowerCase()))
             {
                 numberOfWordsUsed += howManyTimesIsTheWordUsed(descriptionSplitted, woordenLijstEfficiëntie, i);
             }
         }

        for(int i = 0; i < woordenlijstSubstitutiegedrag.length; i++)
        {
            if(description.contains(woordenlijstSubstitutiegedrag[i].toLowerCase()))
            {
                numberOfWordsUsedSubstitutie += howManyTimesIsTheWordUsed(descriptionSplitted, woordenlijstSubstitutiegedrag, i);
            } else {
                numberOfUnUsedWords++;
            }
        }

        efficiëntie = (numberOfWordsUsed / Float.parseFloat(woordenLijstEfficiëntie.length + "")) * 100;

        substitutiegedrag = (numberOfUnUsedWords / Float.parseFloat(woordenlijstSubstitutiegedrag.length + "")) * 100;

        result.setText(getString(R.string.efficiëntie) + " " + efficiëntie + "%" + "\n" + getString(R.string.substitutiegedrag) + " " + substitutiegedrag + "%");
    }

    public int howManyTimesIsTheWordUsed(String descriptionSplitted [], String listOfWords [], int word)
    {
        int numberOfWordsUsed = 0;
        for(int j = 0; j < descriptionSplitted.length; j ++)
        {
            if(descriptionSplitted[j].contains(listOfWords[word].toLowerCase()))
            {
                numberOfWordsUsed ++;
            }
        }

        return numberOfWordsUsed;
    }
}
