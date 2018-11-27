package com.alirezaafkar.sundatepicker.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TextAndFontUtility {

    public static void setFonts(View v, Typeface typeface) {
        if (typeface != null && v != null) {
            if (v instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                    setFonts(((ViewGroup) v).getChildAt(i), typeface);
                }
            } else if (v instanceof Button) {
                Button e = (Button) v;
                e.setTypeface(typeface);
            } else if (v instanceof TextView) {
                TextView e = (TextView) v;
                e.setTypeface(typeface);
            }
        }
    }

    public static String toPersianNumber(String text) {
        if (text == null)
            return "";
        String out = "";
        String[] persianNumbers = new String[]{ "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };
        String[] englishNumbers = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        out = text;
        for (int i = 0; i < persianNumbers.length; i++) {
            out = out.replaceAll(englishNumbers[i], persianNumbers[i]);

        }

        return out;
    }

    public static String toEnglishNumber(String text) {
        if (text == null)
            return "";
        String out = "";
        String[] persianNumbers = new String[]{ "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };
        String[] englishNumbers = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        out = text;
        for (int i = 0; i < persianNumbers.length; i++) {
            out = out.replaceAll(persianNumbers[i], englishNumbers[i]);

        }

        return out;
    }
}
