package com.example.w4_p5;

import java.util.Random;

public class Hangman {
    public static int max_length = 6;
    private String [] words = {"TAX", "APPLE", "JAVA", "MARCH", "AMAZON"};
    private String word;        // chosen word
    private boolean[] indexesstatus;       // status of every location
    private int totalchance;         // number of chances in total
    private int leftchance;            // number of chances left

    public Hangman(int guesses){
        if(guesses > 0){
            totalchance = guesses;
        }
        else{
            totalchance = max_length;
        }
        leftchance = totalchance;
        Random random = new Random();
        int index = random.nextInt(words.length);
        word = words[index];
        indexesstatus = new boolean[word.length()];
    }
    public Hangman(int guesses, String chosenword, boolean[] oldindexes){
        if(guesses > 0){
            totalchance = guesses;
        }
        else{
            totalchance = max_length;
        }
        leftchance = totalchance;
        word = chosenword;
        indexesstatus = oldindexes;
    }
    public int getWordLength(){
        return word.length();
    }
    public int getTotalchance(){
        return totalchance;
    }
    public int getLeftchance(){
        return leftchance;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean[] getIndexesstatus() {
        return indexesstatus;
    }

    public void setIndexesstatus(boolean[] indexesstatus) {
        this.indexesstatus = indexesstatus;
    }

    // check if the letter is part of the word
    public boolean check(char c){
        for(int i = 0; i < word.length(); i++){
            if (c == word.charAt(i))
                return true;
        }
        return false;
    }
    // play the game
    public boolean guess(char c){
        boolean correct = false;
        for (int i = 0; i < word.length(); i++){
            if (!indexesstatus[i] && c == word.charAt(i)){
                indexesstatus[i] = true;
                correct = true;
            }
        }
        if (!correct){
            leftchance--;
            return false;
        }
        else {
            return true;
        }
    }
    // get the latest status of word
    public String currentWordStatus(){
        String current = "";
        for (int i = 0; i < word.length(); i++){
            if (indexesstatus[i])
                current += word.charAt(i) + " ";
            else
                current += "_ ";
        }
        return current;
    }
    public int isOver(){
        boolean won = true;
        for (int i = 0; i < indexesstatus.length; i++){
            if(!indexesstatus[i]){
                won = false;
                break;
            }
        }
        if (won){
            return 1;
        }
        // fail
        else if (leftchance == 0){
            return -1;
        }
        else{return 0;}
    }
}
