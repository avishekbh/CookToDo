package com.example.prateep.attempt3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

import static com.example.prateep.attempt3.activity_ingredients.CALL;

public class activity_payment extends AppCompatActivity {

    private String m_Text = "1";
    String ussdCode = "*#06#";
    String upiPin;
    String[] itemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payment);
        itemName = (String[]) getIntent().getSerializableExtra("item");
        String price = itemName[0];
        TextView txt_Bill = (TextView) findViewById(R.id.txt_showBill);
        txt_Bill.setText("Bill Amount : "+price);
        TextView txt_pin = (TextView) findViewById(R.id.txt_UPI);
        upiPin = txt_pin.getText().toString();
        //ussdCode = "*99*1*1*9674538178*"+m_Text+"*"+upiPin+"#";

    }
/*
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("$%#$%#$%");
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(ussdToCallableUri(ussdCode));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                startActivity(callIntent);
            }
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(activity_payment.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity_payment.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(activity_payment.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(activity_payment.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
*/
    public Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:"))
            uriString += "tel:";

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }

    public void toNavPay(View v) {

        // TODO Auto-generated method stub
        /*
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(ussdToCallableUri(ussdCode));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
        */
        TextView txt_pin = (TextView) findViewById(R.id.txt_UPI);
        upiPin = txt_pin.getText().toString();
        if(upiPin.length() == 6){

            Intent intent = new Intent(getApplicationContext(), activity_directions.class);
            //Get the value of the item you clicked

            String recipeName = removeSpaces(itemName[1]);
            String price = itemName[0];
            intent.putExtra("item", recipeName+"_dir");
            Toast.makeText(getApplicationContext(),
                    "Payment Successful", Toast.LENGTH_LONG).show();
            startActivity(intent);

        }
        else{
            Toast.makeText(getApplicationContext(),
                    "UPI PIN should be of 6 digits", Toast.LENGTH_LONG).show();
        }
    }

    public String removeSpaces(String s){
        //System.out.println(s);
        StringTokenizer token = new StringTokenizer(s);
        String result = "";
        for (int i = 0; i <= token.countTokens(); i++){
            result += token.nextToken();
        }
        //System.out.println(result);
        return result;
    }
}
