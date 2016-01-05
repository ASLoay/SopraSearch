package com.app.ashmawy.soprasearch;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.List;


public class MainActivity extends ActionBarActivity implements GUI_Output{

    /**
     * Attributes
     */

    Button connect;
    Button manageProfile;
    EditText username;
    RadioButton RadioAdmin;
    RadioButton RadioUser;

    private static int version = 0;


    private Presenter presenter;
    private DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creation of entities and linking
        DB = new DataBase(this , "SopraSearch_RT11", null, ++version) ;
        presenter = new Presenter();

        presenter.setGUIOutput(this);
        presenter.setDBOutput(DB);
        DB.setDBListener(presenter);

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




    public void ShowSearch(View view) {
        setContentView(R.layout.searchscreenlayout);
    }
    public void ShowManageScreen(View view){
        setContentView(R.layout.manageprofilelayout);
    }



    public void connectOnclick(View view){
        String name = String.valueOf(username.getText());
        if (RadioAdmin.isChecked()) {
            presenter.performAuthentication(name, true);
        } else if (RadioUser.isChecked()) {
            presenter.performAuthentication(name, false);
        }
    }







    /**
     * AUTHENTICATION
     */

    @Override
    public void showSearchScreen() {
        setContentView(R.layout.searchscreenlayout);
    }



    /**
     * ROOM BOOKING
     */

    @Override
    public void listOfAvailableRooms(List rooms) {

    }

    @Override
    public void roomBooked() {

    }



    /**
     * PROFIL MANAGEMENT
     */

    @Override
    public void localisationSaved() {

    }



    /**
     * GENERAL INFO
     */

    @Override
    public void generalInfoPage(int nbSites, int nbRooms, int nbReservations, int reservationRate) {

    }



    /**
     * SITE MANAGEMENT
     */

    @Override
    public void suppressionSiteSucceed() {

    }

    @Override
    public void infoSite(String name_site,  int nb_salles_site, String address_sites) {

    }



    /**
     * ADD/MODIFY SITE
     */

    @Override
    public void siteAddedOrModified() {

    }



    /**
     * ROOM MANAGEMENT
     */

    @Override
    public void suppressionRoomSucceed() {

    }

    @Override
    public void infoRoom(int num_room, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }



    /**
     * ADD/MODIFY ROOM
     */

    @Override
    public void roomAddedOrModified() {

    }



    /**
     * USER INTERACTION
     */

    @Override
    public void error(String message) {

    }

    @Override
    public void testUserInput() {

    }
}
