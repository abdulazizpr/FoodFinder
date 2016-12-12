package com.example.android.findfood;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Achievement extends AppCompatActivity {

    public static final String EXTRA_MESSAGE2 = "badges";
    int hitung=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        //untuk toolbar
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar2);
        getSupportActionBar().setTitle("Achievement");

        //notif
        //ambil intent
        Intent intent = getIntent();
        //ambil datanya
        String pesan = intent.getStringExtra(Main2Activity.EXTRA_MESSAGE2);
        //tampilkan toast
        Toast t = Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_LONG);
        t.show();

        hitungBadges();
    }

    //tollbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mBack:
                klikKembali();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void klikKembali() {
        Intent intent2 = getIntent();
        intent2.putExtra(Main2Activity.EXTRA_MESSAGE2,"");
        setResult(RESULT_OK, intent2);
        finish();


    }


    public void hitungBadges(){
        if (hitung == 5){
            ImageView img = (ImageView) findViewById(R.id.badge2);
            img.setImageResource(R.drawable.cake);
        }

        else if (hitung == 10){
            ImageView img = (ImageView) findViewById(R.id.badge2);
            img.setImageResource(R.drawable.egg);
        }

        else if (hitung == 15){
            ImageView img = (ImageView) findViewById(R.id.badge2);
            img.setImageResource(R.drawable.chicken);
        }

        else if (hitung == 20){
            ImageView img = (ImageView) findViewById(R.id.badge2);
            img.setImageResource(R.drawable.burger);
        }

        else if (hitung == 25){
            ImageView img = (ImageView) findViewById(R.id.badge2);
            img.setImageResource(R.drawable.pizza);
        }

    }

    public void klikBadges1(View v){
        new AlertDialog.Builder(this)
                .setTitle("First Eat")
                .setMessage(Html.fromHtml("This is Your First Achievemennt! Visit More Places To Get Another Badges."+"\n"+"<b>"+"Your Current Point : "+hitung+"</b>"))
                .setNegativeButton("Ok", null)
                .show();
    }

    public void klikBadges2(View v){
        new AlertDialog.Builder(this)
                .setTitle("Sweet Cupcake")
                .setMessage(Html.fromHtml("You Must Get 5 Points to Unlock This Achievement!"+"\n"+"<b>"+"Your Current Point : "+hitung+"</b>"))
                .setNegativeButton("Ok", null)
                .show();
    }

    public void klikBadges3(View v){
        new AlertDialog.Builder(this)
                .setTitle("Lovely Egg")
                .setMessage(Html.fromHtml("You Must Get 10 Points to Unlock This Achievement!"+"\n"+"<b>"+"Your Current Point : "+hitung+"</b>"))
                .setNegativeButton("Ok", null)
                .show();
    }

    public void klikBadges4(View v){
        new AlertDialog.Builder(this)
                .setTitle("Perfecto Chicken")
                .setMessage(Html.fromHtml("You Must Get 15 Points to Unlock This Achievement!"+"\n"+"<b>"+"Your Current Point : "+hitung+"</b>"))
                .setNegativeButton("Ok", null)
                .show();
    }

    public void klikBadges5(View v){
        new AlertDialog.Builder(this)
                .setTitle("Super Burger")
                .setMessage(Html.fromHtml("You Must Get 20 Points to Unlock This Achievement!"+"\n"+"<b>"+"Your Current Point : "+hitung+"</b>"))
                .setNegativeButton("Ok", null)
                .show();
    }

    public void klikBadges6(View v){
        new AlertDialog.Builder(this)
                .setTitle("Yummy Pizza")
                .setMessage(Html.fromHtml("You Must Get 25 Points to Unlock This Achievement!"+"\n"+"<b>"+"Your Current Point : "+hitung+"</b>"))
                .setNegativeButton("Ok", null)
                .show();
    }


}



