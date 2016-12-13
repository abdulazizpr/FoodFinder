package com.example.android.findfood;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class your_profile extends AppCompatActivity {

    public static final String EXTRA_MESSAGE3 = "profile" ;
    public static final String EXTRA_ID = "id" ;
    public TextView tUsername;
    public TextView tScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        //untuk toolbar
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar2);
        getSupportActionBar().setTitle("Profile");



        tUsername = (TextView) findViewById(R.id.uName);
        tScore = (TextView) findViewById(R.id.uScore);

        //notif
        //ambil intent
        Intent intent = getIntent();
        //ambil datanya
        String pesan = intent.getStringExtra(Main2Activity.EXTRA_MESSAGE3);
        new AmbilData().execute("http://makersinstitute.id:5000/users/" + intent.getStringExtra(Main2Activity.EXTRA_ID));;
        //tampilkan toast
        Toast t = Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_LONG);
        t.show();
    }

    //tollbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nBack:
                klikKembali();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void klikKembali() {
        Intent intent2 = getIntent();
        intent2.putExtra(Main2Activity.EXTRA_MESSAGE3,"");
        setResult(RESULT_OK, intent2);
        finish();

    }

    private class AmbilData extends AsyncTask<String, Integer, String> {
        protected String username;
        protected String score;
        protected String deskripsi;

        protected String doInBackground(String... strUrl) {
            Log.v("yw", "mulai ambil data");
            String hasil="";
            String kind = "";

            //ambil data dari internet
            InputStream inStream = null;
            int len = 500; //buffer
            try {
                URL url = new URL(strUrl[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //timeout
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);


                conn.setRequestMethod("GET");
                conn.connect();
                int response = conn.getResponseCode();
                inStream = conn.getInputStream();  //ambil stream data


                //konversi stream ke string
                /*Reader r = null;
                r = new InputStreamReader(inStream, "UTF-8");
                char[] buffer = new char[len];
                r.read(buffer);
                hasil  =  new String(buffer);*/
                Reader r = null;
                r = new InputStreamReader(inStream, "UTF-8");
                BufferedReader bfr  = new BufferedReader(r);
                String s;
                StringBuilder sb = new StringBuilder();
                s = bfr.readLine();
                while (s != null) {
                    sb.append(s);
                    s = bfr.readLine(); //baca per baris
                }

                hasil  =  sb.toString().trim();



                JSONObject jsonObj = new JSONObject(hasil);
                JSONObject jo  =  jsonObj.getJSONObject("message");
                username = jo.getString("username");
                score = jo.getString("points");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return username;
        }


        protected void onPostExecute(String result) {
            tUsername.setText(username);
            tScore.setText(score);
        }
    }
}
