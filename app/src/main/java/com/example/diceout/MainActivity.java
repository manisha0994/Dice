package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int score;
    TextView game;
   // Button roll;
    Random random;
    int die1;
    int die2;
    int die3;
    TextView scoretext;
    ArrayList<Integer> dice;
    ArrayList<ImageView> diceimageview;
    AlertDialog.Builder builder;

    ImageView imageView1,imageView2,imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                rolldice(view);
            }
        });
        score =0;

        Toast.makeText(MainActivity.this,"Welcome", Toast.LENGTH_LONG).show();
        game = findViewById(R.id.game);
        //roll = findViewById(R.id.roll);
        random = new Random();
        dice = new ArrayList<Integer>();
        diceimageview = new ArrayList<>();

        imageView1 = findViewById(R.id.image_button1);
        imageView2 = findViewById(R.id.image_button2);
        imageView3 = findViewById(R.id.image_button3);
        diceimageview.add(imageView1);
        diceimageview.add(imageView2);
        diceimageview.add(imageView3);
        scoretext = findViewById(R.id.score);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void rolldice(View view){
        game.setText("Started");
        die1 = random.nextInt(6)+1;
        die2 = random.nextInt(6)+1;
        die3 = random.nextInt(6)+1;

        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.dice_sound);
        mediaPlayer.start();

        for(int setofdice=0; setofdice<3; setofdice++){
            String imagename = "die_"+ dice.get(setofdice)+".png";
            try{
                InputStream stream = getAssets().open(imagename);
                Drawable d = Drawable.createFromStream(stream,null);
                diceimageview.get(setofdice).setImageDrawable(d);

            }catch (IOException exp){
                exp.printStackTrace();
            }
        }

        //String msg = "You rolled the dice" + die1 +",dice "+ die2 + ",dice " + die3;
        String msg;
        if(die1 == die2 && die1 == die3){
            int scorecount = die1 * 100;
            msg = "You rolled triple " + die1 +" score count" +scorecount +" points";
            score += scorecount;
        }else if(die1 == die2 || die1 == die3 || die2 == die3){
            msg = "You rolled double for 50 points";
            score +=50;
        }else{
            msg = "Try Again";
        }
        if(score >= 10000)
        {
            builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater layoutInflater = this.getLayoutInflater();
            View viewnew = layoutInflater.inflate(R.layout.item_dialog,null);
            TextView text = viewnew.findViewById(R.id.text2);
            Button button = viewnew.findViewById(R.id.ok);
            text.setText("Score is : "+score);

            builder.setView(viewnew);
            final AlertDialog alertDialog = builder.create();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        alertDialog.dismiss();
                }
            });
            alertDialog.show();
            score = 0;
        }
        //String randomValue = "roll the dice "+ die1;

        game.setText(msg);
        scoretext.setText("SCORE : "+score);
    }
}
