package com.encryptorcode.abhay.infinitycalc.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;

import com.encryptorcode.abhay.infinitycalc.R;
import com.encryptorcode.abhay.infinitycalc.models.Theme;

/**
 * Created by abhay-5228 on 12/08/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    int colorPrimary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("app",MODE_PRIVATE);
        Theme theme = Theme.getTheme(sp.getInt("theme",0));
        switch (theme) {
            case BLUE:
                setTheme(R.style.BaseTheme_Blue);
                break;
            case DARK:
                setTheme(R.style.BaseTheme_Dark);
                break;
            case RED:
                setTheme(R.style.BaseTheme_Red);
                break;
            case GREEN:
                setTheme(R.style.BaseTheme_Green);
                break;
            case PINK:
                setTheme(R.style.BaseTheme_Pink);
                break;
            case ORANGE:
                setTheme(R.style.BaseTheme_Orange);
                break;
            case YELLOW:
                setTheme(R.style.BaseTheme_Yellow);
                break;
            case PURPLE:
                setTheme(R.style.BaseTheme_Purple);
                break;
        }
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        colorPrimary = getPrimaryColor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private int getPrimaryColor(){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
