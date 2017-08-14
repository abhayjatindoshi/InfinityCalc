package com.encryptorcode.abhay.infinitycalc.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.encryptorcode.abhay.infinitycalc.R;
import com.encryptorcode.abhay.infinitycalc.models.Theme;

/**
 * Created by abhay-5228 on 12/08/17.
 */

public abstract class NavigationBaseActivity extends AppCompatActivity {

    int colorPrimary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("app",MODE_PRIVATE);
        Theme theme = Theme.getTheme(sp.getInt("theme",0));
        switch (theme) {
            case BLUE:
                setTheme(R.style.BaseTheme_Blue_NoActionBar);
                break;
            case DARK:
                setTheme(R.style.BaseTheme_Dark_NoActionBar);
                break;
            case RED:
                setTheme(R.style.BaseTheme_Red_NoActionBar);
                break;
            case GREEN:
                setTheme(R.style.BaseTheme_Green_NoActionBar);
                break;
            case PINK:
                setTheme(R.style.BaseTheme_Pink_NoActionBar);
                break;
            case ORANGE:
                setTheme(R.style.BaseTheme_Orange_NoActionBar);
                break;
            case YELLOW:
                setTheme(R.style.BaseTheme_Yellow_NoActionBar);
                break;
            case PURPLE:
                setTheme(R.style.BaseTheme_Purple_NoActionBar);
                break;
        }
        super.onCreate(savedInstanceState);

        colorPrimary = getPrimaryColor();
    }

    private int getPrimaryColor(){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
