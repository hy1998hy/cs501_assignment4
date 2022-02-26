package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout LLMain;
    private LinearLayout.LayoutParams LLMP;
    private LinearLayout LLInput;
    private ViewGroup.LayoutParams LLIP;
    private LinearLayout LLHint;
    private LinearLayout.LayoutParams LLHP;
    private GridLayout GL;
    private GridLayout.LayoutParams GLP;
    private ImageView pics;
    private TextView selecthint;
    private TextView game_status;
    Button[] btnLetters;
    private Button restart;
    private Button gethint;
    private int clickcount;
    private TextView wordhint;
    private boolean[] oldindexstatus;
    private String oldword;
    private int res;
    private CharSequence hint;
    private int hintsecondcount = -1;
    private int secondcount = -1;


    private Hangman hangman;
    private int imagephase = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // View issues(portrait)
            setContentView(R.layout.activity_main);
            LLMain = (LinearLayout) findViewById(R.id.LLMain);
            LLHint = (LinearLayout) findViewById(R.id.LLHint);
            game_status = (TextView) findViewById(R.id.game_status);
            pics = (ImageView)findViewById(R.id.IMGdisplay);
            selecthint = (TextView)findViewById(R.id.selecthint);

            GL = (GridLayout) findViewById(R.id.GLLetters);
            if (btnLetters == null){
                btnLetters = new Button[26];
                for (int i = 0; i < 26; i++) {
                    btnLetters[i] = new Button(this);
                    btnLetters[i].setId((i + 1) * 2893);
                    btnLetters[i].setText(Character.toString((char) (i + 65)));
                    btnLetters[i].setTextSize(20);
                    btnLetters[i].setMinimumHeight(5);
                    btnLetters[i].setMinimumWidth(5);
                    btnLetters[i].setTag(i);

                    GLP = new GridLayout.LayoutParams();
                    GLP.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    GLP.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
//                GLP.columnSpec = spec;
                    GLP.setGravity(Gravity.CENTER);
                    btnLetters[i].setLayoutParams(GLP);
                    GL.addView(btnLetters[i]);

                }
            }

        }
        else{
            // View issues(landscape)
            setContentView(R.layout.activity_main);
            LLMain = (LinearLayout) findViewById(R.id.LLMain);
            LLHint = (LinearLayout) findViewById(R.id.LLHint);
            game_status = (TextView) findViewById(R.id.game_status);
            pics = (ImageView)findViewById(R.id.IMGdisplay);
            selecthint = (TextView)findViewById(R.id.selecthint);
            wordhint = (TextView)findViewById(R.id.wordhint);
            gethint = (Button) findViewById(R.id.gethint);

            GL = (GridLayout) findViewById(R.id.GLLetters);
            if (btnLetters == null){
                btnLetters = new Button[26];
                for (int i = 0; i < 26; i++) {
                    btnLetters[i] = new Button(this);
                    btnLetters[i].setId((i + 1) * 2893);
                    btnLetters[i].setText(Character.toString((char) (i + 65)));
                    btnLetters[i].setTextSize(20);
                    btnLetters[i].setMinimumHeight(5);
                    btnLetters[i].setMinimumWidth(5);
                    btnLetters[i].setTag(i);

                    GLP = new GridLayout.LayoutParams();
                    GLP.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    GLP.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
//                GLP.columnSpec = spec;
                    GLP.setGravity(Gravity.CENTER);
                    btnLetters[i].setLayoutParams(GLP);
                    GL.addView(btnLetters[i]);

                }
            }

        }
//        for (int i = 0; i < 26; i++){System.out.println(btnLetters[i].getId());}
//        System.out.println(GL.getId());

        // Restore the information from the bundle
        if (savedInstanceState != null){
            for (int i = 0; i < 26; i++){
                btnLetters[i].setEnabled(savedInstanceState.getBoolean("button" + i));
            }
            game_status.setText(savedInstanceState.getCharSequence("status"));
            selecthint.setText(savedInstanceState.getCharSequence("selecthint"));
            oldword = savedInstanceState.getString("game");
            oldindexstatus = savedInstanceState.getBooleanArray("indexguessed");
            res = savedInstanceState.getInt("result");
            imagephase = savedInstanceState.getInt("imgphase");
            clickcount = savedInstanceState.getInt("clickcount");
            if (savedInstanceState.getCharSequence("wordhint") != null){
                hint = savedInstanceState.getCharSequence("wordhint");
            }
            if (savedInstanceState.getInt("hintsecondcount") != -1){
                secondcount = savedInstanceState.getInt("hintsecondcount");
            }
        }
        if (oldword == null){
            hangman = new Hangman(Hangman.max_length);
            oldword = hangman.getWord();
            oldindexstatus = hangman.getIndexesstatus();
        }
        else{hangman = new Hangman(Hangman.max_length, oldword, oldindexstatus);}
        game_status.setText(hangman.currentWordStatus());
        if(gethint != null && hint != null)
            wordhint.setText(hint);
        if (secondcount != 0)
            hintsecondcount = secondcount;
        if (res == 1){
            for (int i = 0; i < 26; i++){
                btnLetters[i].setEnabled(false);
            }
            clickcount = 3;
            game_status.setText(R.string.won);
            ViewGroup parent = (ViewGroup) selecthint.getParent();
            parent.removeView(selecthint);
            restart = new Button(MainActivity.this);
            restart.setText(R.string.restart);
            restart.setTextSize(15);
            restart.setBackgroundColor(getResources().getColor(R.color.green));
            LLHP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LLHP.weight = 1.0f;
            LLHP.gravity = Gravity.CENTER;
            restart.setLayoutParams(LLHP);
            LLHint.addView(restart);
//                                selecthint.setText(R.string.over);
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // re-initiate the game
                    hangman = new Hangman(Hangman.max_length);
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    String time = System.currentTimeMillis() + "";
                }
            });
        }
        if (imagephase == 7) {
            for (int i = 0; i < 26; i++){
                btnLetters[i].setEnabled(false);
            }
            clickcount = 3;
            game_status.setText(R.string.lost);
            ViewGroup parent = (ViewGroup) selecthint.getParent();
            parent.removeView(selecthint);
            restart = new Button(MainActivity.this);
            restart.setText(R.string.restart);
            restart.setTextSize(15);
            restart.setBackgroundColor(getResources().getColor(R.color.deepskyblue));
            LLHP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LLHP.weight = 1.0f;
            LLHP.gravity = Gravity.CENTER;
            restart.setLayoutParams(LLHP);
            LLHint.addView(restart);
            restart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hangman = new Hangman(Hangman.max_length);
                    finish();
                    startActivity(getIntent());
                    // this basically provides animation
                    overridePendingTransition(0, 0);
                    String time = System.currentTimeMillis() + "";
                }
            });}
        // keep the state of image when rotating
        updateImage(imagephase);

        // Implementation of HINT functions
        if (gethint != null){
            gethint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickcount == 0){
                        switch (oldword){
                            case "TAX":
                                wordhint.setText("MONEY");
                                break;
                            case "APPLE":
                                wordhint.setText("FOOD");
                                break;
                            case "JAVA":
                                wordhint.setText("CODE");
                                break;
                            case "AMAZON":
                                wordhint.setText("SHOP");
                                break;
                            case "MARCH":
                                wordhint.setText("MONTH");
                                break;
                        }
                        clickcount += 1;
                    }
                    else if (imagephase == 6){
                        Toast.makeText(MainActivity.this, "Hint not available", Toast.LENGTH_LONG).show();
                    }
                    else if (clickcount == 1){
                        hintsecondcount = 26;
                        for (int i = 0; i < 26; i++){
                            if (hangman.check(btnLetters[i].getText().toString().charAt(0)) && !btnLetters[i].isEnabled()){
                                hintsecondcount -= 1;
                            }
                            else if (hangman.check(btnLetters[i].getText().toString().charAt(0))){
                                hintsecondcount -= 1;
                            }
                        }
                        hintsecondcount = hintsecondcount / 2;
                        int j = 0;
                        while(hintsecondcount >= 0 && j < 26){
                            if(hangman.check(btnLetters[j].getText().toString().charAt(0)) && !btnLetters[j].isEnabled())
                                j += 1;
                            else if(hangman.check(btnLetters[j].getText().toString().charAt(0)) || !btnLetters[j].isEnabled())
                                j += 1;
                            else{
                                btnLetters[j].setEnabled(false);
                                j += 1;
                                hintsecondcount -= 1;
                            }
                        }
                        imagephase += 1;
                        updateImage(imagephase);
                        clickcount += 1;
                    }
                    else if (clickcount == 2){
                        for (int i = 0; i < 26; i++){
                            if (!btnLetters[i].isEnabled())
                                continue;
                            else if ((btnLetters[i].getText().toString().equalsIgnoreCase("A") && hangman.check(btnLetters[i].getText().toString().charAt(0))) || (btnLetters[i].getText().toString().equalsIgnoreCase("E") && hangman.check(btnLetters[i].getText().toString().charAt(0))) ||
                                    (btnLetters[i].getText().toString().equalsIgnoreCase("I") && hangman.check(btnLetters[i].getText().toString().charAt(0))) || (btnLetters[i].getText().toString().equalsIgnoreCase("O") && hangman.check(btnLetters[i].getText().toString().charAt(0))) ||
                                    (btnLetters[i].getText().toString().equalsIgnoreCase("U") && hangman.check(btnLetters[i].getText().toString().charAt(0)))){
                                hangman.guess(btnLetters[i].getText().toString().charAt(0));
                                btnLetters[i].setEnabled(false);
                            }
                        }
                        game_status.setText(hangman.currentWordStatus());
                        imagephase += 1;
                        updateImage(imagephase);
                        clickcount += 1;
                    }
                    else if (clickcount == 3){
                        Toast.makeText(MainActivity.this, "Hint not available", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        // Main process of play
        for (int i = 0; i < 26; i++){
            int finalI = i;
            btnLetters[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String input = btnLetters[finalI].getText().toString();
                    // guess the correct letter
                    if (hangman.guess(input.charAt(0))){
                        //TODO: disable the button, update textview (finished)
                        btnLetters[finalI].setEnabled(false);
                        game_status.setText(hangman.currentWordStatus());
                        res = hangman.isOver();
                        if (res == 1){
                            //TODO: won
                            for (int i = 0; i < 26; i++){
                                btnLetters[i].setEnabled(false);
                            }
                            clickcount = 3;
                            game_status.setText(R.string.won);
                            ViewGroup parent = (ViewGroup) selecthint.getParent();
                            parent.removeView(selecthint);
                            restart = new Button(MainActivity.this);
                            restart.setText(R.string.restart);
                            restart.setTextSize(15);
                            restart.setBackgroundColor(getResources().getColor(R.color.green));
                            LLHP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LLHP.weight = 1.0f;
                            LLHP.gravity = Gravity.CENTER;
                            restart.setLayoutParams(LLHP);
                            LLHint.addView(restart);
//                                selecthint.setText(R.string.over);
                            restart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    hangman = new Hangman(Hangman.max_length);
                                    // re-initiate the game
                                    finish();
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    String time = System.currentTimeMillis() + "";
                                }
                            });
                        }
                    }
                    // guess the wrong letter
                    else{
                        btnLetters[finalI].setEnabled(false);
                        imagephase += 1;
                        updateImage(imagephase);
                        if (imagephase == 7){
                            // lost
                            for (int i = 0; i < 26; i++){
                                btnLetters[i].setEnabled(false);
                            }
                            clickcount = 3;
                            game_status.setText(R.string.lost);
                            ViewGroup parent = (ViewGroup) selecthint.getParent();
                            parent.removeView(selecthint);
                            restart = new Button(MainActivity.this);
                            restart.setText(R.string.restart);
                            restart.setTextSize(15);
                            restart.setBackgroundColor(getResources().getColor(R.color.deepskyblue));
                            LLHP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            LLHP.weight = 1.0f;
                            LLHP.gravity = Gravity.CENTER;
                            restart.setLayoutParams(LLHP);
                            LLHint.addView(restart);
//                                selecthint.setText(R.string.over);
                            restart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // re-initiate the game
                                    hangman = new Hangman(Hangman.max_length);
                                    finish();
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    String time = System.currentTimeMillis() + "";
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    public Hangman getGame(){
        return hangman;
    }


    public void updateImage(int x){
        switch (x){
            case 1:
                pics.setImageResource(R.drawable.phase1);
                break;
            case 2:
                pics.setImageResource(R.drawable.phase2);
                break;
            case 3:
                pics.setImageResource(R.drawable.phase3);
                break;
            case 4:
                pics.setImageResource(R.drawable.phase4);
                break;
            case 5:
                pics.setImageResource(R.drawable.phase5);
                break;
            case 6:
                pics.setImageResource(R.drawable.phase6);
                break;
            case 7:
                pics.setImageResource(R.drawable.phase7);
                break;
        }
    }

    //
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        // TODO: Save image, word, status of buttons (finished)
//        savedInstanceState.putSerializable("allbuttons", btnLetters);
        savedInstanceState.putCharSequence("status", game_status.getText());
        savedInstanceState.putCharSequence("selecthint", selecthint.getText());
        for (int i = 0; i < 26; i++){
            savedInstanceState.putBoolean("button"+ i , btnLetters[i].isEnabled());
        }
        // first rotation from landscape to portrait
        if (wordhint != null ){
            savedInstanceState.putCharSequence("wordhint", wordhint.getText());
        }
        // second rotation from portrait to landscape
        if (hint != null){
            savedInstanceState.putCharSequence("wordhint", hint);
        }
        if (hintsecondcount != -1){
            savedInstanceState.putInt("hintsecondcount", hintsecondcount);
        }
        if (secondcount != -1){
            savedInstanceState.putInt("hintsecondcount", secondcount);
        }
        savedInstanceState.putBooleanArray("indexguessed", hangman.getIndexesstatus());
        savedInstanceState.putString("game", hangman.getWord());
        savedInstanceState.putInt("result", res);
        savedInstanceState.putInt("imgphase", imagephase);
        savedInstanceState.putInt("clickcount", clickcount);
        super.onSaveInstanceState(savedInstanceState);
     }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
////        btnLetters = (Button[])savedInstanceState.getSerializable("allbuttons");
//        for (int i = 0; i < 26; i++){
//            btnLetters[i].setEnabled(savedInstanceState.getBoolean("button" + i));
//        }
//        game_status.setText(savedInstanceState.getCharSequence("status"));
//        selecthint.setText(savedInstanceState.getCharSequence("selecthint"));
//        oldword = savedInstanceState.getString("game");
//        oldindexguessed = savedInstanceState.getBooleanArray("indexguessed");
//        System.out.println(oldword);
////        if (wordhint == null) wordhint.setText(savedInstanceState.getCharSequence("wordhint"));
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}