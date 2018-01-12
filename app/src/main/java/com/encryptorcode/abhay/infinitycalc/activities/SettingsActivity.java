package com.encryptorcode.abhay.infinitycalc.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.encryptorcode.abhay.infinitycalc.R;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    InputMethodManager imm;
    SharedPreferences sp;

    SharedPreferences.Editor edit;
    View settingTheme;
    Dialog themeSelectorDialog;
    GridLayout colorGrid;

    ImageView colorCircle;
    View settingDecimal,selectRoundButton;
    Dialog roundToDialog;
    EditText picker;

    TextView roundNumber;
    SwitchCompat infinityModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        sp = getSharedPreferences("app",MODE_PRIVATE);
        edit = getSharedPreferences("app",MODE_PRIVATE).edit();

        settingTheme = findViewById(R.id.setting_theme);
        themeSelectorDialog = new Dialog(this);
        themeSelectorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        themeSelectorDialog.setContentView(R.layout.theme_selector_dialog);
        colorGrid = (GridLayout) themeSelectorDialog.findViewById(R.id.color_selector_grid);
        colorCircle = (ImageView) findViewById(R.id.setting_primary_color);

        settingDecimal = findViewById(R.id.setting_decimal);
        roundToDialog = new Dialog(this);
        roundToDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        roundToDialog.setContentView(R.layout.rount_to_dialog);
        picker = (EditText) roundToDialog.findViewById(R.id.round_to_picker);
        roundNumber = (TextView) findViewById(R.id.round_number);
        selectRoundButton = roundToDialog.findViewById(R.id.round_to_select_button);

        infinityModeSwitch = (SwitchCompat) findViewById(R.id.infinity_mode_switch);

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

        settingDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundToDialog.show();
                picker.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        roundToDialog.findViewById(R.id.round_to_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundToDialog.dismiss();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        int round = sp.getInt("round",2);
        roundNumber.setText(String.valueOf(round));
        picker.setText(String.valueOf(round));
        selectRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundNumber.setText(String.valueOf(picker.getText()));
                edit.putInt("round",Integer.parseInt(picker.getText().toString()));
                edit.apply();
                roundToDialog.dismiss();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        infinityModeSwitch.setChecked(sp.getBoolean("infinityMode",false));
        infinityModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edit.putBoolean("infinityMode",isChecked);
                edit.apply();
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
        Intent i = new Intent(SettingsActivity.this,HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
    }
}
