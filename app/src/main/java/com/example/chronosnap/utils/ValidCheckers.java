package com.example.chronosnap.utils;

import android.text.TextUtils;

public class ValidCheckers {

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isValidPassword(CharSequence target) {
        return !TextUtils.isEmpty(target) && target.length() >= 8 && target.length() <= 25;
    }

    public boolean isValidStartAndFinishTime(String s1, String s2){
        String[] parts1 = s1.split(":");
        if (parts1.length != 2) return false;
        String[] parts2 = s2.split(":");
        if (parts2.length != 2) return false;

        int h1 = Integer.parseInt(parts1[0]);
        int m1 = Integer.parseInt(parts1[1]);
        int h2 = Integer.parseInt(parts2[0]);
        int m2 = Integer.parseInt(parts2[1]);

        if (h1>23 || m1>=60 || h2>23 || m2>=60) return false;
        if ((h1==h2 && m1>m2) || h1>h2) return false;

        return true;
    }

    public int getTimeUnit(String s, boolean b){
        String[] parts = s.split(":");
        if (b){
            if (parts[0].charAt(0)=='0'){
                return Integer.parseInt(String.valueOf(parts[0].charAt(1)));
            }else{
                return Integer.parseInt(parts[0]);
            }
        }else{
            if (parts[1].charAt(0)=='0'){
                return Integer.parseInt(String.valueOf(parts[1].charAt(1)));
            }else{
                return Integer.parseInt(parts[1]);
            }
        }
    }
}
