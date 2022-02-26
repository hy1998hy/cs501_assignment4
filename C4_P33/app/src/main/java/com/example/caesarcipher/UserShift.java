package com.example.caesarcipher ;

import androidx.appcompat.app.AppCompatActivity ;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserShift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_shift_layout);

        EditText word = findViewById(R.id.word);
        EditText shift_value = findViewById(R.id.shift_value);

        TextView res = findViewById(R.id.res);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String output = Cipher.cipher(word.getText().toString(), Integer.valueOf(shift_value.getText().toString()));
                res.setText(output);

            }
        });

    }
}

