package com.encryptorcode.abhay.infinitycalc.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.encryptorcode.abhay.infinitycalc.R;
import com.encryptorcode.abhay.infinitycalc.controllers.ControllerBinder;
import com.encryptorcode.abhay.infinitycalc.exceptions.IllegalExpressionException;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView expression,result;

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

        expression = (TextView) findViewById(R.id.expression_text);
        result = (TextView) findViewById(R.id.result_text);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
                break;

            case R.id.keyboard_button_delete:
                String expressionText = expression.getText().toString();
                if(expressionText.length() > 0)
                    expression.setText(expressionText.substring(0,expressionText.length()-1));
                break;

            case R.id.keyboard_button_equal:
                try {
                    result.setText(ControllerBinder.eval(expression.getText().toString()));
                } catch (IllegalExpressionException e) {
                    Toast.makeText(this,"Invalid Expression",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
