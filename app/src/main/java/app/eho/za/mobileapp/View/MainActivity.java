package app.eho.za.mobileapp.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.eho.za.mobileapp.Controller.HttpHandler;
import app.eho.za.mobileapp.Model.PeopleInformation;
import app.eho.za.mobileapp.R;

public class MainActivity extends AppCompatActivity {

    //private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView listViewPeople;
    String next, speciesName;
    EditText edtSearch;
    SimpleAdapter adapter;

    // URL to get contacts JSON
    private static String url = "http://swapi.co/api/people/?page=";

    ArrayList<HashMap<String, String>> itemList;

    List<PeopleInformation> peopleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = new ArrayList<>();
        peopleList = new ArrayList<PeopleInformation>();
        listViewPeople = (ListView) findViewById(R.id.list);
        edtSearch = (EditText) findViewById(R.id.edtSearch);

        new GetContacts().execute();

        listViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });






    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();



        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler httpHandler = new HttpHandler();
            int count = 1;

            do{

                // Making a request to url and getting response
            String jsonStrPeople = httpHandler.makeServiceCall(url + count);

            if (jsonStrPeople != null) {

                try {

                    JSONObject jsonObj = new JSONObject(jsonStrPeople);
                    next = jsonObj.getString("next");
                    count++;

                    JSONArray arrayResults = jsonObj.getJSONArray("results");

                        // looping through All ArrayResults
                        for (int i = 0; i < arrayResults.length(); i++) {

                            JSONObject results = arrayResults.getJSONObject(i);
                            String name = results.getString("name");
                            String gender = results.getString("gender");
                            String height = results.getString("height");
                            String mass = results.getString("mass");
                            String hair_color = results.getString("hair_color");
                            String skin_color = results.getString("skin_color");
                            String birth_year = results.getString("birth_year");




                            HttpHandler httpHandlerSpecies = new HttpHandler();

                            try
                            {
                                JSONArray species = results.getJSONArray("species");
                                String speciesURL = species.get(0).toString();
                                String jsonStrSpecies = httpHandlerSpecies.makeServiceCall(speciesURL);

                                if (jsonStrSpecies != null) {
                                    JSONObject jsonObj1 = new JSONObject(jsonStrSpecies);
                                    speciesName = jsonObj1.getString("name");
                                }

                            }
                            catch (IndexOutOfBoundsException e)
                            {

                            }

                            HashMap<String, String> contact = new HashMap<>();
                            contact.put("name", "Name: "+name);
                            contact.put("gender", "Gender: "+gender);
                            contact.put("species", "Species: "+speciesName);

                            itemList.add(contact);
                            PeopleInformation peopleInformation = new PeopleInformation(name,gender,speciesName, height, mass, hair_color, skin_color, birth_year);
                            peopleList.add(peopleInformation);


                        }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            }while(next!="null");

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
                 adapter = new SimpleAdapter(
                    MainActivity.this, itemList, R.layout.list_item,
                    new String[]{"name", "gender", "species"},
                    new int[]{R.id.name, R.id.gender, R.id.species});

            listViewPeople.setAdapter(adapter);
            listViewPeople.setTextFilterEnabled(true);

            listViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    //String name = itemList.get(position).toString();
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Information")
                            .setMessage("Name: "+peopleList.get(position).getName() +"\n"+
                                        "Height: "+peopleList.get(position).getHeight() +"\n"+
                                    "Mass: "+peopleList.get(position).getMass() +"\n"+
                                    "Hair colour: "+peopleList.get(position).getHairColour() +"\n"+
                                    "Skin colour: "+peopleList.get(position).getSkinColor() +"\n"+
                                    "Year of birth: "+peopleList.get(position).getYearOfBirth() )
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();


                }
            });


            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    MainActivity.this.adapter.getFilter().filter(s);

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }




    }





}
