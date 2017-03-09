package com.example.prateep.attempt3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class activity_navigator extends AppCompatActivity {

    private static final int MY_DATA_CHECK_CODE = 1;
    String itemName = "";
    String recipe = "";
    TextView text1;
    CountDownTimer timer;
    HashMap<String, Long> hashedDirections;
    private static final String FORMAT = "%02d:%02d:%02d";

    int seconds , minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_navigator);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        text1=(TextView)findViewById(R.id.txt_timer);


        hashedDirections = (HashMap<String, Long>)(getIntent().getSerializableExtra("hashedDir"));
        //itemName = getIntent().getStringExtra("item");
        splitRecipeIntoSteps(hashedDirections);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

/*
    public String readRecipe(String filename) throws IOException {
        String ingredients = "";
        InputStream inpStream = this.getAssets().open(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));
        String line = "";
        while((line = br.readLine()) != null ){
            ingredients += line + "\n";
        }
        return ingredients;
    }
*/
    ArrayList<String> recipeSteps = new ArrayList<>();
    public void splitRecipeIntoSteps(HashMap<String, Long> hashedDirections){

        //System.out.println(recipeSteps.get(0) + "%%%%%%%%%%%%%%");
        /*Toast.makeText(getApplicationContext(),
                ("<<" + recipeSteps.get(0) + " >>"), Toast.LENGTH_SHORT).show();
                */
        for(String step:hashedDirections.keySet())
            recipeSteps.add(step);
    }

    //int curStep = 0;

    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    public TextToSpeech mTts;
    public boolean nearToFinish = false;
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts=new TextToSpeech(activity_navigator.this, new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {
                        // TODO Auto-generated method stub
                        if(status == TextToSpeech.SUCCESS){
                            int result=mTts.setLanguage(Locale.getDefault());
                            if(result==TextToSpeech.LANG_MISSING_DATA ||
                                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.e("error", "This Language is not supported");
                            }
                            else{
                                long curStepTime = hashedDirections.get(recipeSteps.get(0));
                                timer= new CountDownTimer(curStepTime, 1000) { // adjust the milli seconds here

                                    public void onTick(long millisUntilFinished) {
                                        //System.out.println("DIRECTIONS GIVEN SM");
                                        if (millisUntilFinished <= 30000 && nearToFinish == false){
                                            nearToFinish = true;
                                            System.out.println("<<<<<<<<<");

                                        }
                                        text1.setText(""+String.format(FORMAT,
                                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                    }

                                    public void onFinish() {
                                        text1.setText("00:00:00");
                                        toneG.stopTone();
                                        nearToFinish = false;
                                    }
                                };

                                TextView recipe = (TextView)findViewById(R.id.txt_step);
                                recipe.setText(recipeSteps.get(0));
                                timer.start();

                                mTts.speak(recipeSteps.get(0), TextToSpeech.QUEUE_FLUSH, null);
                                if(nearToFinish){
                                    toneG.startTone(ToneGenerator.TONE_PROP_BEEP, 30000);
                                }
                                //timer.onFinish();
                                recipeSteps.remove(0);
                                timer.onFinish();
                                //curStep++;
                            }
                        }
                        else
                            Log.e("error", "Initilization Failed!");
                    }
                });
                } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    public void toNextStep(View v)
    {
        // TODO Auto-generated method stub
        mTts.stop();
        timer.cancel();
        toneG.stopTone();
        nearToFinish = false;
        if(!recipeSteps.isEmpty()){
            long curStepTime = hashedDirections.get(recipeSteps.get(0));
            timer = new CountDownTimer(curStepTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished <= 30000 ){
                        nearToFinish = true;
                        System.out.println(">>>>>>>>");
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 30000);
                    }
                    text1.setText(""+String.format(FORMAT,
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                @Override
                public void onFinish() {
                    text1.setText("00:00:00");
                    toneG.stopTone();
                    nearToFinish = false;
                }


            };
            System.out.println("DIRECTIONS GIVEN AFTER");
            TextView recipe = (TextView)findViewById(R.id.txt_step);
            recipe.setText(recipeSteps.get(0));
            timer.start();
            mTts.speak(recipeSteps.get(0), TextToSpeech.QUEUE_FLUSH, null);
            if(nearToFinish){
                toneG.startTone(ToneGenerator.TONE_PROP_BEEP, 30000);

            }
            recipeSteps.remove(0);
            if(recipeSteps.isEmpty()){
                showSimplePopUp();;

            }
                //System.out.println("DIRECTIONS FINISHED");
            System.out.println("DIRECTIONS GIVEN AFT");
        }
    }

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Done and Ready to Serve!");
        helpBuilder.setMessage("You have successfully prepared the dish!");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent i = new Intent(getApplicationContext(), activity_lunch.class);
                        startActivity(i);
                        setContentView(R.layout.layout_lunch);
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}
