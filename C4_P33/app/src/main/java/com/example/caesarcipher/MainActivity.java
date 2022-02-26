package com.example.caesarcipher ;

import androidx.appcompat.app.AppCompatActivity ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//    String word;
    int shift_value = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText word = findViewById(R.id.word);

        TextView res = findViewById(R.id.res);

        Button button = findViewById(R.id.button);

        Button change = findViewById(R.id.change);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String output = Cipher.cipher(word.getText().toString(), shift_value);
                res.setText(output);

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), UserShift.class );
                startActivity(i);

            }
        });

    }
}

