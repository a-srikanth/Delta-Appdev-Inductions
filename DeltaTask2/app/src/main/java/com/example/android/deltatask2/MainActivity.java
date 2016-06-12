package com.example.android.deltatask2;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int translateX = 0;
    int translateY = 0;
    int item_width = 160;
    int item_height = 160;
    String commands = "Commands:\n";

    int movable_width = Resources.getSystem().getDisplayMetrics().widthPixels;
    int movable_height = Resources.getSystem().getDisplayMetrics().heightPixels-520-96;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            commands = commands+">"+spokenText+"\n";
            TextView textView = (TextView)findViewById(R.id.textview);
            if (textView != null) {
                textView.setText(commands);
            }

            if(spokenText.equalsIgnoreCase("right") || spokenText.equalsIgnoreCase("go right")){
                moveRight();
            }
            else if(spokenText.equalsIgnoreCase("left") || spokenText.equalsIgnoreCase("go left")){
                moveLeft();
            }
            else if(spokenText.equalsIgnoreCase("up") || spokenText.equalsIgnoreCase("go up")){
                moveUp();
            }
            else if(spokenText.equalsIgnoreCase("down") || spokenText.equalsIgnoreCase("go down")){
                moveDown();
            }


            if(spokenText.equalsIgnoreCase("increase size")){
                increaseSize();
            }
            else if(spokenText.equalsIgnoreCase("decrease size")){
                decreaseSize();
            }


            if(spokenText.equalsIgnoreCase("change to circle")){
                ImageView imageView = (ImageView)findViewById(R.id.image);
                imageView.setImageResource(R.drawable.circle);
            }
            else if(spokenText.equalsIgnoreCase("change to square")){
                ImageView imageView = (ImageView)findViewById(R.id.image);
                imageView.setImageResource(R.drawable.square);
            }
            else if(spokenText.equalsIgnoreCase("change to hexagon")){
                ImageView imageView = (ImageView)findViewById(R.id.image);
                imageView.setImageResource(R.drawable.hexagon);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, 1);
    }

    public void voiceInput(View view){
        displaySpeechRecognizer();
    }

    public void moveRight(){
        if(translateX< movable_width-(item_width+10)) {
            translateX += 10;
        }else{
            translateX = movable_width-item_width;
        }
        ImageView imageView = (ImageView) findViewById(R.id.image);
        if (imageView != null) {
            imageView.setTranslationX(translateX);
        }
    }

    public void moveLeft(){
        if(translateX>10) {
            translateX -= 10;
        }else{
            translateX = 0;
        }
        ImageView imageView = (ImageView)findViewById(R.id.image);
        if (imageView != null) {
            imageView.setTranslationX(translateX);
        }
    }

    public void moveUp(){
        if(translateY>10) {
            translateY -= 10;
        }else{
            translateY = 0;
        }
        ImageView imageView = (ImageView)findViewById(R.id.image);
        if (imageView != null) {
            imageView.setTranslationY(translateY);
        }
    }

    public void moveDown(){
        if(translateY< movable_height-(item_height+10)) {
            translateY += 10;
        }else{
            translateY = movable_height-item_height;
        }
        ImageView imageView = (ImageView)findViewById(R.id.image);
        if (imageView != null) {
            imageView.setTranslationY(translateY);
        }
    }

    public void increaseSize(){
        ImageView imageView = (ImageView)findViewById(R.id.image);
        item_width = imageView.getWidth();
        item_height = imageView.getHeight();

        if(item_width<movable_width-10 && item_height<movable_height-10){
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = item_width+10;
            layoutParams.height = item_height+10;
            imageView.setLayoutParams(layoutParams);
            item_width+=10;
            item_height+=10;
        }
    }

    public void decreaseSize(){
        ImageView imageView = (ImageView)findViewById(R.id.image);
        item_width = imageView.getWidth();
        item_height = imageView.getHeight();

        if(item_width>10 && item_height>10){
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = item_width-10;
            layoutParams.height = item_height-10;
            imageView.setLayoutParams(layoutParams);
            item_width-=10;
            item_height-=10;
        }
    }

}
