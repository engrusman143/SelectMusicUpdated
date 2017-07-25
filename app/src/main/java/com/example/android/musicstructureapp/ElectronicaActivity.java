package com.example.android.musicstructureapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.example.android.musicstructureapp.R.id.pauseButton;
import static com.example.android.musicstructureapp.R.id.playButton;
import static com.example.android.musicstructureapp.R.id.radio;
import static com.example.android.musicstructureapp.R.id.statusText;

public class ElectronicaActivity extends AppCompatActivity {
    boolean radioSelected = false;
    boolean radioPlaying = false;
    String radioListening;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // recovering the instance state on screen switch
        if (savedInstanceState != null) {
            radioSelected = savedInstanceState.getBoolean("Play_state");
            radioPlaying = savedInstanceState.getBoolean("Radio_playing");
            radioListening = savedInstanceState.getString("Radio_listening_to");
        }
        setContentView(R.layout.activity_electronica);

        // Find radios by id to assign them event Listeners and Custom Font
        Button protonButton = (Button) findViewById(R.id.proton);
        Button rinseButton = (Button) findViewById(R.id.rinsefm);
        Button bassdriveButton = (Button) findViewById(R.id.bassdrive);
        Button bbconeButton = (Button) findViewById(R.id.bbcone);
        Button pulseButton = (Button) findViewById(R.id.pulse);
        final TextView statusText = (TextView) findViewById(R.id.statusText);
        final TextView radioNameStatusText = (TextView) findViewById(R.id.radioNameStatus);

        //Assigning custom font to radios
        Typeface typefaceCustomFont = Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.ttf");
        protonButton.setTypeface(typefaceCustomFont);
        rinseButton.setTypeface(typefaceCustomFont);
        bassdriveButton.setTypeface(typefaceCustomFont);
        pulseButton.setTypeface(typefaceCustomFont);
        bbconeButton.setTypeface(typefaceCustomFont);
        statusText.setTypeface(typefaceCustomFont);
        radioNameStatusText.setTypeface(typefaceCustomFont);

        // Find the Close Explanation Button to attach the eventListener
        Button closeExplButton = (Button) findViewById(R.id.closeExplanationButton);

        // Hides explanation box on Close Explanation Button click
        closeExplButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView explanation = (ScrollView) findViewById(R.id.explanation);
                explanation.setVisibility(View.GONE);
            }
        });

        //Gets the Play, Pause and Return button
        final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        final ImageButton pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        final ImageButton retrunButton = (ImageButton) findViewById(R.id.returnButton);

        //On Click of Play Button, hides Play button, shows Pause button ( and should play audio when added)
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioSelected) {
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);
                    radioSelected = true;
                    radioPlaying = true;
                    statusText.setText(getString(R.string.nowplaying));
                }
            }
        });

        //On Click of Pause Button, hides Pause button, shows Play button ( and should pause audio when added)
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                radioPlaying = false;
                radioSelected = true;
                statusText.setText(getString(R.string.pause));
            }
        });

        //On click of Return button creates intent to the Genre activity
        retrunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent genreIntent = new Intent(ElectronicaActivity.this, MainActivity.class);
                startActivity(genreIntent);
            }
        });

        //Changes the string of Status textView, set radioSelected & RadioPlaying variables to true
        // and allow play button to switch to pause, should play audio - Radio Changer Functionality for rinse radio
        rinseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioListening = getString(R.string.rinsefm);
                setRadioPlaying();
            }
        });
        //Radio Changer Functionality for bassdrive radio
        bassdriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioListening = getString(R.string.bassdrive);
                setRadioPlaying();
            }
        });
        //Radio Changer Functionality for proton radio
        protonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioListening = getString(R.string.proton);
                setRadioPlaying();
            }
        });
        //Radio Changer Functionality for bbcone radio
        bbconeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioListening = getString(R.string.bbcradioone);
                setRadioPlaying();
            }
        });
        //Radio Changer Functionality for pulse radio
        pulseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioListening = getString(R.string.pulse);
                setRadioPlaying();
            }
        });
    }

    //Changes the currently playing radio string and status of player
    //Here the MediaPlayer can be integrated, every listener that refers this method should pass
    //a String containing the url of the streamed radio
    public void setRadioPlaying() {
        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        ImageButton pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        TextView statusText = (TextView) findViewById(R.id.statusText);
        TextView radioNameStatusText = (TextView) findViewById(R.id.radioNameStatus);
        radioPlaying = true;
        radioSelected = true;
        playButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        statusText.setText(getString(R.string.nowplaying));
        radioNameStatusText.setText(radioListening);
    }
    // string of the currently playing radio, prevents from pressing Play button, when no radio is selected
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        ImageButton pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        TextView statusText = (TextView) findViewById(R.id.statusText);
        TextView radioNameStatusText = (TextView) findViewById(R.id.radioNameStatus);
        if ((radioSelected) && (radioPlaying)) {
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            statusText.setText(getString(R.string.nowplaying));
            radioNameStatusText.setText(radioListening);
        } else if (radioSelected) {
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
            statusText.setText(getString(R.string.pause));
            radioNameStatusText.setText(radioListening);
        } else {
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
        }
    }
    //currently playing radio and radio URL, and state of radio pausing or playing
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("Play_state", radioSelected);
        outState.putBoolean("Radio_playing", radioPlaying);
        outState.putString("Radio_listening_to", radioListening);
        super.onSaveInstanceState(outState);
    }
}
