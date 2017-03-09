package com.example.prateep.attempt3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class activity_directions extends AppCompatActivity {
    HashMap <String, Long> hashedDirections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_directions);
        String itemName = getIntent().getStringExtra("item");
        try {
            String directions = readDirections(itemName);
            TextView recipe = (TextView)findViewById(R.id.txt_directions);
            recipe.setTextSize(20);
            recipe.setMovementMethod(new ScrollingMovementMethod());
           recipe.setText(directions);
            System.out.println("DIRECTIONS GIVEN2");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public String readDirections(String filename) throws IOException, SQLException, ClassNotFoundException {
        ResultSet[] rs = ((new Database1()).data(filename));
        //InputStream inpStream = this.getAssets().open(filename);
        //BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));
        hashedDirections = new HashMap<String, Long>();
        String line = "";
        String directions="";
        while(rs[0].next()) {
            //Retrieve by column name
            String stepname = rs[0].getString("process");
            long time = rs[0].getLong("time");
            hashedDirections.put(stepname, time);
            directions += stepname + "\n\n";
            System.out.println(directions);
        }

        return directions;

    }

    public void toTTS(View v)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getApplicationContext(), activity_navigator.class);
        //Get the value of the item you clicked
        intent.putExtra("hashedDir",hashedDirections);
        startActivity(intent);
    }
}
