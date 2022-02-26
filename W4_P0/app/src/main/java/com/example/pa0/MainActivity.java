package com.example.pa0;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private LinearLayout LLMain;
    private LinearLayout.LayoutParams LLP;
    private GestureDetector GD;
    boolean hasFlash=false;
    boolean flishState = false;
    Switch aSwitch;
    EditText editText;
    TextView textView;
    CameraManager cameraManager;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GD = new GestureDetector(this,this);
        //Create the viewgroup
        LLMain = new LinearLayout(MainActivity.this);
        LLMain.setOrientation(LinearLayout.VERTICAL);
        LLP=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LLP.gravity= Gravity.CENTER_VERTICAL;
        LLP.setMargins(5,5,5,5);


//5: Render the View Group with it's contents.  (instead of the usual call to SetContentView)
        hasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        aSwitch = new Switch(getBaseContext());
        aSwitch.setLayoutParams(LLP);
        LLMain.addView(aSwitch);
        editText = new EditText(getBaseContext());
        editText.setText("Off");
        editText.setLayoutParams(LLP);
        LLMain.addView(editText);
        textView = new TextView(getBaseContext());
        textView.setText("Status:Off");
        textView.setLayoutParams(LLP);
        LLMain.addView(textView);
        this.addContentView(LLMain, LLP);   //Q: Wait a minute, I thought LLP was for the views, not the view group.  What's different this time? A: _______

//MISC...
        System.out.println(hasFlash);
        setaSwitch(aSwitch);
       //boolean boo=editText.getText().toString().equals("Off");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            if(editText.getText().toString().equals("On")){
                aSwitch.setChecked(true);
                textView.setText("Status:On");
            }else if(editText.getText().toString().equals("Off")){
                aSwitch.setChecked(false);
                textView.setText("Status:Off");
            }else{
                textView.setText("Invalid input! Valid input On/Off");


            }
            }
        });

    }

    public void setaSwitch(Switch aSwitch){
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cameraManager =(CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        cameraManager.setTorchMode("0",true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    editText.setText("On");

                }else{
                    cameraManager =(CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        cameraManager.setTorchMode("0",false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    editText.setText("Off");

                }

            }
        });

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
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        float diffY = motionEvent1.getY()-motionEvent.getY();
        if(Math.abs(diffY)>100 && Math.abs(v1)>100){
            if(diffY<0){
                aSwitch.setChecked(true);
            }else if(diffY>0){
                aSwitch.setChecked(false);
            }
        }

        return false;
    }
}