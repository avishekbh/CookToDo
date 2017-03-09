package com.example.prateep.attempt3;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final Integer CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        System.out.println("1321321");
    }



    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        Intent i = new Intent(getApplicationContext(),Authentication.class);
        startActivity(i);
        setContentView(R.layout.activity_authentication);
    }

    public void toLunch(View v)
    {
        // TODO Auto-generated method stub
        boolean flag=true;
        EditText phone_no = (EditText)findViewById(R.id.editText);
        String phone= phone_no .getText().toString();
        EditText pwd = (EditText)findViewById(R.id.editText4);
        String password= pwd .getText().toString();
        System.out.println(phone+" "+password);
        if(phone.length()!=10 || password.length()!=6)
            showSimplePopUp();
        else {
            for (int i = 0; i < phone.length(); i++) {
                char ch = phone.charAt(i);
                if(!(ch >= '0' && ch<='9' && (phone.charAt(0) == '9' || phone.charAt(0) == '8' || phone.charAt(0) == '7')))
                flag = false;
                break;
            }

            if (flag == true) {
                Intent i = new Intent(getApplicationContext(), activity_lunch.class);
                startActivity(i);
                setContentView(R.layout.layout_lunch);
            } else {
                showSimplePopUp();
            }
        }
    }

    public void addListenerOnButton() {

        final Context context = this;
        Intent intent = new Intent(context, Authentication.class);
        startActivity(intent);

        };

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Invalid Phone Number/Wrong Password");
        helpBuilder.setMessage("Please Enter your credentials again!");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


}





