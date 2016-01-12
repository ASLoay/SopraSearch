package com.app.ashmawy.soprasearch;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.app.ashmawy.soprasearch.DataBase.DataBase;
import com.app.ashmawy.soprasearch.DataBase.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;
import com.app.ashmawy.soprasearch.Presenter.Presenter;


public class MainActivity extends ActionBarActivity implements GUI_Output {

    /**
     * Attributes
     */

    private Button connect;
    private Button manageProfile;
    private RadioButton RadioAdmin;
    private RadioButton RadioUser;
    private EditText username;
    private EditText dateBegin;
    private EditText dateEnd;
    private EditText timeBegin;
    private EditText timeEnd;
    private EditText duration;
    private EditText description;
    private EditText numOfCollab;
    private CheckBox visio;
    private CheckBox secured;
    private CheckBox digilab;
    private CheckBox telephone;
    private DatePicker datePickerBegin;
    private DatePicker datePickerEnd;
    private TimePicker timePickerBegin;
    private TimePicker timePickerEnd;
    ListView listeSite;
    ListView listRooms;
    ArrayList<Site> Lsite;
    ArrayList<String> modelsite;
    ArrayAdapter<String> adapter;
    private Presenter presenter;
    private DataBase DB;

    /**
     * Launch app
     * @param savedInstanceState state of instance
     */
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

        createComponents();
    }

    /**
     * Resume app
     */
    @Override
     protected void onResume() {
        // Open the DataBase
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

        Lsite = presenter.getSiteList();
        modelsite = new ArrayList<>();
        for(Site s: Lsite){
            modelsite.add(s.getName_site());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        listeSite=(ListView)findViewById(R.id.listSites);

        // Assign adapter to ListView
        listeSite.setAdapter(adapter);
    }

    /**
     * Create the components : buttons, texts...
     */
    public void createComponents() {

        // Radio buttons
        RadioAdmin= (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser= (RadioButton) findViewById(R.id.radio_User);

        // Buttons
        connect = (Button) findViewById(R.id.button_login);
        manageProfile = (Button) findViewById(R.id.buttonGP);

        // Texts
        username = (EditText) findViewById(R.id.editTextLogin);
        dateBegin = (EditText) findViewById(R.id.editDateBegin);
        dateEnd = (EditText) findViewById(R.id.editDateEnd);
       // timeBegin = (EditText) findViewById(R.id.editTimeBegin);
        timeEnd = (EditText) findViewById(R.id.editTimeEnd);
        description = (EditText) findViewById(R.id.editTextDesc);


        // Check boxes
        visio = (CheckBox)findViewById(R.id.checkBoxVisio);
        telephone = (CheckBox)findViewById(R.id.checkBoxTelephone);
        digilab = (CheckBox)findViewById(R.id.checkBoxDigilab);
        secured = (CheckBox)findViewById(R.id.checkBoxSecurite);

        // DatePicker and TimePicker
        datePickerBegin = (DatePicker)findViewById(R.id.datePickerBegin);

    }


    /**
     * On opening, the client chooses his nickname and selects User or Admin
     * Then clicks on LOG IN button
     * @param view the activity_main view
     */
    public void connectOnclick(View view){
        String name = String.valueOf(username.getText());

        // Depending on User or Admin, check if the client has the right to access to the appropriated page
        if (RadioAdmin.isChecked()) {
            // The client is an admin
            presenter.performAuthentication(name, false);
        } else if (RadioUser.isChecked()) {
            // The client is a User
            presenter.performAuthentication(name, true);
        }else {
            showAlert("Please check Admin or User");
        }
    }

    /**
     * The user selects the begin date of the reservation
     * @param view the searchscreenlayout view
     */
    public void selectDateBegin(View view){
        datePickerBegin.setVisibility(View.VISIBLE);
    }

    /**
     * The user selects the end date of the reservation
     * @param view the searchscreenlayout view
     */
    public void selectDateEnd(View view) {

    }

    /**
     * The user selects the begin time of the reservation
     * @param view the searchscreenlayout view
     */
    public void selectTimeBegin(View view) {

    }

    /**
     * The user selects the end time of the reservation
     * @param view the searchscreenlayout view
     */
    public void selectTimeEnd(View view) {

    }

    /**
     * The user has selected the parametters for a room booking
     * He clicks on SEARCH button
     * @param view the searchscreenlayout view
     */
    public void clickOnSearchRoom(View view) {
        // Test the parametter the User must selects
        if(duration.getText().equals("")) {
            showAlert("Please select a duration");
        }
        else if(description.getText().equals("")) {
            showAlert("Please select a description");
        }
        else if(numOfCollab.getText().equals("")) {
            showAlert("Please select a number of collaborators");
        }
        else {
            // Date
            // Contient la date et l'heure au moment de sa création
            Calendar calendrier = Calendar.getInstance();
            int month = calendrier.get(Calendar.MONTH);


            //DB.searchAvailableRooms(1, description.getText(), );
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
