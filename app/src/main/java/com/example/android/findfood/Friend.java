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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;

public class Friend extends AppCompatActivity {

    public static final String EXTRA_MESSAGE4 = "friend";

    //menggunakan arrayList untuk menyimpan
    //data yang akan ditampilkan
    private ArrayList<String> items = new ArrayList<>();

    //penghubung antara data dengan listview
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        new AmbilData().execute("http://makersinstitute.id:5000/users/online");

        final ListView listFriend = (ListView) findViewById(R.id.listFriend);
        /*buat adapter
       3 parameter:
          - context:
          - layout listview: disini kita menggunakan yg sudah ada (nantinya bisa custom)
          - datanya: items
       */
        adapter = new ArrayAdapter (this, android.R.layout.simple_expandable_list_item_1, items);

        //set adapter ke listview
        listFriend.setAdapter(adapter);

        listFriend.setClickable(true);
        listFriend.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String isiBaris = (String) listFriend.getItemAtPosition(position);
                String pesan = "Posisi:"+position +"->"+ isiBaris;
                Toast toast = Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT);
                toast.show();
            }
        });




        //untuk toolbar
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(myToolbar2);
        getSupportActionBar().setTitle("Friend Nearby");

        //notif
        //ambil intent
        Intent intent = getIntent();
        //ambil datanya
        String pesan = intent.getStringExtra(Main2Activity.EXTRA_MESSAGE4);
        //tampilkan toast
        Toast t = Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_LONG);
        t.show();

    }

    //tollbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.oBack:
                klikKembali();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void klikKembali() {
        Intent intent2 = getIntent();
        intent2.putExtra(Main2Activity.EXTRA_MESSAGE4,"");
        setResult(RESULT_OK, intent2);
        finish();

    }

    private class AmbilData extends AsyncTask<String, Integer, String> {
        protected String judul;
        protected String pengarang;
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
                JSONArray jo  =  jsonObj.getJSONArray("data_online");
                //loop jsonarray
                for(int i=0 ; i<jo.length(); i++){
                    JSONObject jo2 = jo.getJSONObject(i);
                //Log.v("yw","jo2:"+jo2.getString("type"));
                    items.add(jo2.getString("username"));

                }



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
            return pengarang;
        }


        protected void onPostExecute(String result) {

        }
    }
}
