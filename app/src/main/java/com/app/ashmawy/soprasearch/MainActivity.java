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
    EditText username;
    RadioButton RadioAdmin;
    RadioButton RadioUser;
    GUI_Listener presenter= new Presenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.setGUI(this);
        presenter.start();
        connect=(Button) findViewById(R.id.button_login);
        username= (EditText) findViewById(R.id.editTextLogin);

        RadioAdmin= (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser= (RadioButton) findViewById(R.id.radio_User);
        RadioUser.setChecked(true);
        RadioAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!RadioAdmin.isChecked()) {
                    RadioAdmin.setChecked(true);
                    RadioUser.setChecked(false);
                }
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = String.valueOf(username.getText());
                if (RadioAdmin.isChecked()) {
                    presenter.PerformAuthentication(name, true);
                } else if (RadioUser.isChecked()) {
                    presenter.PerformAuthentication(name, false);
                    ShowSearchScreen();
                }
            }
        });

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

    @Override
    public void LocalisationSaved() {

    }
}
