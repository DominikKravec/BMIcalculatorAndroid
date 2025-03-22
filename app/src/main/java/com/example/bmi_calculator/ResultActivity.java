package com.example.bmi_calculator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    private TextView fnameTv, lnameTv, heightTv, weightTv, ageTv, bmiTv;
    private SeekBar resultBar;
    private Button backBt, videoBt;
    private LinearLayout bmi_result_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fnameTv = findViewById(R.id.fname_tv);
        lnameTv = findViewById(R.id.lname_tv);
        heightTv = findViewById(R.id.height_tv);
        weightTv = findViewById(R.id.weight_tv);
        ageTv = findViewById(R.id.age_tv);
        resultBar = findViewById(R.id.result_bar);
        bmiTv = findViewById(R.id.bmi_tv);
        backBt = findViewById(R.id.back_bt);
        videoBt = findViewById(R.id.video_bt);
        bmi_result_view = findViewById(R.id.bmi_result_view);

        Intent intent = getIntent();

        String serializationFileName = intent.getStringExtra("serializationFileName");
        Person person;

        try {
            FileInputStream fis = this.openFileInput(serializationFileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            person = (Person) is.readObject();
            is.close();
            fis.close();
            Log.i("SERIALIZATION", "Object deserialized");

            fnameTv.setText(getString(R.string.fname, person.getFname()));
            lnameTv.setText(getString(R.string.lname, person.getLname()));
            weightTv.setText(getString(R.string.weight, person.getWeight() + ""));
            heightTv.setText(getString(R.string.height, person.getHeight() + ""));
            ageTv.setText(getString(R.string.age, person.getAge() + ""));

            Double bmi = BMICalculator.calculate(person.getWeight(), person.getHeight());

            bmiTv.setText("BMI: " + new DecimalFormat("#.#").format(bmi));

            if(bmi > 30){
                bmi_result_view.removeView(resultBar);
                SeekBar newSeek = new SeekBar(new ContextThemeWrapper(this, R.style.MySeekBarBad));
                newSeek.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                newSeek.setProgress((int) ((bmi / 50) * 100));
                newSeek.setEnabled(false);
                bmi_result_view.addView(newSeek);
            }else if(bmi > 22){
                bmi_result_view.removeView(resultBar);
                SeekBar newSeek = new SeekBar(new ContextThemeWrapper(this, R.style.MySeekBarMedium));
                newSeek.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                newSeek.setProgress((int) ((bmi / 50) * 100));
                newSeek.setEnabled(false);
                bmi_result_view.addView(newSeek);
            }else {
                resultBar.setProgress((int) ((bmi / 50) * 100));
                resultBar.setEnabled(false);
            }

            Log.i("result", ( (bmi / 40) * 100) + "");

            Log.i("result", bmi + "");
        } catch (Exception e) {
            Log.i("SERIALIZATION", "Error reading object: " + e);

        }

        videoBt.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            startActivity(i);
        });

        backBt.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }
}