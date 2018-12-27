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
    private EditText mVoiceInputTv;
    private Button mSpeakBtn;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;

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
        mVoiceInputTv = (EditText) findViewById(R.id.voiceInput);
        mSpeakBtn = (Button) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
        mImageView = (ImageView) findViewById(R.id.situatieplaat);
        mScaleGestureDetector = new ScaleGestureDetector(this, new writing_test.ScaleListener());

        setListeners();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                }
                break;
            }

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
}
