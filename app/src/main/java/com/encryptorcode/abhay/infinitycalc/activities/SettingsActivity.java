package com.encryptorcode.abhay.infinitycalc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.encryptorcode.abhay.infinitycalc.R;
import com.encryptorcode.abhay.infinitycalc.models.Theme;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    View settingTheme,selectThemeButton;
    Dialog themeSelectorDialog;
    GridLayout colorGrid;
    ImageView colorCircle;

    View settingDecimal,selectRoundButton;
    Dialog roundToDialog;
    NumberPicker picker;
    TextView roundNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp = getSharedPreferences("app",MODE_PRIVATE);
        edit = getSharedPreferences("app",MODE_PRIVATE).edit();

        settingTheme = findViewById(R.id.setting_theme);
        themeSelectorDialog = new Dialog(this);
        themeSelectorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        themeSelectorDialog.setContentView(R.layout.theme_selector_dialog);
        colorGrid = (GridLayout) themeSelectorDialog.findViewById(R.id.color_selector_grid);
        selectThemeButton = themeSelectorDialog.findViewById(R.id.theme_selector_select_button);
        colorCircle = (ImageView) findViewById(R.id.setting_primary_color);

        settingDecimal = findViewById(R.id.setting_decimal);
        roundToDialog = new Dialog(this);
        roundToDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        roundToDialog.setContentView(R.layout.rount_to_dialog);
        picker = (NumberPicker) roundToDialog.findViewById(R.id.round_to_picker);
        roundNumber = (TextView) findViewById(R.id.round_number);
        selectRoundButton = roundToDialog.findViewById(R.id.round_to_select_button);

        for (int i = 0; i < colorGrid.getChildCount(); i++) {
            FrameLayout layout = (FrameLayout) colorGrid.getChildAt(i);
            layout.setOnClickListener(this);
        }

        colorCircle.setColorFilter(colorPrimary);

        settingTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int themeId = sp.getInt("theme",0);
                for (int i = 0; i < colorGrid.getChildCount(); i++) {
                    FrameLayout layout = (FrameLayout) colorGrid.getChildAt(i);
                    if(layout.getTag().equals(String.valueOf(themeId)))
                        layout.getChildAt(1).setVisibility(View.VISIBLE);
                }
                themeSelectorDialog.show();
            }
        });

        themeSelectorDialog.findViewById(R.id.theme_selector_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeSelectorDialog.dismiss();
            }
        });

        selectThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this,HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            }
        });

        settingDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundToDialog.show();
            }
        });

        roundToDialog.findViewById(R.id.rount_to_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundToDialog.dismiss();
            }
        });

        int round = sp.getInt("round",2);
        roundNumber.setText(String.valueOf(round));
        picker.setMinValue(0);
        picker.setMaxValue(99999);
        picker.setValue(round);
        selectRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundNumber.setText(String.valueOf(picker.getValue()));
                edit.putInt("round",picker.getValue());
                edit.apply();
                roundToDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < colorGrid.getChildCount(); i++) {
            FrameLayout layout = (FrameLayout) colorGrid.getChildAt(i);
            layout.getChildAt(1).setVisibility(View.INVISIBLE);
        }
        FrameLayout layout = (FrameLayout) v;
        layout.getChildAt(1).setVisibility(View.VISIBLE);
        edit.putInt("theme",Integer.parseInt(String.valueOf(layout.getTag())));
        edit.apply();
    }
}
