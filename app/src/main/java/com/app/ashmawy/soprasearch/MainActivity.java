package com.app.ashmawy.soprasearch;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import com.app.ashmawy.soprasearch.DataBase.DataBase;
import com.app.ashmawy.soprasearch.DataBase.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;
import com.app.ashmawy.soprasearch.Presenter.Presenter;


public class MainActivity extends ActionBarActivity implements GUI_Output {

    /**
     * Attributes
     */

    Button connect;
    Button manageProfile;
    EditText username;
    RadioButton RadioAdmin;
    RadioButton RadioUser;
    EditText date;
    EditText duration;
    CheckBox visio;
    CheckBox secured;
    CheckBox digilab;
    CheckBox telephone;
    ListView listeSite;
    ListView listRooms;
    ArrayList<Site> Lsite;
    ArrayList<String> modelsite;
    ArrayAdapter<String> adapter;
    private Presenter presenter;
    private DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialisation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creation of entities and linking
        DB = new DataBase(this) ;
        presenter = new Presenter();

        presenter.setGUIOutput(this);
        presenter.setDBOutput(DB);
        DB.setDBListener(presenter);

        connect=(Button) findViewById(R.id.button_login);
        manageProfile=(Button) findViewById(R.id.buttonGP);
        username= (EditText) findViewById(R.id.editTextLogin);

        RadioAdmin= (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser= (RadioButton) findViewById(R.id.radio_User);

        date = (EditText) findViewById(R.id.editTextDate);
        duration= (EditText) findViewById(R.id.editTextCreneaux);
        visio=(CheckBox)findViewById(R.id.checkBoxVisio);
        telephone=(CheckBox)findViewById(R.id.checkBoxTelephone);
        digilab=(CheckBox)findViewById(R.id.checkBoxDigilab);
        secured=(CheckBox)findViewById(R.id.checkBoxSecurite);



    }

    @Override
     protected void onResume() {
        DB.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        DB.close();
        super.onPause();
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

    }


    public void showAlert(String message){
        new AlertDialog.Builder(this).setTitle("Warning").setMessage(message).setNeutralButton("Close", null).show();
    }

    public void ShowSearch(View view) {
        setContentView(R.layout.searchscreenlayout);
    }
    public void ShowManageScreen(View view){

        setContentView(R.layout.manageprofilelayout);


        Lsite=presenter.getSiteList();
        modelsite=new ArrayList<>();
        for(Site s: Lsite){
            modelsite.add(s.getName_site());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        listeSite=(ListView)findViewById(R.id.listSites);

        // Assign adapter to ListView
        listeSite.setAdapter(adapter);
    }



    public void connectOnclick(View view){
        String name = String.valueOf(username.getText());
        if (RadioAdmin.isChecked()) {
            presenter.performAuthentication(name, false);
        } else if (RadioUser.isChecked()) {
            presenter.performAuthentication(name, true);
        }else {
            showAlert("Please check Admin or User");
        }
    }



    /*************************
     * METHODS FROM INTERFACE
     *************************/



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
