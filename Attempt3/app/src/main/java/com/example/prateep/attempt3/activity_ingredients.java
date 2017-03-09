package com.example.prateep.attempt3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_ingredients extends AppCompatActivity {

    static final Integer CALL = 0x2;
    //Double add = 2.0, sub = 1.0;
    Double price = 0.0;
    String[][] itemName;
    String recipeName;
    ArrayList<CheckBox> boxes = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_ingredients);
        itemName = (String[][]) getIntent().getSerializableExtra("ingred");
        LinearLayout ingredTable = (LinearLayout) findViewById(R.id.lin_ingredients);
        recipeName = itemName[itemName.length - 1][0];
        int count = 0;
        for(int i = 0; i < itemName.length - 1; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(itemName[i][0]+" ( \u20B9 "+Double.parseDouble(itemName[i][1])*Double.parseDouble(itemName[i][2])+")");
            cb.setId(count++);
            cb.setTextSize(20);
            cb.setTextColor(Color.parseColor("#0000F0"));
            boxes.add(cb);
            ingredTable.addView(cb);
        }

        for(int i = 0; i < boxes.size(); i++){
            final int j = i;
            boxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    System.out.println("Checked ID :: " + boxes.get(j).getId());
                    if(isChecked)
                        price += Double.parseDouble(itemName[j][1])*Double.parseDouble(itemName[j][2]);
                    else
                        price -= Double.parseDouble(itemName[j][1])*Double.parseDouble(itemName[j][2]);
                    Toast.makeText(getApplicationContext(),
                            "₹ "+price.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(activity_ingredients.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity_ingredients.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(activity_ingredients.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(activity_ingredients.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private String m_Text = "";
    String ussdCode = "*#06#";
    String[] recipePrice = new String[2];
    public void toUSSD(View v){
        Intent intent = new Intent(getApplicationContext(), activity_payment.class);
        //Get the value of the item you clicked
        //String itemName = getIntent().getStringExtra("item");
        recipePrice[0] = Double.toString(price);
        recipePrice[1] = recipeName;
        System.out.println(recipePrice[1]);
        intent.putExtra("item", recipePrice);
        startActivity(intent);
    }
    /*
    public void toUSSD(View v){
        askForPermission(Manifest.permission.CALL_PHONE,CALL);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter UPI PIN");
        // Set up the input
        final EditText input = new EditText(this);
        int maxLength = 6;
        input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_CLASS_NUMBER);

        builder.setView(input);

        // Set up the buttons

        builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                price = 1.0;
                //String ussdCode = "*99*1*1*9674538178*"+Integer.toString(price.intValue())+"*"+m_Text+"#";
                ussdCode = "*#06#";
                System.out.println(ussdCode);


                System.out.println(m_Text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(ussdToCallableUri(ussdCode));
        try{
            startActivity(intent);
        } catch (SecurityException e){
            e.printStackTrace();
        }

        builder.show();
    }
*/
    public Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if(!ussd.startsWith("tel:"))
            uriString += "tel:";

        for(char c : ussd.toCharArray()) {

            if(c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }

        return Uri.parse(uriString);
    }
    /*
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
                System.out.println("$%#$%#$%");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(ussdToCallableUri(ussdCode));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }
            }
        }
        */
    public void toNav_1(View v)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getApplicationContext(), activity_directions.class);
        //Get the value of the item you clicked

        intent.putExtra("item", recipeName+"_dir");
        startActivity(intent);
    }
}
