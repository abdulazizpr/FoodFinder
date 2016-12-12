package com.example.android.findfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        //list
        //inisiasi isi
        items.add("satu");
        items.add("dua");
        items.add("tiga");
        items.add("empat");
        items.add("lima");

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
}
