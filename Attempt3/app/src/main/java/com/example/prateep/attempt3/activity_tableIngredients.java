package com.example.prateep.attempt3;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class activity_tableIngredients extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tableingredients);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String itemName = getIntent().getStringExtra("item");
        itemName = removeSpaces(itemName);
        //System.out.println(itemName);

        try {
            String ingredients = readIngredients(itemName);
            //TextView recipe = (TextView)findViewById(R.id.textView);
            //recipe.setText(ingredients);
            TableLayout tableIngred = (TableLayout) findViewById(R.id.table_ingred);
            for(int i = 0;i < masterIngred.length-1; i++){
                TableRow row= new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView item_ingred = new TextView(this);
                TextView qty_ingred = new TextView(this);
                item_ingred.setTextColor(Color.parseColor("#0000F0"));
                item_ingred.setTextSize(20);
                item_ingred.setText(masterIngred[i][0]+"\t\t\t");
                DecimalFormat formatter = new DecimalFormat("#0.00");
                //System.out.println(formatter.format(d));
                qty_ingred.setText(Double.parseDouble(masterIngred[i][1])+" "+masterIngred[i][3]);
                qty_ingred.setTextSize(20);
                row.addView(item_ingred);
                row.addView(qty_ingred);
                tableIngred.addView(row, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    //HashMap<String, HashMap<Double, HashMap<Double, String>>> masterIngred = new HashMap<>();
    String[][] masterIngred;
    public String readIngredients(String itemName) throws IOException, SQLException, ClassNotFoundException {
        ResultSet[] rs = ((new Database1()).data(itemName));
        rs[1].next();
        masterIngred = new String[rs[1].getInt(1) + 1][4];
        //masterIngred[0][0] = itemName;
        //System.out.println(masterIngred.length);
        //InputStream inpStream = this.getAssets().open(filename);
        //BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));
        DecimalFormat formatter = new DecimalFormat("#0.00");
        String line = "";
        String ingredients="";
        int curIngred = 0;
        while(rs[0].next()) {
            //Retrieve by column name
            String ingredient_name = rs[0].getString("ingr_name");
            float qty = rs[0].getFloat("qty");
            float price = rs[0].getFloat("price");
            String unit = rs[0].getString("unit");
            masterIngred[curIngred][0] = ingredient_name;
            masterIngred[curIngred][1] = formatter.format(qty);
            masterIngred[curIngred][2] = formatter.format(price);
            masterIngred[curIngred++][3] = unit;
            ingredients += ingredient_name + " " + qty + " " + price + " " + unit + "\n";
            //System.out.println(ingredients);
        }
        masterIngred[curIngred][0] = itemName;
        return ingredients;
    }

    public void toIngredients(View v)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getApplicationContext(),activity_ingredients.class);
        //String itemName = getIntent().getStringExtra("item");
        //itemName = removeSpaces(itemName);
        intent.putExtra("ingred", masterIngred);
        startActivity(intent);
        setContentView(R.layout.layout_ingredients);
    }

    public void toNav(View v)
    {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getApplicationContext(), activity_directions.class);
        //Get the value of the item you clicked
        String itemName = getIntent().getStringExtra("item") + "_dir";
        itemName = removeSpaces(itemName);
        intent.putExtra("item", itemName);
        startActivity(intent);
    }

}


class Database1 extends AsyncTask<Void, Void, Void> {

    public ResultSet[] data(String table_name) throws ClassNotFoundException, SQLException {

        ResultSet[] results = new ResultSet[2];
        System.out.print("pdh");
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        Statement stmt = null;
        /*
        String jdbcUrl = String.format(
                "jdbc:mysql://google/%s?cloudSqlInstance=%s&"
                        + "socketFactory=com.google.cloud.sql.mysql.SocketFactory",
                "CookToDo",
                "flash-precept-157618:us-central1:cooktodo");
*/
        //Connection connection = DriverManager.getConnection(jdbcUrl, "root", "");
        conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-azure-east-c.cloudapp.net/cooktodo","ba3fbb9231ddd5","c468e6eb");
        //conn = DriverManager.getConnection(jdbcUrl, "root", "das123");
        stmt = conn.createStatement();
        System.out.print("abc");
        String sql = "SELECT * FROM "+table_name+";";
        ResultSet rs = stmt.executeQuery(sql);
        results[0] = rs;
        stmt = conn.createStatement();
        String sql1 = "select count(*) from "+table_name+";";
        ResultSet rs1 = stmt.executeQuery(sql1);
        results[1] = rs1;
        System.out.print("def");
        System.out.println(rs);
        return results;
    }


    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }
}
