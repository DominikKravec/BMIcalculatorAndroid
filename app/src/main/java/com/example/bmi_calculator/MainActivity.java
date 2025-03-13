package com.example.bmi_calculator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText fnameInput;
    private EditText lnameInput;
    private EditText weightInput;
    private SeekBar weightInputSlider;
   // private TextView weightTv;
    private EditText heightInput;
    private SeekBar heightInputSlider;
    private EditText ageInput;
    private RadioButton maleButton;
    private RadioButton femaleButton;

    private Button calculateBt;
    private String gender = "";

    private float weight = 0;
    private float height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fnameInput = findViewById(R.id.fname_input);
        lnameInput = findViewById(R.id.lname_input);
        weightInput = findViewById(R.id.weight_input);
        weightInputSlider = findViewById(R.id.weight_input_slider);
        heightInput = findViewById(R.id.height_input);
        heightInputSlider = findViewById(R.id.height_input_slider);
        ageInput = findViewById(R.id.age_input);
        maleButton = findViewById(R.id.male_radio);
        femaleButton = findViewById(R.id.female_radio);
        calculateBt = findViewById(R.id.calculate_bt);
        //weightTv = findViewById(R.id.weight_tv);

        heightInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    height = Float.parseFloat(heightInput.getText().toString());
                    heightInputSlider.setProgress((int)((height / 300) * 100));
                } catch (NumberFormatException e) {

                }
            }
        });

        weightInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    weight = Float.parseFloat(weightInput.getText().toString());
                    weightInputSlider.setProgress((int)((weight / 300) * 100));
                } catch (NumberFormatException e) {

                }
            }
        });


        weightInputSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts touching the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user stops touching the SeekBar
                Log.i("DEBUG", weightInputSlider.getProgress() + "");
                weight = (weightInputSlider.getProgress()) * 3;
                weightInput.setText(weight + "");
            }
        });

        heightInputSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.i("DEBUG", progress + "");


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts touching the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user stops touching the SeekBar
                height = (heightInputSlider.getProgress()) * 3;
                heightInput.setText(height + "");
            }
        });

        maleButton.setOnCheckedChangeListener((view, isChecked) -> {
            if(isChecked){
                gender = "male";
            }

            Log.i("genderDebug", gender);

        });

        femaleButton.setOnCheckedChangeListener((view, isChecked) -> {
            if(isChecked){
                gender = "female";
            }
            Log.i("genderDebug", gender);
        });


        calculateBt.setOnClickListener(view -> {


            try {
                if(!fnameInput.getText().toString().equals("") && !lnameInput.getText().toString().equals("") && weight > 0 && height > 0 && !ageInput.getText().toString().equals("") && !gender.isEmpty()){
                    if(Float.parseFloat(heightInput.getText().toString()) >= 150 && Integer.parseInt(ageInput.getText().toString()) >= 12){
                        //calculate BMI
                        Person person = new Person(fnameInput.getText().toString(), lnameInput.getText().toString(), height, weight, Integer.parseInt(ageInput.getText().toString()), gender);
                        String serializationFileName = "serializationFile.txt";

                        try
                        {
                            //Saving of object in a file
                            FileOutputStream file = this.openFileOutput(serializationFileName, Context.MODE_PRIVATE);;
                            ObjectOutputStream out = new ObjectOutputStream(file);

                            // Method for serialization of object
                            out.writeObject(person);

                            out.close();
                            file.close();

                            Log.i("SERIALIZATION", "Object has been serialized");
                            Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                            i.putExtra("serializationFileName", serializationFileName);
                            startActivity(i);
                        }

                        catch(IOException ex)
                        {
                            Log.i("SERIALIZATION", "Error serializing: " + ex.toString());
                        }


                    }else{
                        //tell user you cant calculate their BMI
                        if(Float.parseFloat(heightInput.getText().toString()) < 150){
                            Toast.makeText(this, R.string.height_error_message, Toast.LENGTH_SHORT).show();
                        }
                        if( Integer.parseInt(ageInput.getText().toString()) < 12){
                            Toast.makeText(this, R.string.age_error_message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(this, R.string.empty_field_message, Toast.LENGTH_SHORT).show();
                }
            }catch(Exception error){
                //check for errors
                Toast.makeText(this, R.string.invalid_input_message, Toast.LENGTH_SHORT).show();
                Log.i("DEBUG", "Error: " + error);
            }
        });

    }
}