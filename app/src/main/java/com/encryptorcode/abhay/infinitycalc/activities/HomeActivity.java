package com.encryptorcode.abhay.infinitycalc.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encryptorcode.abhay.infinitycalc.R;
import com.encryptorcode.abhay.infinitycalc.utils.ControllerBinder;
import com.encryptorcode.abhay.infinitycalc.exceptions.EmptyExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;
import com.encryptorcode.abhay.infinitycalc.exceptions.LimitCrossedException;

import java.util.EmptyStackException;

public class HomeActivity extends NavigationBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView expression,result;
    View infinityBlinkView;
    int round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
        if(expression.getText().toString().equals("")){
            result.setText("");
            return;
        }
        round = getSharedPreferences("app",MODE_PRIVATE).getInt("round",2);
        try {
            result.setText(ControllerBinder.eval(expression.getText().toString(),round));
        } catch (ArithmeticException e) {
            if(isEqualPressed) {
                infinityBlinkAnimation();
            }
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

}
