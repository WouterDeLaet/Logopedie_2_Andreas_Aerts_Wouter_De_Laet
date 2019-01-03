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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class writing_test extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHelper db;
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
                    "plank",
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
    String woordenlijstCausaalVerband [] = {
            "Vaas valt. grootvader pijn",
            "Vaas valt. man pijn",
            "Vaas valt. opa pijn",
            "Vaas valt. papa pijn",
            "Vaas valt. vader pijn",
            "Boeken vallen. grootvader pijn",
            "Boeken vallen. man pijn",
            "Boeken vallen. opa pijn",
            "Boeken vallen. papa pijn",
            "Boeken vallen. vader pijn",
            "Kat duwt. boeken vallen",
            "Kat staart. boeken vallen",
            "poes duwt. boeken vallen",
            "poes staart. boeken vallen",
            "Kat duwt. vaas valt",
            "Kat staart. vaas valt",
            "poes duwt. vaas valt",
            "poes staart. vaas valt",
            "Kat probeert vis te vangen. vaas valt",
            "poes probeert vis te vangen. vaas valt",
            "Kat probeert vis te vangen. boeken vallen",
            "poes probeert vis te vangen. boeken vallen",
            "Moe. Grootvader slaapt",
            "Moe. man slaapt",
            "Moe. opa slaapt",
            "Moe. papa slaapt",
            "Moe. vader slaapt",
            "gedronken. Grootvader slaapt",
            "gedronken. man slaapt",
            "gedronken. opa slaapt",
            "gedronken. papa slaapt",
            "gedronken. vader slaapt",
            "Boeken vallen. dochter waarschuwen grootvader",
            "Boeken vallen. kind waarschuwen grootvader",
            "Boeken vallen. kindje waarschuwen grootvader",
            "Boeken vallen. meisje waarschuwen grootvader",
            "Boeken vallen. dochter te waarschuwen grootvader",
            "Boeken vallen. kind te waarschuwen grootvader",
            "Boeken vallen. kindje te waarschuwen grootvader",
            "Boeken vallen. meisje te waarschuwen grootvader",
            "Boeken vallen. dochter waarschuwt grootvader",
            "Boeken vallen. kind waarschuwt grootvader",
            "Boeken vallen. kindje waarschuwt grootvader",
            "Boeken vallen. meisje waarschuwt grootvader",
            "Boeken vallen. dochter gewaarschuwd grootvader",
            "Boeken vallen. kind gewaarschuwd grootvader",
            "Boeken vallen. kindje gewaarschuwd grootvader",
            "Boeken vallen. meisje gewaarschuwd grootvader",
            "Boeken vallen. dochter waarschuwde grootvader",
            "Boeken vallen. kind waarschuwde grootvader",
            "Boeken vallen. kindje waarschuwde grootvader",
            "Boeken vallen. meisje waarschuwde grootvader",
            "Boeken vallen. dochter wakker maken grootvader",
            "Boeken vallen. kind wakker maken grootvader",
            "Boeken vallen. kindje wakker maken grootvader",
            "Boeken vallen. meisje wakker maken grootvader",
            "Boeken vallen. dochter wakker te maken grootvader",
            "Boeken vallen. kind wakker te maken grootvader",
            "Boeken vallen. kindje wakker te maken grootvader",
            "Boeken vallen. meisje wakker te maken grootvader",
            "Boeken vallen. dochter maakt wakker grootvader",
            "Boeken vallen. kind maakt wakker grootvader",
            "Boeken vallen. kindje maakt wakker grootvader",
            "Boeken vallen. meisje maakt wakker grootvader",
            "Boeken vallen. dochter maakte wakker grootvader",
            "Boeken vallen. kind maakt wakker grootvader",
            "Boeken vallen. kindje maakt wakker grootvader",
            "Boeken vallen. meisje maakt wakker grootvader",
            "Boeken vallen. dochter maakte wakker grootvader",
            "Boeken vallen. kind maakte wakker grootvader",
            "Boeken vallen. kindje maakte wakker grootvader",
            "Boeken vallen. meisje maakte wakker grootvader",
            "Boeken vallen. dochter wakker gemaakt grootvader",
            "Boeken vallen. kind wakker gemaakt grootvader",
            "Boeken vallen. kindje wakker gemaakt grootvader",
            "Boeken vallen. meisje wakker gemaakt grootvader",
            "Boeken vallen. dochter wekken grootvader",
            "Boeken vallen. kind wekken grootvader",
            "Boeken vallen. kindje wekken grootvader",
            "Boeken vallen. meisje wekken grootvader",
            "Boeken vallen. dochter te wekken grootvader",
            "Boeken vallen. kind te wekken grootvader",
            "Boeken vallen. kindje te wekken grootvader",
            "Boeken vallen. meisje te wekken grootvader",
            "Boeken vallen. dochter wekt grootvader",
            "Boeken vallen. kind wekt grootvader",
            "Boeken vallen. kindje wekt grootvader",
            "Boeken vallen. meisje wekt grootvader",
            "Boeken vallen. dochter gewekt grootvader",
            "Boeken vallen. kind gewekt grootvader",
            "Boeken vallen. kindje gewekt grootvader",
            "Boeken vallen. meisje gewekt grootvader",
            "Boeken vallen. dochter wekte grootvader",
            "Boeken vallen. kind wekte grootvader",
            "Boeken vallen. kindje wekte grootvader",
            "Boeken vallen. meisje wekte grootvader",
            "Boeken vallen. dochter waarschuwen man",//dasssssssssssssssssssssssssssssssssssssssssssssssssssss
            "Boeken vallen. kind waarschuwen man",
            "Boeken vallen. kindje waarschuwen man",
            "Boeken vallen. meisje waarschuwen man",
            "Boeken vallen. dochter te waarschuwen man",
            "Boeken vallen. kind te waarschuwen man",
            "Boeken vallen. kindje te waarschuwen man",
            "Boeken vallen. meisje te waarschuwen man",
            "Boeken vallen. dochter waarschuwt man",
            "Boeken vallen. kind waarschuwt man",
            "Boeken vallen. kindje waarschuwt man",
            "Boeken vallen. meisje waarschuwt man",
            "Boeken vallen. dochter gewaarschuwd man",
            "Boeken vallen. kind gewaarschuwd man",
            "Boeken vallen. kindje gewaarschuwd man",
            "Boeken vallen. meisje gewaarschuwd man",
            "Boeken vallen. dochter waarschuwde man",
            "Boeken vallen. kind waarschuwde man",
            "Boeken vallen. kindje waarschuwde man",
            "Boeken vallen. meisje waarschuwde man",
            "Boeken vallen. dochter wakker maken man",
            "Boeken vallen. kind wakker maken man",
            "Boeken vallen. kindje wakker maken man",
            "Boeken vallen. meisje wakker maken man",
            "Boeken vallen. dochter wakker te maken man",
            "Boeken vallen. kind wakker te maken man",
            "Boeken vallen. kindje wakker te maken man",
            "Boeken vallen. meisje wakker te maken man",
            "Boeken vallen. dochter maakt wakker man",
            "Boeken vallen. kind maakt wakker man",
            "Boeken vallen. kindje maakt wakker man",
            "Boeken vallen. meisje maakt wakker man",
            "Boeken vallen. dochter maakte wakker man",
            "Boeken vallen. kind maakt wakker man",
            "Boeken vallen. kindje maakt wakker man",
            "Boeken vallen. meisje maakt wakker man",
            "Boeken vallen. dochter maakte wakker man",
            "Boeken vallen. kind maakte wakker man",
            "Boeken vallen. kindje maakte wakker man",
            "Boeken vallen. meisje maakte wakker man",
            "Boeken vallen. dochter wakker gemaakt man",
            "Boeken vallen. kind wakker gemaakt man",
            "Boeken vallen. kindje wakker gemaakt man",
            "Boeken vallen. meisje wakker gemaakt man",
            "Boeken vallen. dochter wekken man",
            "Boeken vallen. kind wekken man",
            "Boeken vallen. kindje wekken man",
            "Boeken vallen. meisje wekken man",
            "Boeken vallen. dochter te wekken man",
            "Boeken vallen. kind te wekken man",
            "Boeken vallen. kindje te wekken man",
            "Boeken vallen. meisje te wekken man",
            "Boeken vallen. dochter wekt man",
            "Boeken vallen. kind wekt man",
            "Boeken vallen. kindje wekt man",
            "Boeken vallen. meisje wekt man",
            "Boeken vallen. dochter gewekt man",
            "Boeken vallen. kind gewekt man",
            "Boeken vallen. kindje gewekt man",
            "Boeken vallen. meisje gewekt man",
            "Boeken vallen. dochter wekte man",
            "Boeken vallen. kind wekte man",
            "Boeken vallen. kindje wekte man",
            "Boeken vallen. meisje wekte man",
            "Boeken vallen. dochter waarschuwen opa",//deqwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
            "Boeken vallen. kind waarschuwen opa",
            "Boeken vallen. kindje waarschuwen opa",
            "Boeken vallen. meisje waarschuwen opa",
            "Boeken vallen. dochter te waarschuwen opa",
            "Boeken vallen. kind te waarschuwen opa",
            "Boeken vallen. kindje te waarschuwen opa",
            "Boeken vallen. meisje te waarschuwen opa",
            "Boeken vallen. dochter waarschuwt opa",
            "Boeken vallen. kind waarschuwt opa",
            "Boeken vallen. kindje waarschuwt opa",
            "Boeken vallen. meisje waarschuwt opa",
            "Boeken vallen. dochter gewaarschuwd opa",
            "Boeken vallen. kind gewaarschuwd opa",
            "Boeken vallen. kindje gewaarschuwd opa",
            "Boeken vallen. meisje gewaarschuwd opa",
            "Boeken vallen. dochter waarschuwde opa",
            "Boeken vallen. kind waarschuwde opa",
            "Boeken vallen. kindje waarschuwde opa",
            "Boeken vallen. meisje waarschuwde opa",
            "Boeken vallen. dochter wakker maken opa",
            "Boeken vallen. kind wakker maken opa",
            "Boeken vallen. kindje wakker maken opa",
            "Boeken vallen. meisje wakker maken opa",
            "Boeken vallen. dochter wakker te maken opa",
            "Boeken vallen. kind wakker te maken opa",
            "Boeken vallen. kindje wakker te maken opa",
            "Boeken vallen. meisje wakker te maken opa",
            "Boeken vallen. dochter maakt wakker opa",
            "Boeken vallen. kind maakt wakker opa",
            "Boeken vallen. kindje maakt wakker opa",
            "Boeken vallen. meisje maakt wakker opa",
            "Boeken vallen. dochter maakte wakker opa",
            "Boeken vallen. kind maakt wakker opa",
            "Boeken vallen. kindje maakt wakker opa",
            "Boeken vallen. meisje maakt wakker opa",
            "Boeken vallen. dochter maakte wakker opa",
            "Boeken vallen. kind maakte wakker opa",
            "Boeken vallen. kindje maakte wakker opa",
            "Boeken vallen. meisje maakte wakker opa",
            "Boeken vallen. dochter wakker gemaakt opa",
            "Boeken vallen. kind wakker gemaakt opa",
            "Boeken vallen. kindje wakker gemaakt opa",
            "Boeken vallen. meisje wakker gemaakt opa",
            "Boeken vallen. dochter wekken opa",
            "Boeken vallen. kind wekken opa",
            "Boeken vallen. kindje wekken opa",
            "Boeken vallen. meisje wekken opa",
            "Boeken vallen. dochter te wekken opa",
            "Boeken vallen. kind te wekken opa",
            "Boeken vallen. kindje te wekken opa",
            "Boeken vallen. meisje te wekken opa",
            "Boeken vallen. dochter wekt opa",
            "Boeken vallen. kind wekt opa",
            "Boeken vallen. kindje wekt opa",
            "Boeken vallen. meisje wekt opa",
            "Boeken vallen. dochter gewekt opa",
            "Boeken vallen. kind gewekt opa",
            "Boeken vallen. kindje gewekt opa",
            "Boeken vallen. meisje gewekt opa",
            "Boeken vallen. dochter wekte opa",
            "Boeken vallen. kind wekte opa",
            "Boeken vallen. kindje wekte opa",
            "Boeken vallen. meisje wekte opa",
            "Boeken vallen. dochter waarschuwen papa",//ooooooooooooooooooooooooooooooooooooooooooooooo
            "Boeken vallen. kind waarschuwen papa",
            "Boeken vallen. kindje waarschuwen papa",
            "Boeken vallen. meisje waarschuwen papa",
            "Boeken vallen. dochter te waarschuwen papa",
            "Boeken vallen. kind te waarschuwen papa",
            "Boeken vallen. kindje te waarschuwen papa",
            "Boeken vallen. meisje te waarschuwen papa",
            "Boeken vallen. dochter waarschuwt papa",
            "Boeken vallen. kind waarschuwt papa",
            "Boeken vallen. kindje waarschuwt papa",
            "Boeken vallen. meisje waarschuwt papa",
            "Boeken vallen. dochter gewaarschuwd papa",
            "Boeken vallen. kind gewaarschuwd papa",
            "Boeken vallen. kindje gewaarschuwd papa",
            "Boeken vallen. meisje gewaarschuwd papa",
            "Boeken vallen. dochter waarschuwde papa",
            "Boeken vallen. kind waarschuwde papa",
            "Boeken vallen. kindje waarschuwde papa",
            "Boeken vallen. meisje waarschuwde papa",
            "Boeken vallen. dochter wakker maken papa",
            "Boeken vallen. kind wakker maken papa",
            "Boeken vallen. kindje wakker maken papa",
            "Boeken vallen. meisje wakker maken papa",
            "Boeken vallen. dochter wakker te maken papa",
            "Boeken vallen. kind wakker te maken papa",
            "Boeken vallen. kindje wakker te maken papa",
            "Boeken vallen. meisje wakker te maken papa",
            "Boeken vallen. dochter maakt wakker papa",
            "Boeken vallen. kind maakt wakker papa",
            "Boeken vallen. kindje maakt wakker papa",
            "Boeken vallen. meisje maakt wakker papa",
            "Boeken vallen. dochter maakte wakker papa",
            "Boeken vallen. kind maakt wakker papa",
            "Boeken vallen. kindje maakt wakker papa",
            "Boeken vallen. meisje maakt wakker papa",
            "Boeken vallen. dochter maakte wakker papa",
            "Boeken vallen. kind maakte wakker papa",
            "Boeken vallen. kindje maakte wakker papa",
            "Boeken vallen. meisje maakte wakker papa",
            "Boeken vallen. dochter wakker gemaakt papa",
            "Boeken vallen. kind wakker gemaakt papa",
            "Boeken vallen. kindje wakker gemaakt papa",
            "Boeken vallen. meisje wakker gemaakt papa",
            "Boeken vallen. dochter wekken papa",
            "Boeken vallen. kind wekken papa",
            "Boeken vallen. kindje wekken papa",
            "Boeken vallen. meisje wekken papa",
            "Boeken vallen. dochter te wekken papa",
            "Boeken vallen. kind te wekken papa",
            "Boeken vallen. kindje te wekken papa",
            "Boeken vallen. meisje te wekken papa",
            "Boeken vallen. dochter wekt papa",
            "Boeken vallen. kind wekt papa",
            "Boeken vallen. kindje wekt papa",
            "Boeken vallen. meisje wekt papa",
            "Boeken vallen. dochter gewekt papa",
            "Boeken vallen. kind gewekt papa",
            "Boeken vallen. kindje gewekt papa",
            "Boeken vallen. meisje gewekt papa",
            "Boeken vallen. dochter wekte papa",
            "Boeken vallen. kind wekte papa",
            "Boeken vallen. kindje wekte papa",
            "Boeken vallen. meisje wekte papa",
            "Boeken vallen. dochter waarschuwen vader",//llllllllllllllllllllllllllllllllllllllllllllll
            "Boeken vallen. kind waarschuwen vader",
            "Boeken vallen. kindje waarschuwen vader",
            "Boeken vallen. meisje waarschuwen vader",
            "Boeken vallen. dochter te waarschuwen vader",
            "Boeken vallen. kind te waarschuwen vader",
            "Boeken vallen. kindje te waarschuwen vader",
            "Boeken vallen. meisje te waarschuwen vader",
            "Boeken vallen. dochter waarschuwt vader",
            "Boeken vallen. kind waarschuwt vader",
            "Boeken vallen. kindje waarschuwt vader",
            "Boeken vallen. meisje waarschuwt vader",
            "Boeken vallen. dochter gewaarschuwd vader",
            "Boeken vallen. kind gewaarschuwd vader",
            "Boeken vallen. kindje gewaarschuwd vader",
            "Boeken vallen. meisje gewaarschuwd vader",
            "Boeken vallen. dochter waarschuwde vader",
            "Boeken vallen. kind waarschuwde vader",
            "Boeken vallen. kindje waarschuwde vader",
            "Boeken vallen. meisje waarschuwde vader",
            "Boeken vallen. dochter wakker maken vader",
            "Boeken vallen. kind wakker maken vader",
            "Boeken vallen. kindje wakker maken vader",
            "Boeken vallen. meisje wakker maken vader",
            "Boeken vallen. dochter wakker te maken vader",
            "Boeken vallen. kind wakker te maken vader",
            "Boeken vallen. kindje wakker te maken vader",
            "Boeken vallen. meisje wakker te maken vader",
            "Boeken vallen. dochter maakt wakker vader",
            "Boeken vallen. kind maakt wakker vader",
            "Boeken vallen. kindje maakt wakker vader",
            "Boeken vallen. meisje maakt wakker vader",
            "Boeken vallen. dochter maakte wakker vader",
            "Boeken vallen. kind maakt wakker vader",
            "Boeken vallen. kindje maakt wakker vader",
            "Boeken vallen. meisje maakt wakker vader",
            "Boeken vallen. dochter maakte wakker vader",
            "Boeken vallen. kind maakte wakker vader",
            "Boeken vallen. kindje maakte wakker vader",
            "Boeken vallen. meisje maakte wakker vader",
            "Boeken vallen. dochter wakker gemaakt vader",
            "Boeken vallen. kind wakker gemaakt vader",
            "Boeken vallen. kindje wakker gemaakt vader",
            "Boeken vallen. meisje wakker gemaakt vader",
            "Boeken vallen. dochter wekken vader",
            "Boeken vallen. kind wekken vader",
            "Boeken vallen. kindje wekken vader",
            "Boeken vallen. meisje wekken vader",
            "Boeken vallen. dochter te wekken vader",
            "Boeken vallen. kind te wekken vader",
            "Boeken vallen. kindje te wekken vader",
            "Boeken vallen. meisje te wekken vader",
            "Boeken vallen. dochter wekt vader",
            "Boeken vallen. kind wekt vader",
            "Boeken vallen. kindje wekt vader",
            "Boeken vallen. meisje wekt vader",
            "Boeken vallen. dochter gewekt vader",
            "Boeken vallen. kind gewekt vader",
            "Boeken vallen. kindje gewekt vader",
            "Boeken vallen. meisje gewekt vader",
            "Boeken vallen. dochter wekte vader",
            "Boeken vallen. kind wekte vader",
            "Boeken vallen. kindje wekte vader",
            "Boeken vallen. meisje wekte vader",//dedddddddddddddddddddddddddddddddddd
            "Vaas valt. dochter waarschuwen grootvader",
            "Vaas valt. kind waarschuwen grootvader",
            "Vaas valt. kindje waarschuwen grootvader",
            "Vaas valt. meisje waarschuwen grootvader",
            "Vaas valt. dochter te waarschuwen grootvader",
            "Vaas valt. kind te waarschuwen grootvader",
            "Vaas valt. kindje te waarschuwen grootvader",
            "Vaas valt. meisje te waarschuwen grootvader",
            "Vaas valt. dochter waarschuwt grootvader",
            "Vaas valt. kind waarschuwt grootvader",
            "Vaas valt. kindje waarschuwt grootvader",
            "Vaas valt. meisje waarschuwt grootvader",
            "Vaas valt. dochter gewaarschuwd grootvader",
            "Vaas valt. kind gewaarschuwd grootvader",
            "Vaas valt. kindje gewaarschuwd grootvader",
            "Vaas valt. meisje gewaarschuwd grootvader",
            "Vaas valt. dochter waarschuwde grootvader",
            "Vaas valt. kind waarschuwde grootvader",
            "Vaas valt. kindje waarschuwde grootvader",
            "Vaas valt. meisje waarschuwde grootvader",
            "Vaas valt. dochter wakker maken grootvader",
            "Vaas valt. kind wakker maken grootvader",
            "Vaas valt. kindje wakker maken grootvader",
            "Vaas valt. meisje wakker maken grootvader",
            "Vaas valt. dochter wakker te maken grootvader",
            "Vaas valt. kind wakker te maken grootvader",
            "Vaas valt. kindje wakker te maken grootvader",
            "Vaas valt. meisje wakker te maken grootvader",
            "Vaas valt. dochter maakt wakker grootvader",
            "Vaas valt. kind maakt wakker grootvader",
            "Vaas valt. kindje maakt wakker grootvader",
            "Vaas valt. meisje maakt wakker grootvader",
            "Vaas valt. dochter maakte wakker grootvader",
            "Vaas valt. kind maakt wakker grootvader",
            "Vaas valt. kindje maakt wakker grootvader",
            "Vaas valt. meisje maakt wakker grootvader",
            "Vaas valt. dochter maakte wakker grootvader",
            "Vaas valt. kind maakte wakker grootvader",
            "Vaas valt. kindje maakte wakker grootvader",
            "Vaas valt. meisje maakte wakker grootvader",
            "Vaas valt. dochter wakker gemaakt grootvader",
            "Vaas valt. kind wakker gemaakt grootvader",
            "Vaas valt. kindje wakker gemaakt grootvader",
            "Vaas valt. meisje wakker gemaakt grootvader",
            "Vaas valt. dochter wekken grootvader",
            "Vaas valt. kind wekken grootvader",
            "Vaas valt. kindje wekken grootvader",
            "Vaas valt. meisje wekken grootvader",
            "Vaas valt. dochter te wekken grootvader",
            "Vaas valt. kind te wekken grootvader",
            "Vaas valt. kindje te wekken grootvader",
            "Vaas valt. meisje te wekken grootvader",
            "Vaas valt. dochter wekt grootvader",
            "Vaas valt. kind wekt grootvader",
            "Vaas valt. kindje wekt grootvader",
            "Vaas valt. meisje wekt grootvader",
            "Vaas valt. dochter gewekt grootvader",
            "Vaas valt. kind gewekt grootvader",
            "Vaas valt. kindje gewekt grootvader",
            "Vaas valt. meisje gewekt grootvader",
            "Vaas valt. dochter wekte grootvader",
            "Vaas valt. kind wekte grootvader",
            "Vaas valt. kindje wekte grootvader",
            "Vaas valt. meisje wekte grootvader",
            "Vaas valt. dochter waarschuwen man",//dasssssssssssssssssssssssssssssssssssssssssssssssssssss
            "Vaas valt. kind waarschuwen man",
            "Vaas valt. kindje waarschuwen man",
            "Vaas valt. meisje waarschuwen man",
            "Vaas valt. dochter te waarschuwen man",
            "Vaas valt. kind te waarschuwen man",
            "Vaas valt. kindje te waarschuwen man",
            "Vaas valt. meisje te waarschuwen man",
            "Vaas valt. dochter waarschuwt man",
            "Vaas valt. kind waarschuwt man",
            "Vaas valt. kindje waarschuwt man",
            "Vaas valt. meisje waarschuwt man",
            "Vaas valt. dochter gewaarschuwd man",
            "Vaas valt. kind gewaarschuwd man",
            "Vaas valt. kindje gewaarschuwd man",
            "Vaas valt. meisje gewaarschuwd man",
            "Vaas valt. dochter waarschuwde man",
            "Vaas valt. kind waarschuwde man",
            "Vaas valt. kindje waarschuwde man",
            "Vaas valt. meisje waarschuwde man",
            "Vaas valt. dochter wakker maken man",
            "Vaas valt. kind wakker maken man",
            "Vaas valt. kindje wakker maken man",
            "Vaas valt. meisje wakker maken man",
            "Vaas valt. dochter wakker te maken man",
            "Vaas valt. kind wakker te maken man",
            "Vaas valt. kindje wakker te maken man",
            "Vaas valt. meisje wakker te maken man",
            "Vaas valt. dochter maakt wakker man",
            "Vaas valt. kind maakt wakker man",
            "Vaas valt. kindje maakt wakker man",
            "Vaas valt. meisje maakt wakker man",
            "Vaas valt. dochter maakte wakker man",
            "Vaas valt. kind maakt wakker man",
            "Vaas valt. kindje maakt wakker man",
            "Vaas valt. meisje maakt wakker man",
            "Vaas valt. dochter maakte wakker man",
            "Vaas valt. kind maakte wakker man",
            "Vaas valt. kindje maakte wakker man",
            "Vaas valt. meisje maakte wakker man",
            "Vaas valt. dochter wakker gemaakt man",
            "Vaas valt. kind wakker gemaakt man",
            "Vaas valt. kindje wakker gemaakt man",
            "Vaas valt. meisje wakker gemaakt man",
            "Vaas valt. dochter wekken man",
            "Vaas valt. kind wekken man",
            "Vaas valt. kindje wekken man",
            "Vaas valt. meisje wekken man",
            "Vaas valt. dochter te wekken man",
            "Vaas valt. kind te wekken man",
            "Vaas valt. kindje te wekken man",
            "Vaas valt. meisje te wekken man",
            "Vaas valt. dochter wekt man",
            "Vaas valt. kind wekt man",
            "Vaas valt. kindje wekt man",
            "Vaas valt. meisje wekt man",
            "Vaas valt. dochter gewekt man",
            "Vaas valt. kind gewekt man",
            "Vaas valt. kindje gewekt man",
            "Vaas valt. meisje gewekt man",
            "Vaas valt. dochter wekte man",
            "Vaas valt. kind wekte man",
            "Vaas valt. kindje wekte man",
            "Vaas valt. meisje wekte man",
            "Vaas valt. dochter waarschuwen opa",//deqwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
            "Vaas valt. kind waarschuwen opa",
            "Vaas valt. kindje waarschuwen opa",
            "Vaas valt. meisje waarschuwen opa",
            "Vaas valt. dochter te waarschuwen opa",
            "Vaas valt. kind te waarschuwen opa",
            "Vaas valt. kindje te waarschuwen opa",
            "Vaas valt. meisje te waarschuwen opa",
            "Vaas valt. dochter waarschuwt opa",
            "Vaas valt. kind waarschuwt opa",
            "Vaas valt. kindje waarschuwt opa",
            "Vaas valt. meisje waarschuwt opa",
            "Vaas valt. dochter gewaarschuwd opa",
            "Vaas valt. kind gewaarschuwd opa",
            "Vaas valt. kindje gewaarschuwd opa",
            "Vaas valt. meisje gewaarschuwd opa",
            "Vaas valt. dochter waarschuwde opa",
            "Vaas valt. kind waarschuwde opa",
            "Vaas valt. kindje waarschuwde opa",
            "Vaas valt. meisje waarschuwde opa",
            "Vaas valt. dochter wakker maken opa",
            "Vaas valt. kind wakker maken opa",
            "Vaas valt. kindje wakker maken opa",
            "Vaas valt. meisje wakker maken opa",
            "Vaas valt. dochter wakker te maken opa",
            "Vaas valt. kind wakker te maken opa",
            "Vaas valt. kindje wakker te maken opa",
            "Vaas valt. meisje wakker te maken opa",
            "Vaas valt. dochter maakt wakker opa",
            "Vaas valt. kind maakt wakker opa",
            "Vaas valt. kindje maakt wakker opa",
            "Vaas valt. meisje maakt wakker opa",
            "Vaas valt. dochter maakte wakker opa",
            "Vaas valt. kind maakt wakker opa",
            "Vaas valt. kindje maakt wakker opa",
            "Vaas valt. meisje maakt wakker opa",
            "Vaas valt. dochter maakte wakker opa",
            "Vaas valt. kind maakte wakker opa",
            "Vaas valt. kindje maakte wakker opa",
            "Vaas valt. meisje maakte wakker opa",
            "Vaas valt. dochter wakker gemaakt opa",
            "Vaas valt. kind wakker gemaakt opa",
            "Vaas valt. kindje wakker gemaakt opa",
            "Vaas valt. meisje wakker gemaakt opa",
            "Vaas valt. dochter wekken opa",
            "Vaas valt. kind wekken opa",
            "Vaas valt. kindje wekken opa",
            "Vaas valt. meisje wekken opa",
            "Vaas valt. dochter te wekken opa",
            "Vaas valt. kind te wekken opa",
            "Vaas valt. kindje te wekken opa",
            "Vaas valt. meisje te wekken opa",
            "Vaas valt. dochter wekt opa",
            "Vaas valt. kind wekt opa",
            "Vaas valt. kindje wekt opa",
            "Vaas valt. meisje wekt opa",
            "Vaas valt. dochter gewekt opa",
            "Vaas valt. kind gewekt opa",
            "Vaas valt. kindje gewekt opa",
            "Vaas valt. meisje gewekt opa",
            "Vaas valt. dochter wekte opa",
            "Vaas valt. kind wekte opa",
            "Vaas valt. kindje wekte opa",
            "Vaas valt. meisje wekte opa",
            "Vaas valt. dochter waarschuwen papa",//ooooooooooooooooooooooooooooooooooooooooooooooo
            "Vaas valt. kind waarschuwen papa",
            "Vaas valt. kindje waarschuwen papa",
            "Vaas valt. meisje waarschuwen papa",
            "Vaas valt. dochter te waarschuwen papa",
            "Vaas valt. kind te waarschuwen papa",
            "Vaas valt. kindje te waarschuwen papa",
            "Vaas valt. meisje te waarschuwen papa",
            "Vaas valt. dochter waarschuwt papa",
            "Vaas valt. kind waarschuwt papa",
            "Vaas valt. kindje waarschuwt papa",
            "Vaas valt. meisje waarschuwt papa",
            "Vaas valt. dochter gewaarschuwd papa",
            "Vaas valt. kind gewaarschuwd papa",
            "Vaas valt. kindje gewaarschuwd papa",
            "Vaas valt. meisje gewaarschuwd papa",
            "Vaas valt. dochter waarschuwde papa",
            "Vaas valt. kind waarschuwde papa",
            "Vaas valt. kindje waarschuwde papa",
            "Vaas valt. meisje waarschuwde papa",
            "Vaas valt. dochter wakker maken papa",
            "Vaas valt. kind wakker maken papa",
            "Vaas valt. kindje wakker maken papa",
            "Vaas valt. meisje wakker maken papa",
            "Vaas valt. dochter wakker te maken papa",
            "Vaas valt. kind wakker te maken papa",
            "Vaas valt. kindje wakker te maken papa",
            "Vaas valt. meisje wakker te maken papa",
            "Vaas valt. dochter maakt wakker papa",
            "Vaas valt. kind maakt wakker papa",
            "Vaas valt. kindje maakt wakker papa",
            "Vaas valt. meisje maakt wakker papa",
            "Vaas valt. dochter maakte wakker papa",
            "Vaas valt. kind maakt wakker papa",
            "Vaas valt. kindje maakt wakker papa",
            "Vaas valt. meisje maakt wakker papa",
            "Vaas valt. dochter maakte wakker papa",
            "Vaas valt. kind maakte wakker papa",
            "Vaas valt. kindje maakte wakker papa",
            "Vaas valt. meisje maakte wakker papa",
            "Vaas valt. dochter wakker gemaakt papa",
            "Vaas valt. kind wakker gemaakt papa",
            "Vaas valt. kindje wakker gemaakt papa",
            "Vaas valt. meisje wakker gemaakt papa",
            "Vaas valt. dochter wekken papa",
            "Vaas valt. kind wekken papa",
            "Vaas valt. kindje wekken papa",
            "Vaas valt. meisje wekken papa",
            "Vaas valt. dochter te wekken papa",
            "Vaas valt. kind te wekken papa",
            "Vaas valt. kindje te wekken papa",
            "Vaas valt. meisje te wekken papa",
            "Vaas valt. dochter wekt papa",
            "Vaas valt. kind wekt papa",
            "Vaas valt. kindje wekt papa",
            "Vaas valt. meisje wekt papa",
            "Vaas valt. dochter gewekt papa",
            "Vaas valt. kind gewekt papa",
            "Vaas valt. kindje gewekt papa",
            "Vaas valt. meisje gewekt papa",
            "Vaas valt. dochter wekte papa",
            "Vaas valt. kind wekte papa",
            "Vaas valt. kindje wekte papa",
            "Vaas valt. meisje wekte papa",
            "Vaas valt. dochter waarschuwen vader",//llllllllllllllllllllllllllllllllllllllllllllll
            "Vaas valt. kind waarschuwen vader",
            "Vaas valt. kindje waarschuwen vader",
            "Vaas valt. meisje waarschuwen vader",
            "Vaas valt. dochter te waarschuwen vader",
            "Vaas valt. kind te waarschuwen vader",
            "Vaas valt. kindje te waarschuwen vader",
            "Vaas valt. meisje te waarschuwen vader",
            "Vaas valt. dochter waarschuwt vader",
            "Vaas valt. kind waarschuwt vader",
            "Vaas valt. kindje waarschuwt vader",
            "Vaas valt. meisje waarschuwt vader",
            "Vaas valt. dochter gewaarschuwd vader",
            "Vaas valt. kind gewaarschuwd vader",
            "Vaas valt. kindje gewaarschuwd vader",
            "Vaas valt. meisje gewaarschuwd vader",
            "Vaas valt. dochter waarschuwde vader",
            "Vaas valt. kind waarschuwde vader",
            "Vaas valt. kindje waarschuwde vader",
            "Vaas valt. meisje waarschuwde vader",
            "Vaas valt. dochter wakker maken vader",
            "Vaas valt. kind wakker maken vader",
            "Vaas valt. kindje wakker maken vader",
            "Vaas valt. meisje wakker maken vader",
            "Vaas valt. dochter wakker te maken vader",
            "Vaas valt. kind wakker te maken vader",
            "Vaas valt. kindje wakker te maken vader",
            "Vaas valt. meisje wakker te maken vader",
            "Vaas valt. dochter maakt wakker vader",
            "Vaas valt. kind maakt wakker vader",
            "Vaas valt. kindje maakt wakker vader",
            "Vaas valt. meisje maakt wakker vader",
            "Vaas valt. dochter maakte wakker vader",
            "Vaas valt. kind maakt wakker vader",
            "Vaas valt. kindje maakt wakker vader",
            "Vaas valt. meisje maakt wakker vader",
            "Vaas valt. dochter maakte wakker vader",
            "Vaas valt. kind maakte wakker vader",
            "Vaas valt. kindje maakte wakker vader",
            "Vaas valt. meisje maakte wakker vader",
            "Vaas valt. dochter wakker gemaakt vader",
            "Vaas valt. kind wakker gemaakt vader",
            "Vaas valt. kindje wakker gemaakt vader",
            "Vaas valt. meisje wakker gemaakt vader",
            "Vaas valt. dochter wekken vader",
            "Vaas valt. kind wekken vader",
            "Vaas valt. kindje wekken vader",
            "Vaas valt. meisje wekken vader",
            "Vaas valt. dochter te wekken vader",
            "Vaas valt. kind te wekken vader",
            "Vaas valt. kindje te wekken vader",
            "Vaas valt. meisje te wekken vader",
            "Vaas valt. dochter wekt vader",
            "Vaas valt. kind wekt vader",
            "Vaas valt. kindje wekt vader",
            "Vaas valt. meisje wekt vader",
            "Vaas valt. dochter gewekt vader",
            "Vaas valt. kind gewekt vader",
            "Vaas valt. kindje gewekt vader",
            "Vaas valt. meisje gewekt vader",
            "Vaas valt. dochter wekte vader",
            "Vaas valt. kind wekte vader",
            "Vaas valt. kindje wekte vader",
            "Vaas valt. meisje wekte vader",//wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
            "dochter waarschuwen grootvader. grootvader wakker",
            "kind waarschuwen grootvader. grootvader wakker",
            "kindje waarschuwen grootvader. grootvader wakker",
            "meisje waarschuwen grootvader. grootvader wakker",
            "dochter te waarschuwen grootvader. grootvader wakker",
            "kind te waarschuwen grootvader. grootvader wakker",
            "kindje te waarschuwen grootvader. grootvader wakker",
            "meisje te waarschuwen grootvader. grootvader wakker",
            "dochter waarschuwt grootvader. grootvader wakker",
            "kind waarschuwt grootvader. grootvader wakker",
            "kindje waarschuwt grootvader. grootvader wakker",
            "meisje waarschuwt grootvader. grootvader wakker",
            "dochter gewaarschuwd grootvader. grootvader wakker",
            "kind gewaarschuwd grootvader. grootvader wakker",
            "kindje gewaarschuwd grootvader. grootvader wakker",
            "meisje gewaarschuwd grootvader. grootvader wakker",
            "dochter waarschuwde grootvader. grootvader wakker",
            "kind waarschuwde grootvader. grootvader wakker",
            "kindje waarschuwde grootvader. grootvader wakker",
            "meisje waarschuwde grootvader. grootvader wakker",
            "dochter wakker maken grootvader. grootvader wakker",
            "kind wakker maken grootvader. grootvader wakker",
            "kindje wakker maken grootvader. grootvader wakker",
            "meisje wakker maken grootvader. grootvader wakker",
            "dochter wakker te maken grootvader. grootvader wakker",
            "kind wakker te maken grootvader. grootvader wakker",
            "kindje wakker te maken grootvader. grootvader wakker",
            "meisje wakker te maken grootvader. grootvader wakker",
            "dochter maakt wakker grootvader. grootvader wakker",
            "kind maakt wakker grootvader. grootvader wakker",
            "kindje maakt wakker grootvader. grootvader wakker",
            "meisje maakt wakker grootvader. grootvader wakker",
            "dochter maakte wakker grootvader. grootvader wakker",
            "kind maakt wakker grootvader. grootvader wakker",
            "kindje maakt wakker grootvader. grootvader wakker",
            "meisje maakt wakker grootvader. grootvader wakker",
            "dochter maakte wakker grootvader. grootvader wakker",
            "kind maakte wakker grootvader. grootvader wakker",
            "kindje maakte wakker grootvader. grootvader wakker",
            "meisje maakte wakker grootvader. grootvader wakker",
            "dochter wakker gemaakt grootvader. grootvader wakker",
            "kind wakker gemaakt grootvader. grootvader wakker",
            "kindje wakker gemaakt grootvader. grootvader wakker",
            "meisje wakker gemaakt grootvader. grootvader wakker",
            "dochter wekken grootvader. grootvader wakker",
            "kind wekken grootvader. grootvader wakker",
            "kindje wekken grootvader. grootvader wakker",
            "meisje wekken grootvader. grootvader wakker",
            "dochter te wekken grootvader. grootvader wakker",
            "kind te wekken grootvader. grootvader wakker",
            "kindje te wekken grootvader. grootvader wakker",
            "meisje te wekken grootvader. grootvader wakker",
            "dochter wekt grootvader. grootvader wakker",
            "kind wekt grootvader. grootvader wakker",
            "kindje wekt grootvader. grootvader wakker",
            "meisje wekt grootvader. grootvader wakker",
            "dochter gewekt grootvader. grootvader wakker",
            "kind gewekt grootvader. grootvader wakker",
            "kindje gewekt grootvader. grootvader wakker",
            "meisje gewekt grootvader. grootvader wakker",
            "dochter wekte grootvader. grootvader wakker",
            "kind wekte grootvader. grootvader wakker",
            "kindje wekte grootvader. grootvader wakker",
            "meisje wekte grootvader. grootvader wakker",
            "dochter waarschuwen man. man wakker",//dasssssssssssssssssssssssssssssssssssssssssssssssssssss
            "kind waarschuwen man. man wakker",
            "kindje waarschuwen man. man wakker",
            "meisje waarschuwen man. man wakker",
            "dochter te waarschuwen man. man wakker",
            "kind te waarschuwen man. man wakker",
            "kindje te waarschuwen man. man wakker",
            "meisje te waarschuwen man. man wakker",
            "dochter waarschuwt man. man wakker",
            "kind waarschuwt man. man wakker",
            "kindje waarschuwt man. man wakker",
            "meisje waarschuwt man. man wakker",
            "dochter gewaarschuwd man. man wakker",
            "kind gewaarschuwd man. man wakker",
            "kindje gewaarschuwd man. man wakker",
            "meisje gewaarschuwd man. man wakker",
            "dochter waarschuwde man. man wakker",
            "kind waarschuwde man. man wakker",
            "kindje waarschuwde man. man wakker",
            "meisje waarschuwde man. man wakker",
            "dochter wakker maken man. man wakker",
            "kind wakker maken man. man wakker",
            "kindje wakker maken man. man wakker",
            "meisje wakker maken man. man wakker",
            "dochter wakker te maken man. man wakker",
            "kind wakker te maken man. man wakker",
            "kindje wakker te maken man. man wakker",
            "meisje wakker te maken man. man wakker",
            "dochter maakt wakker man. man wakker",
            "kind maakt wakker man. man wakker",
            "kindje maakt wakker man. man wakker",
            "meisje maakt wakker man. man wakker",
            "dochter maakte wakker man. man wakker",
            "kind maakt wakker man. man wakker",
            "kindje maakt wakker man. man wakker",
            "meisje maakt wakker man. man wakker",
            "dochter maakte wakker man. man wakker",
            "kind maakte wakker man. man wakker",
            "kindje maakte wakker man. man wakker",
            "meisje maakte wakker man. man wakker",
            "dochter wakker gemaakt man. man wakker",
            "kind wakker gemaakt man. man wakker",
            "kindje wakker gemaakt man. man wakker",
            "meisje wakker gemaakt man. man wakker",
            "dochter wekken man. man wakker",
            "kind wekken man. man wakker",
            "kindje wekken man. man wakker",
            "meisje wekken man. man wakker",
            "dochter te wekken man. man wakker",
            "kind te wekken man. man wakker",
            "kindje te wekken man. man wakker",
            "meisje te wekken man. man wakker",
            "dochter wekt man. man wakker",
            "kind wekt man. man wakker",
            "kindje wekt man. man wakker",
            "meisje wekt man. man wakker",
            "dochter gewekt man. man wakker",
            "kind gewekt man. man wakker",
            "kindje gewekt man. man wakker",
            "meisje gewekt man. man wakker",
            "dochter wekte man. man wakker",
            "kind wekte man. man wakker",
            "kindje wekte man. man wakker",
            "meisje wekte man. man wakker",
            "dochter waarschuwen opa. opa wakker",//deqwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
            "kind waarschuwen opa. opa wakker",
            "kindje waarschuwen opa. opa wakker",
            "meisje waarschuwen opa. opa wakker",
            "dochter te waarschuwen opa. opa wakker",
            "kind te waarschuwen opa. opa wakker",
            "kindje te waarschuwen opa. opa wakker",
            "meisje te waarschuwen opa. opa wakker",
            "dochter waarschuwt opa. opa wakker",
            "kind waarschuwt opa. opa wakker",
            "kindje waarschuwt opa. opa wakker",
            "meisje waarschuwt opa. opa wakker",
            "dochter gewaarschuwd opa. opa wakker",
            "kind gewaarschuwd opa. opa wakker",
            "kindje gewaarschuwd opa. opa wakker",
            "meisje gewaarschuwd opa. opa wakker",
            "dochter waarschuwde opa. opa wakker",
            "kind waarschuwde opa. opa wakker",
            "kindje waarschuwde opa. opa wakker",
            "meisje waarschuwde opa. opa wakker",
            "dochter wakker maken opa. opa wakker",
            "kind wakker maken opa. opa wakker",
            "kindje wakker maken opa. opa wakker",
            "meisje wakker maken opa. opa wakker",
            "dochter wakker te maken opa. opa wakker",
            "kind wakker te maken opa. opa wakker",
            "kindje wakker te maken opa. opa wakker",
            "meisje wakker te maken opa. opa wakker",
            "dochter maakt wakker opa. opa wakker",
            "kind maakt wakker opa. opa wakker",
            "kindje maakt wakker opa. opa wakker",
            "meisje maakt wakker opa. opa wakker",
            "dochter maakte wakker opa. opa wakker",
            "kind maakt wakker opa. opa wakker",
            "kindje maakt wakker opa. opa wakker",
            "meisje maakt wakker opa. opa wakker",
            "dochter maakte wakker opa. opa wakker",
            "kind maakte wakker opa. opa wakker",
            "kindje maakte wakker opa. opa wakker",
            "meisje maakte wakker opa. opa wakker",
            "dochter wakker gemaakt opa. opa wakker",
            "kind wakker gemaakt opa. opa wakker",
            "kindje wakker gemaakt opa. opa wakker",
            "meisje wakker gemaakt opa. opa wakker",
            "dochter wekken opa. opa wakker",
            "kind wekken opa. opa wakker",
            "kindje wekken opa. opa wakker",
            "meisje wekken opa. opa wakker",
            "dochter te wekken opa. opa wakker",
            "kind te wekken opa. opa wakker",
            "kindje te wekken opa. opa wakker",
            "meisje te wekken opa. opa wakker",
            "dochter wekt opa. opa wakker",
            "kind wekt opa. opa wakker",
            "kindje wekt opa. opa wakker",
            "meisje wekt opa. opa wakker",
            "dochter gewekt opa. opa wakker",
            "kind gewekt opa. opa wakker",
            "kindje gewekt opa. opa wakker",
            "meisje gewekt opa. opa wakker",
            "dochter wekte opa. opa wakker",
            "kind wekte opa. opa wakker",
            "kindje wekte opa. opa wakker",
            "meisje wekte opa. opa wakker",
            "dochter waarschuwen papa. papa wakker",//ooooooooooooooooooooooooooooooooooooooooooooooo
            "kind waarschuwen papa. papa wakker",
            "kindje waarschuwen papa. papa wakker",
            "meisje waarschuwen papa. papa wakker",
            "dochter te waarschuwen papa. papa wakker",
            "kind te waarschuwen papa. papa wakker",
            "kindje te waarschuwen papa. papa wakker",
            "meisje te waarschuwen papa. papa wakker",
            "dochter waarschuwt papa. papa wakker",
            "kind waarschuwt papa. papa wakker",
            "kindje waarschuwt papa. papa wakker",
            "meisje waarschuwt papa. papa wakker",
            "dochter gewaarschuwd papa. papa wakker",
            "kind gewaarschuwd papa. papa wakker",
            "kindje gewaarschuwd papa. papa wakker",
            "meisje gewaarschuwd papa. papa wakker",
            "dochter waarschuwde papa. papa wakker",
            "kind waarschuwde papa. papa wakker",
            "kindje waarschuwde papa. papa wakker",
            "meisje waarschuwde papa. papa wakker",
            "dochter wakker maken papa. papa wakker",
            "kind wakker maken papa. papa wakker",
            "kindje wakker maken papa. papa wakker",
            "meisje wakker maken papa. papa wakker",
            "dochter wakker te maken papa. papa wakker",
            "kind wakker te maken papa. papa wakker",
            "kindje wakker te maken papa. papa wakker",
            "meisje wakker te maken papa. papa wakker",
            "dochter maakt wakker papa. papa wakker",
            "kind maakt wakker papa. papa wakker",
            "kindje maakt wakker papa. papa wakker",
            "meisje maakt wakker papa. papa wakker",
            "dochter maakte wakker papa. papa wakker",
            "kind maakt wakker papa. papa wakker",
            "kindje maakt wakker papa. papa wakker",
            "meisje maakt wakker papa. papa wakker",
            "dochter maakte wakker papa. papa wakker",
            "kind maakte wakker papa. papa wakker",
            "kindje maakte wakker papa. papa wakker",
            "meisje maakte wakker papa. papa wakker",
            "dochter wakker gemaakt papa. papa wakker",
            "kind wakker gemaakt papa. papa wakker",
            "kindje wakker gemaakt papa. papa wakker",
            "meisje wakker gemaakt papa. papa wakker",
            "dochter wekken papa. papa wakker",
            "kind wekken papa. papa wakker",
            "kindje wekken papa. papa wakker",
            "meisje wekken papa. papa wakker",
            "dochter te wekken papa. papa wakker",
            "kind te wekken papa. papa wakker",
            "kindje te wekken papa. papa wakker",
            "meisje te wekken papa. papa wakker",
            "dochter wekt papa. papa wakker",
            "kind wekt papa. papa wakker",
            "kindje wekt papa. papa wakker",
            "meisje wekt papa. papa wakker",
            "dochter gewekt papa. papa wakker",
            "kind gewekt papa. papa wakker",
            "kindje gewekt papa. papa wakker",
            "meisje gewekt papa. papa wakker",
            "dochter wekte papa. papa wakker",
            "kind wekte papa. papa wakker",
            "kindje wekte papa. papa wakker",
            "meisje wekte papa. papa wakker",
            "dochter waarschuwen vader. vader wakker",//llllllllllllllllllllllllllllllllllllllllllllll
            "kind waarschuwen vader. vader wakker",
            "kindje waarschuwen vader. vader wakker",
            "meisje waarschuwen vader. vader wakker",
            "dochter te waarschuwen vader. vader wakker",
            "kind te waarschuwen vader. vader wakker",
            "kindje te waarschuwen vader. vader wakker",
            "meisje te waarschuwen vader. vader wakker",
            "dochter waarschuwt vader. vader wakker",
            "kind waarschuwt vader. vader wakker",
            "kindje waarschuwt vader. vader wakker",
            "meisje waarschuwt vader. vader wakker",
            "dochter gewaarschuwd vader. vader wakker",
            "kind gewaarschuwd vader. vader wakker",
            "kindje gewaarschuwd vader. vader wakker",
            "meisje gewaarschuwd vader. vader wakker",
            "dochter waarschuwde vader. vader wakker",
            "kind waarschuwde vader. vader wakker",
            "kindje waarschuwde vader. vader wakker",
            "meisje waarschuwde vader. vader wakker",
            "dochter wakker maken vader. vader wakker",
            "kind wakker maken vader. vader wakker",
            "kindje wakker maken vader. vader wakker",
            "meisje wakker maken vader. vader wakker",
            "dochter wakker te maken vader. vader wakker",
            "kind wakker te maken vader. vader wakker",
            "kindje wakker te maken vader. vader wakker",
            "meisje wakker te maken vader. vader wakker",
            "dochter maakt wakker vader. vader wakker",
            "kind maakt wakker vader. vader wakker",
            "kindje maakt wakker vader. vader wakker",
            "meisje maakt wakker vader. vader wakker",
            "dochter maakte wakker vader. vader wakker",
            "kind maakt wakker vader. vader wakker",
            "kindje maakt wakker vader. vader wakker",
            "meisje maakt wakker vader. vader wakker",
            "dochter maakte wakker vader. vader wakker",
            "kind maakte wakker vader. vader wakker",
            "kindje maakte wakker vader. vader wakker",
            "meisje maakte wakker vader. vader wakker",
            "dochter wakker gemaakt vader. vader wakker",
            "kind wakker gemaakt vader. vader wakker",
            "kindje wakker gemaakt vader. vader wakker",
            "meisje wakker gemaakt vader. vader wakker",
            "dochter wekken vader. vader wakker",
            "kind wekken vader. vader wakker",
            "kindje wekken vader. vader wakker",
            "meisje wekken vader. vader wakker",
            "dochter te wekken vader. vader wakker",
            "kind te wekken vader. vader wakker",
            "kindje te wekken vader. vader wakker",
            "meisje te wekken vader. vader wakker",
            "dochter wekt vader. vader wakker",
            "kind wekt vader. vader wakker",
            "kindje wekt vader. vader wakker",
            "meisje wekt vader. vader wakker",
            "dochter gewekt vader. vader wakker",
            "kind gewekt vader. vader wakker",
            "kindje gewekt vader. vader wakker",
            "meisje gewekt vader. vader wakker",
            "dochter wekte vader. vader wakker",
            "kind wekte vader. vader wakker",
            "kindje wekte vader. vader wakker",
            "meisje wekte vader. vader wakker"

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

        db = new DatabaseHelper(this);

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
        Bundle extras = getIntent().getExtras();
        long currentPatientId = extras.getLong("currentPatientId");
        if(currentPatientId <= 0)
        {
            Toast.makeText(getBaseContext(), "Gelieve een patient aan te duiden", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void goToWritingTest_onClick(MenuItem m)
    {
        Bundle extras = getIntent().getExtras();
        long currentPatientId = extras.getLong("currentPatientId");
        if(currentPatientId <= 0)
        {
            Toast.makeText(getBaseContext(), "Gelieve een patient aan te duiden", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, writing_test.class);
            startActivity(intent);
        }
    }

    public void goToStartScreen_onClick(MenuItem m)
    {
        Bundle extras = getIntent().getExtras();
        long currentPatientId = extras.getLong("currentPatientId");
        if(currentPatientId <= 0)
        {
            Toast.makeText(getBaseContext(), "Gelieve een patient aan te duiden", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, StartScherm.class);
            intent.putExtra("currentPatientId", currentPatientId);
            startActivity(intent);
        }
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
        int numberOfUnUsedWords = 0;
        float substitutiegedrag = 0;
        int numberOfCausaalVerbandenUsed = 0;
        int efficiëntieScore = 0;
        int substitutiegedragScore = 0;
        int numberOfCausaalVerbandenUsedScore = 0;
        int numberOfWordsUsedScore = 0;
        int totalNumberOfWords = 0;
        int FUBAR = 0;
        int test = 0;

        EditText descriptionImage = (EditText) findViewById(R.id.descriptionImage);
        TextView result = (TextView) findViewById(R.id.test);

        String description = descriptionImage.getText().toString();
        description = description.toLowerCase();

        String descriptionSplittedBySpace [] = description.split(" ");
        String descriptionSplittedByPoint [] = description.split(".");

        for(int i = 0; i < descriptionSplittedBySpace.length; i++)
        {
            for(int j = 0; j < woordenLijstEfficiëntie.length; j++)
            {
                if(descriptionSplittedBySpace[i].equals(woordenLijstEfficiëntie[j].toLowerCase()))
                {
                    numberOfWordsUsed ++;
                }
            }
        }
//        for(int i = 0; i < woordenLijstEfficiëntie.length; i++)
//        {
//            if(description.equals(woordenLijstEfficiëntie[i].toLowerCase()))
//            {
//                FUBAR ++;
//                numberOfWordsUsed += howManyTimesIsTheWordUsed(descriptionSplittedBySpace, woordenLijstEfficiëntie, i);
//            }
//            test ++;
//        }

        for(int i = 0; i < descriptionSplittedBySpace.length; i++)
        {
            numberOfWordsUsedSubstitutie = 0;
            for(int j = 0; j < woordenlijstSubstitutiegedrag.length; j ++)
            {
                if(descriptionSplittedBySpace[i].equals(woordenlijstSubstitutiegedrag[j].toLowerCase()))
                {
                    numberOfWordsUsedSubstitutie ++;
                }
            }

            for(int k = 0; k < woordenLijstEfficiëntie.length; k++)
            {
                if(descriptionSplittedBySpace[i].equals(woordenLijstEfficiëntie[k].toLowerCase()))
                {
                    numberOfWordsUsedSubstitutie ++;
                }
            }

            for(int l = 0; l < woordenlijstCausaalVerband.length; l ++)
            {
                if(descriptionSplittedBySpace[i].contains(woordenlijstCausaalVerband[l].toLowerCase()))
                {
                    numberOfWordsUsedSubstitutie ++;
                }
            }

            if(numberOfWordsUsedSubstitutie == 0)
            {
                numberOfUnUsedWords ++;
            }
        }

        totalNumberOfWords = descriptionSplittedBySpace.length;

        efficiëntie = (Float.parseFloat(numberOfWordsUsed + "") / Float.parseFloat(totalNumberOfWords + "")) * 100;

        if(numberOfUnUsedWords == 0)
        {
            substitutiegedrag = 0;
        }
        else
        {
            substitutiegedrag = (Float.parseFloat(numberOfUnUsedWords + "") / Float.parseFloat(totalNumberOfWords + "")) * 100;
        }


        for(int i = 0; i < woordenlijstCausaalVerband.length; i ++)

        {
            if(description.contains(woordenlijstCausaalVerband[i].toLowerCase()))
            {
                numberOfCausaalVerbandenUsed ++;
            }
        }

        if(0 < efficiëntie && efficiëntie <= 25)
        {
            efficiëntieScore = 1;
        }
        else if(25 < efficiëntie && efficiëntie <= 50)
        {
            efficiëntieScore = 2;
        }
        else if(50 < efficiëntie && efficiëntie <= 75)
        {
            efficiëntieScore = 3;
        }
        else if(75 < efficiëntie && efficiëntie <= 100)
        {
            efficiëntieScore = 4;
        }
        else
        {
            efficiëntieScore = 0;
        }

        if(0 <= substitutiegedrag && substitutiegedrag <= 24)
        {
            substitutiegedragScore = 4;
        }
        else if(25 <= substitutiegedrag && substitutiegedrag <= 49)
        {
            substitutiegedragScore = 3;
        }
        else if(50 <= substitutiegedrag && substitutiegedrag <= 74)
        {
            substitutiegedragScore = 2;
        }
        else if(75 <= substitutiegedrag && substitutiegedrag <= 100)
        {
            substitutiegedragScore = 1;
        }
        else
        {
            substitutiegedragScore = 0;
        }

        if(0 < numberOfCausaalVerbandenUsed)
        {
            numberOfCausaalVerbandenUsedScore = Integer.parseInt(numberOfCausaalVerbandenUsed + "");
        }
        else
        {
            numberOfCausaalVerbandenUsedScore = 0;
        }

        Bundle extras = getIntent().getExtras();

            long id = extras.getLong("currentPatientId");
            Patient patient = db.getPatient(id);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date today = Calendar.getInstance().getTime();
            String date = format.format(today);

            patient.setScoreProductiviteit(totalNumberOfWords);
            patient.setScoreEfficientie(efficiëntieScore);
            patient.setScoreSubstitutie(substitutiegedragScore);
            patient.setScoreCoherentie(numberOfCausaalVerbandenUsedScore);
            patient.setTestdatum(date);

            int daysBetween = 0;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date geboortedatumT = formatter.parse(patient.getGeboortedatum());
                Date testdatumOmgezet = formatter.parse(patient.getTestdatum());
                daysBetween = getDifferenceDays(geboortedatumT, testdatumOmgezet);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            patient.setChronologischeLeeftijd(daysBetween);

            db.updatePatient(patient);

        result.setText("Productiviteit " + totalNumberOfWords + " woorden gebruikt" + "\n" +
                getString(R.string.efficiëntie) + " " + efficiëntie + "%" + "\n"
                + getString(R.string.substitutiegedrag) + " " + substitutiegedrag + "%" + "\n"
                + "aantal causale verbanden " + numberOfCausaalVerbandenUsed + "\n");
    }

    public int howManyTimesIsTheWordUsed(String descriptionSplitted [], String listOfWords [], int word)
    {
        int numberOfWordsUsedOfTheCuurentWordInTheList = 0;
        for(int j = 0; j < descriptionSplitted.length; j ++)
        {
            if(descriptionSplitted[j].equals(listOfWords[word].toLowerCase()))
            {
                numberOfWordsUsedOfTheCuurentWordInTheList ++;
            }
        }

        return numberOfWordsUsedOfTheCuurentWordInTheList;
    }

    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
        daysdiff = (int) diffDays;
        return daysdiff;
    }
}
