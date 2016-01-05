package com.app.ashmawy.soprasearch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class MainActivity extends ActionBarActivity implements GUI_Output{

    Button connect;
    Button manageProfile;
    EditText username;
    RadioButton RadioAdmin;
    RadioButton RadioUser;
    Presenter present= new Presenter();
    GUI_Listener presenter=present;


    DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.setGUI(this);
        DB= new DataBase(present);
        presenter.setDB(DB);
        //presenter.start();
        connect=(Button) findViewById(R.id.button_login);
        manageProfile=(Button) findViewById(R.id.buttonGP);
        username= (EditText) findViewById(R.id.editTextLogin);

        RadioAdmin= (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser= (RadioButton) findViewById(R.id.radio_User);
        RadioUser.setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_Admin:
                if (checked) {
                    RadioButton r= (RadioButton) findViewById(R.id.radio_User);
                    RadioButton r2= (RadioButton) findViewById(R.id.radio_Admin);
                    r.setChecked(false);
                    r2.setChecked(true);
                }
            case R.id.radio_User:
                if (checked){
                    RadioButton r= (RadioButton) findViewById(R.id.radio_Admin);
                    RadioButton r2= (RadioButton) findViewById(R.id.radio_User);
                    r.setChecked(false);
                    r2.setChecked(true);
                }

        }
    }


    @Override
    public void ShowSearchScreen() {
        setContentView(R.layout.searchscreenlayout);
    }

    public void ShowSearch(View view) {
        setContentView(R.layout.searchscreenlayout);
    }
    public void ShowManageScreen(View view){
        setContentView(R.layout.manageprofilelayout);
    }

    @Override
    public void LocalisationSaved() {

    }

    public void connectOnclick(View view){
        String name = String.valueOf(username.getText());
        if (RadioAdmin.isChecked()) {
            presenter.PerformAuthentication(name, true);
        } else if (RadioUser.isChecked()) {
            presenter.PerformAuthentication(name, false);
        }
    }
}
