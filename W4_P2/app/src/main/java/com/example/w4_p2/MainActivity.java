package com.example.w4_p2;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private EditText usdEditTxt;
    private TextView jpyTxt;
    private TextView gbpTxt;
    private TextView cadTxt;
    private TextView frfTxt;
    private GestureDetector GD;
    private static final DecimalFormat df = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usdEditTxt = (EditText)findViewById(R.id.usdEditTxt);
        jpyTxt = (TextView)findViewById(R.id.jpyTxt);
        gbpTxt = (TextView)findViewById(R.id.gbpTxt);
        cadTxt = (TextView)findViewById(R.id.cadTxt);
        frfTxt = (TextView)findViewById(R.id.frfTxt);
        GD = new GestureDetector(this, this);

        usdEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                double usdAmount;
                if (editable.toString().isEmpty()) {
                    usdAmount = 0;
                } else {
                    usdAmount = Math.max(0, Double.parseDouble(editable.toString()));
                }
                jpyTxt.setText(df.format(usdAmount * 115.56));
                gbpTxt.setText(df.format(usdAmount * 0.75));
                cadTxt.setText(df.format(usdAmount * 1.27));
                frfTxt.setText(df.format(usdAmount * 5.84));
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        String usdAmount = usdEditTxt.getText().toString();
        double usd;
        if(usdAmount.isEmpty()) {
            usd = 0;
        } else {
            usd = Double.parseDouble(usdAmount);
        }
        if (v1 > 1) {
            usdEditTxt.setText(df.format(usd + 0.1));
        } else {
            if (!usdAmount.isEmpty() && v1 < -1) {
                usdEditTxt.setText(df.format(Math.max(0, usd - 0.1)));
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        float y1 = motionEvent.getY();
        float y2 = motionEvent1.getY();
        String usdAmount = usdEditTxt.getText().toString();
        double usd;
        if(usdAmount.isEmpty()) {
            usd = 0;
        } else {
            usd = Double.parseDouble(usdAmount) ;
        }
        if (y1 > y2) {
            usdEditTxt.setText(df.format(usd + 1));
        } else {
            if (!usdAmount.isEmpty()) {
                usdEditTxt.setText(df.format(Math.max(0, usd - 1)));
            }
        }
        return true;
    }
}