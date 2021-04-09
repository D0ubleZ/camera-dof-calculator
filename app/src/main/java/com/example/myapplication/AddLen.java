package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Model.Lens;
import com.example.myapplication.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddLen extends AppCompatActivity {

    public static Intent makelunchintent(Context c, int pos){
        Intent intent = new Intent(c,AddLen.class);
        intent.putExtra("position", pos);
        return intent;
    }
    private EditText inputMake;
    private EditText inputfocal;
    private EditText inputAperture;
    private EditText inputIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_len);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupSaveButton();
        setupCancelButton();
    }

    private void setupCancelButton() {
        Button save = (Button) findViewById(R.id.cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void setupSaveButton() {
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMake = findViewById(R.id.inputMake);
                inputfocal = findViewById(R.id.inputfocal);
                inputAperture = findViewById(R.id.inputAperture);
                inputIcon = findViewById(R.id.inputIcon);

                String make = inputMake.getText().toString();
                int focal = Integer.parseInt(inputfocal.getText().toString());
                double aperture = Double.parseDouble(inputAperture.getText().toString());
                int icon = Integer.parseInt(inputIcon.getText().toString());

                if(make.length() <= 0 || focal <= 0 || aperture < 1.4){
                    String message = "(make sould valid string, focal should > 0, aperture should >= 1.4)";
                    Toast.makeText(AddLen.this,message,Toast.LENGTH_LONG).show();
                }
                else {
                    LensManager lenses = LensManager.getInstance();
                    Intent intent = getIntent();
                    int pos = intent.getIntExtra("position",0);
                    if (pos != -1){
                        lenses.remove(pos);
                        if (icon == 1) {
                            icon = R.drawable.ic_baseline_brightness_2_24;
                        } else if (icon == 2) {
                            icon = R.drawable.ic_baseline_brightness_4_24;
                        } else if (icon == 3) {
                            icon = R.drawable.ic_baseline_brightness_5_24;
                        } else if (icon == 4) {
                            icon = R.drawable.ic_baseline_brightness_7_24;
                        } else {
                            icon = R.drawable.ic_baseline_brightness_medium_24;
                        }

                        lenses.add(new Lens(make, aperture, focal, icon));
                        Intent i = new Intent(AddLen.this, MainActivity.class);
                        startActivity(i);
                    }else {
                        if (icon == 1) {
                            icon = R.drawable.ic_baseline_brightness_2_24;
                        } else if (icon == 2) {
                            icon = R.drawable.ic_baseline_brightness_4_24;
                        } else if (icon == 3) {
                            icon = R.drawable.ic_baseline_brightness_5_24;
                        } else if (icon == 4) {
                            icon = R.drawable.ic_baseline_brightness_7_24;
                        } else {
                            icon = R.drawable.ic_baseline_brightness_medium_24;
                        }

                        lenses.add(new Lens(make, aperture, focal, icon));

                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
            }
        });
    }
}