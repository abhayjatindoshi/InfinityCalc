package com.encryptorcode.abhay.infinitycalc.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.encryptorcode.abhay.infinitycalc.R;
import com.encryptorcode.abhay.infinitycalc.controllers.ExpressionOperations;
import com.encryptorcode.abhay.infinitycalc.utils.ControllerBinder;
import com.encryptorcode.abhay.infinitycalc.exceptions.EmptyExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.LimitCrossedException;

import java.util.EmptyStackException;

public class HomeActivity extends NavigationBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView expression,result,decimal,binary,octal,hexadecimal;
    View infinityBlinkView;
    int round;
    Dialog baseCodeDialog;
    Boolean infinityMode;
    ProcessEval processEval;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        LinearLayout heaverViewBackground = (LinearLayout) headerView.findViewById(R.id.nav_header_background);
        heaverViewBackground.setBackgroundColor(colorPrimary);

        expression = (TextView) findViewById(R.id.expression_text);
        result = (TextView) findViewById(R.id.result_text);
        infinityBlinkView = findViewById(R.id.infinity_blink_view);

        baseCodeDialog = new Dialog(this);
        baseCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        baseCodeDialog.setContentView(R.layout.base_code_dialog);
        decimal = (TextView) baseCodeDialog.findViewById(R.id.base_decimal);
        binary = (TextView) baseCodeDialog.findViewById(R.id.base_binary);
        octal = (TextView) baseCodeDialog.findViewById(R.id.base_octal);
        hexadecimal = (TextView) baseCodeDialog.findViewById(R.id.base_hexadecimal);
        baseCodeDialog.findViewById(R.id.base_code_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCodeDialog.dismiss();
            }
        });

        expression.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                evaluate(false);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this,AboutActivity.class));
        } else if (id == R.id.nav_code) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/abhayjatindoshi/infinitycalc"));
            startActivity(i);
        } else if (id == R.id.nav_feedback) {
            rateApp();
        } else if (id == R.id.nav_share) {
            shareApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.keyboard_button_0:
            case R.id.keyboard_button_1:
            case R.id.keyboard_button_2:
            case R.id.keyboard_button_3:
            case R.id.keyboard_button_4:
            case R.id.keyboard_button_5:
            case R.id.keyboard_button_6:
            case R.id.keyboard_button_7:
            case R.id.keyboard_button_8:
            case R.id.keyboard_button_9:
            case R.id.keyboard_button_dot:
            case R.id.keyboard_button_open_bracket:
            case R.id.keyboard_button_close_bracket:
            case R.id.keyboard_button_power:
            case R.id.keyboard_button_factorial:
            case R.id.keyboard_button_square_root:
            case R.id.keyboard_button_precentage:
            case R.id.keyboard_button_subtract:
            case R.id.keyboard_button_divide:
            case R.id.keyboard_button_add:
            case R.id.keyboard_button_multiply:
                expression.append(((Button)v).getText());
                break;

            case R.id.keyboard_button_dec:
                try{
                    baseCodeDialog.findViewById(R.id.base_code_values).setVisibility(View.VISIBLE);
                    baseCodeDialog.findViewById(R.id.base_code_error).setVisibility(View.GONE);
                    binary.setText(ExpressionOperations.toBaseCode(result.getText().toString(),2));
                    octal.setText(ExpressionOperations.toBaseCode(result.getText().toString(),8));
                    hexadecimal.setText(ExpressionOperations.toBaseCode(result.getText().toString(),16));
                    decimal.setText(result.getText());
                } catch (IllegalExpressionException e) {
                    baseCodeDialog.findViewById(R.id.base_code_values).setVisibility(View.GONE);
                    baseCodeDialog.findViewById(R.id.base_code_error).setVisibility(View.VISIBLE);
                } catch (NumberFormatException e){
                    break;
                }
                baseCodeDialog.show();
                break;

            case R.id.keyboard_button_all_clear:
                expression.setText("");
                result.setText("");
                break;

            case R.id.keyboard_button_delete:
                String expressionText = expression.getText().toString();
                if(expressionText.length() > 0)
                    expression.setText(expressionText.substring(0,expressionText.length()-1));
                break;

            case R.id.keyboard_button_equal:
                evaluate(true);
                break;

        }
    }

    void evaluate(boolean isEqualPressed){
        infinityMode = getSharedPreferences("app",MODE_PRIVATE).getBoolean("infinityMode",false);
        if(infinityMode){
            if(isEqualPressed) {
                if (processEval != null) {
                    processEval.cancel(true);
                }
                processEval = new ProcessEval(isEqualPressed);
                processEval.execute();
            }
        } else {
            if(expression.getText().toString().equals("")){
                result.setText("");
                return;
            }
            round = getSharedPreferences("app",MODE_PRIVATE).getInt("round",2);
            try {
                result.setText(ControllerBinder.eval(expression.getText().toString(),round,infinityMode));
                if(isEqualPressed){
                    expression.setText(result.getText());
                }
            } catch (ArithmeticException e) {
                infinityBlinkAnimation();
            } catch (IllegalExpressionException | EmptyExpressionException e){
                if(isEqualPressed){
                    result.setText("ERROR");
                }
            } catch (LimitCrossedException e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            } catch (EmptyStackException e) {
                Toast.makeText(this,"Cannot have first element as %",Toast.LENGTH_SHORT).show();
            }
        }
    }

    void infinityBlinkAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.blink);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                infinityBlinkView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                infinityBlinkView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        infinityBlinkView.startAnimation(animation);
    }

    //opens the playstore to rate this app
    private void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("http://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    //creates an intent for opening rate in Play Store
    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    //shows a sharing screen to share the link of the app
    private void shareApp(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT,getResources().getString(R.string.share_text));
        startActivity(Intent.createChooser(sendIntent, "Share app with "));
    }

    class ProcessEval extends AsyncTask<Void,Void,Void>{


        private boolean isEqualPressed;
        private Exception e;
        private String resultText;

        public ProcessEval(boolean isEqualPressed) {
            super();
            this.isEqualPressed = isEqualPressed;
        }

        @Override
        protected void onPreExecute() {
            Log.e("TAG","Pre execute");
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setMessage("Calculating");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("TAG","background");
            if(expression.getText().toString().equals("")){
                resultText = "";
                return null;
            }
            round = getSharedPreferences("app",MODE_PRIVATE).getInt("round",2);
            try {
                resultText = ControllerBinder.eval(expression.getText().toString(),round,infinityMode);
            } catch (Exception e) {
                this.e = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("TAG","Post execute");
            progressDialog.hide();
            if(resultText != null){
                result.setText(resultText);
            }
            if(e != null){
                if(e instanceof ArithmeticException){
                    infinityBlinkAnimation();
                } else if(e instanceof IllegalExpressionException || e instanceof EmptyExpressionException){
                    if(isEqualPressed){
                        result.setText("ERROR");
                    }
                } else if(e instanceof LimitCrossedException){
                    Toast.makeText(HomeActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                } else if(e instanceof EmptyStackException){
                    Toast.makeText(HomeActivity.this,"Cannot have first element as %",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}


