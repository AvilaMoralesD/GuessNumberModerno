package com.dami.guessnumbermoderno;

import android.util.Log;

import androidx.databinding.InverseMethod;

public class Converter {

    public static String intToString(int value) {
        return String.valueOf(value);
    }

    @InverseMethod("intToString")
    public static int stringToInt(String str) {
        try {
            if (str == null) return 0;
            return str.length() == 0 ? 0 : Integer.parseInt(str);
        } catch (Exception e) {
            Log.d("ERROR", "Ocurrió un error con el adapter");
            return 0;
        }
    }
}

