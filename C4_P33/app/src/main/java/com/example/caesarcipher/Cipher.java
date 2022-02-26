package com.example.caesarcipher;

public class Cipher {

    //
    public static String cipher(String input, int shift) {
        char[] array = input.toCharArray();
        for (int i = 0; i <= array.length - 1; i++) {
            int current = array[i] - 'a';
            System.out.println(current);

            current = (current + shift) % 26;

            array[i] = (char) (current + 'a');
        }

        return new String(array);
    }
}
//    public static void main(String ar[]){
//        System.out.println(cipher("hello", 3));
//    }
//}


