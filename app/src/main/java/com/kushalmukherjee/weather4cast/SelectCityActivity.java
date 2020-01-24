package com.kushalmukherjee.weather4cast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import jp.wasabeef.blurry.Blurry;

public class SelectCityActivity extends AppCompatActivity {


    EditText enterCityText;
    Button currentLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        getSupportActionBar().hide();

        ImageView backgroundImage = findViewById(R.id.backgroundImage);
        Blurry.with(this).from(BitmapFactory.decodeResource(getResources(),R.drawable.weather4castimage)).into(backgroundImage);


        enterCityText = (EditText) findViewById(R.id.enterCityPlainText);
        currentLocationButton = (Button) findViewById(R.id.currentLocationButton);

        enterCityText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if(actionId == EditorInfo.IME_ACTION_DONE) {

                    Intent intent = new Intent();
                    intent.putExtra("city",v.getText().toString());

                    setResult(Activity.RESULT_OK, intent);

                    finish();

                }
                return false;
            }
        });


        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.putExtra("city","currentLocation");
                setResult(Activity.RESULT_OK,intent);
                finish();


            }
        });


        Intent intent = getIntent();
    }


}
