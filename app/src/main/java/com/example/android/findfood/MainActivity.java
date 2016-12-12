package com.example.android.findfood;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    static final int ACT2_REQUEST = 99;
    public static final String EXTRA_MESSAGE1 = "keluar" ;
    public String inputNama;
    public EditText masukan1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masukan1 = (EditText) findViewById(R.id.username);
    }

    //cara 2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        inputNama = masukan1.getText().toString();

        // cek request code
        if (requestCode == ACT2_REQUEST) {

            //tampilkan toast
            Toast toast = Toast.makeText(getApplicationContext(),"You Have Been Log Out",Toast.LENGTH_LONG);
            toast.show();


        }
    }



    public void klikButton(View v) throws UnsupportedEncodingException {
        //mengambil masukan
        //untuk username
        inputNama = masukan1.getText().toString();


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(TextUtils.isEmpty(inputNama)) {
            masukan1.setError("Field Username!");
            return;
        }

        if (networkInfo != null && networkInfo.isConnected()) {

            JSONObject post_dict = new JSONObject();

            try {
                post_dict.put("username" ,inputNama );

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (post_dict.length() > 0) {
                new SendPostRequest().execute(String.valueOf(post_dict));
                Intent intent2 = new Intent(this, Main2Activity.class);
                intent2.putExtra(MainActivity.EXTRA_MESSAGE1, "You Have Been Log in.");
                startActivityForResult(intent2,ACT2_REQUEST);
                masukan1.setText("");
            }

        }else{
            // tampilkan error
            Toast t = Toast.makeText( getApplicationContext(), "Tidak ada koneksi!",Toast.LENGTH_LONG);
            t.show();
        }



    }


    public class SendPostRequest extends AsyncTask<String, Void, String> {

        private static final String TAG = "";

        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("http://makersinstitute.id:5000/users");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                //set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                
                // json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
                //input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
                //response data
                Log.i(TAG,JsonResponse);
                //send to post execute
                return JsonResponse;



            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
        }
    }


}
