package com.example.prateep.attempt3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

public class activity_courses extends AppCompatActivity {

    String[] foodItems = {"Chicken Biryani", "Mutton Rogan Josh", "Fish Paturi", "Shahi Paneer", "Bread Omelette", "Pasta", "Mughlai Paratha", "Chicken Tikka Masala", "Kashmiri Pulav", "Khara Pongal", "Veg Pizza", "Wrap", "Veg Cutlet", "HotnSour Soup", "Poha", "Paneer Butter Masala"};
    String[] foodItems_dinner = {"Chicken Biryani", "Mutton Rogan Josh", "Fish Paturi", "Shahi Paneer", "Bread Omelette", "Pasta", "Mughlai Paratha", "Chicken Tikka Masala", "Kashmiri Pulav", "Khara Pongal", "Veg Pizza", "Wrap", "Veg Cutlet", "HotnSour Soup", "Poha", "Paneer Butter Masala"};
    String[] foodItems_snacks = {"Chicken Biryani", "Mutton Rogan Josh", "Fish Paturi", "Shahi Paneer", "Bread Omelette", "Pasta", "Mughlai Paratha", "Chicken Tikka Masala", "Kashmiri Pulav", "Khara Pongal", "Veg Pizza", "Wrap", "Veg Cutlet", "HotnSour Soup", "Poha", "Paneer Butter Masala"};
    String[] foodItems_lunch = {"Chicken Biryani", "Mutton Rogan Josh", "Fish Paturi", "Shahi Paneer", "Bread Omelette", "Pasta", "Mughlai Paratha", "Chicken Tikka Masala", "Kashmiri Pulav", "Khara Pongal", "Veg Pizza", "Wrap", "Veg Cutlet", "HotnSour Soup", "Poha", "Paneer Butter Masala"};

    ArrayList<String> foodArray,foodArray_s,foodArray_l,foodArray_d;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        foodArray_s = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.lunch_list, foodItems_snacks);

        ListView listView1 = (ListView) findViewById(R.id.lstview1);
        listView1.setAdapter(adapter);
        for(String food:foodItems_snacks)
            foodArray_s.add(food.toLowerCase());
        //System.out.println(foodArray.size());
        //System.out.println(foodArray.indexOf("fish paturi"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_courses);

        //listView.setBackgroundColor(Color.BLACK);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                //Get the value of the item you clicked
                String itemClicked = foodItems_snacks[position];
                intent.putExtra("item", itemClicked);
                startActivity(intent);
            }
        });
        ListView listView2 = (ListView) findViewById(R.id.lstview2);
        listView2.setAdapter(adapter);
        //listView.setBackgroundColor(Color.BLACK);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                //Get the value of the item you clicked
                String itemClicked = foodItems_lunch[position];
                intent.putExtra("item", itemClicked);
                startActivity(intent);
            }
        });
        ListView listView3 = (ListView) findViewById(R.id.lstview3);
        listView3.setAdapter(adapter);
        //listView.setBackgroundColor(Color.BLACK);
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                //Get the value of the item you clicked
                String itemClicked = foodItems_dinner[position];
                intent.putExtra("item", itemClicked);
                startActivity(intent);
            }
        });
        Button bt1 = (Button) findViewById(R.id.bt1);
        //listView.setBackgroundColor(Color.BLACK);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(), activity_lunch.class);
                startActivity(intent);
            }
        });
        Button bt2 = (Button) findViewById(R.id.bt2);
        //listView.setBackgroundColor(Color.BLACK);
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_lunch.class);
                startActivity(intent);
            }
        });
        Button bt3 = (Button) findViewById(R.id.bt3);
        //listView.setBackgroundColor(Color.BLACK);
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_lunch.class);
                startActivity(intent);
            }
        });
        search=(SearchView) findViewById(R.id.srchvw1);
        search.setQueryHint("Enter Ingredients to get Recipe recommendations");

        //*** setOnQueryTextFocusChangeListener ***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                /*Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                if(query.equals("egg, bread, chicken, potato")){
                    final String[] foodItems = new String[7];
                    foodItems[0] = "Bread Omelette";
                    foodItems[1] = "Chicken Biryani";
                    foodItems[2] = "Chicken Tikka Masala";
                    foodItems[3] = "Mughlai Paratha";
                    foodItems[4] = "Poha";
                    foodItems[5] = "Veg Cutlet";
                    foodItems[6] = "Wrap";

                    setContentView(R.layout.layout_lunch);

                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.lunch_list, foodItems);


                    ListView listView = (ListView) findViewById(R.id.listItems);
                    listView.setAdapter(adapter);
                    //listView.setBackgroundColor(Color.BLACK);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                            Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                            //Get the value of the item you clicked
                            String itemClicked = foodItems[position];
                            intent.putExtra("item", itemClicked);
                            startActivity(intent);
                        }
                    });
                }
                else if(query.equals("egg, bread")){
                    final String[] foodItems = new String[3];
                    foodItems[0] = "Bread Omelette";
                    foodItems[1] = "Mughlai Paratha";
                    foodItems[2] = "Veg Cutlet";

                    setContentView(R.layout.layout_lunch);

                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.lunch_list, foodItems);


                    ListView listView1 = (ListView) findViewById(R.id.listItems);
                    listView1.setAdapter(adapter);
                    //listView1.setBackgroundColor(Color.BLACK);
                    listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                            Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                            //Get the value of the item you clicked
                            String itemClicked = foodItems[position];
                            intent.putExtra("item", itemClicked);
                            startActivity(intent);
                        }
                    });
                }
                else if(query.equals("egg, chicken")){
                    final String[] foodItems = new String[5];
                    foodItems[0] = "Bread Omelette";
                    foodItems[1] = "Mughlai Paratha";
                    foodItems[2] = "Chicken Biryani";
                    foodItems[3] = "Chicken Tikka Masala";
                    foodItems[4] = "Wrap";

                    setContentView(R.layout.layout_lunch);

                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.lunch_list, foodItems);


                    ListView listView2 = (ListView) findViewById(R.id.listItems);
                    listView2.setAdapter(adapter);
                    //listView2.setBackgroundColor(Color.BLACK);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                            Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                            //Get the value of the item you clicked
                            String itemClicked = foodItems[position];
                            intent.putExtra("item", itemClicked);
                            startActivity(intent);
                        }
                    });
                }
                else if(query.equals("cashew nuts, potato")){
                    final String[] foodItems = new String[6];
                    foodItems[0] = "Kashmiri Pulav";
                    foodItems[1] = "Khara Pongal";
                    foodItems[2] = "Mutton Rogan Josh";
                    foodItems[3] = "Poha";
                    foodItems[4] = "Shahi Paneer";
                    foodItems[5] = "Veg Cutlet";

                    setContentView(R.layout.layout_lunch);

                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.lunch_list, foodItems);


                    ListView listView3 = (ListView) findViewById(R.id.listItems);
                    listView3.setAdapter(adapter);
                    //listView3.setBackgroundColor(Color.BLACK);
                    listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                /*
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                */
                            Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
                            //Get the value of the item you clicked
                            String itemClicked = foodItems[position];
                            intent.putExtra("item", itemClicked);
                            startActivity(intent);
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                return false;
            }
        });


    }

    public String insertSpace(String s){
        StringBuffer sbuff = new StringBuffer();
        char[] charSeq = s.toCharArray();

        for(int i = 0;i < charSeq.length; i++){
            if(charSeq[i] >= 65 || charSeq[i] <= 90){
                sbuff.append(" "+charSeq[i]);
            }
            else
                sbuff.append(charSeq[i]);
        }
        return sbuff.toString().trim();
    }
    /*public ArrayList<String> recipe_suggester(String query) throws ClassNotFoundException, SQLException, ExecutionException, InterruptedException {

        StringTokenizer st = new StringTokenizer(query,",");
        String sql = "select R, count(*) from (";
        int k=st.countTokens();
        for(int i=0;i<k;i++){
            if(i!=k-1)
                sql = sql + "(Select recipe as R from masterTable where ingredient = \'"+st.nextToken().trim()+"\') UNION ALL ";
            else
                sql = sql + "(Select recipe as R from masterTable where ingredient = \'"+st.nextToken().trim()+"\')";
        }
        sql = sql + ") as T group by R;";
        /*
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        Statement stmt = null;

        conn = DriverManager.getConnection("jdbc:mysql://104.198.237.154/CookToDo","vplab","das123");
        //conn = DriverManager.getConnection(jdbcUrl, "root", "das123");
        stmt = conn.createStatement();
        System.out.print("abc");
        */
       /* ArrayList<String> tempList = new ArrayList<>();
        tempList.add(sql);
        //ResultSet rs = new Database2().dataForList(sql);
        Database2 db2 = new Database2();
        tempList = db2.execute(tempList).get();
        System.out.println(tempList.size());
        //System.out.println(db2.getResultset().size());
        //
        // items = db2.getResultset();
        //System.out.println(items.get(0));*/
        /*while(rs.next())
            items.add(rs.getString("R"));*/
        /*return tempList;
    }*/
    private static final int SPEECH_REQUEST_CODE = 0;
    public void displaySpeechRecognizer(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            System.out.println(spokenText);


            Intent intent = new Intent(getApplicationContext(), activity_tableIngredients.class);
            //Get the value of the item you clicked
            int position = foodArray.indexOf(spokenText.trim());
            System.out.println(position);

            Toast.makeText(getApplicationContext(),
                    (spokenText), Toast.LENGTH_SHORT).show();
            if (position<0)
                Toast.makeText(getApplicationContext(),
                        (spokenText+" Recipe Not Found"), Toast.LENGTH_SHORT).show();
            else{
                String itemClicked = foodItems[position];
                intent.putExtra("item", itemClicked);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}


class Database3 extends AsyncTask<Void, Void, Void> {

    public ResultSet data(String table_name) throws ClassNotFoundException, SQLException {
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
        String sql = "show tables;";
        ResultSet rs = stmt.executeQuery(sql);

        return rs;
    }


    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }
}

