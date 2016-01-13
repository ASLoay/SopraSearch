package com.app.ashmawy.soprasearch;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

import com.app.ashmawy.soprasearch.DataBase.DataBase;
import com.app.ashmawy.soprasearch.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;
import com.app.ashmawy.soprasearch.Presenter.Presenter;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */
public class MainActivity extends AppCompatActivity implements GUI_Output {

    /*************************
     * Attributes
     *************************/

    private RadioButton RadioAdmin;
    private RadioButton RadioUser;
    private EditText username;
    private TextView dateBegin;
    private TextView timeBegin;
    private TextView timeEnd;
    private EditText description;
    private EditText numOfCollab;
    private CheckBox visio;
    private CheckBox secured;
    private CheckBox digilab;
    private CheckBox telephone;
    private int hourstart ;
    private int minutestart;
    private int hourend ;
    private int minuteend;
    private int year, month, day;
    //private ListView listRooms;
    private Presenter presenter;
    private DataBase DB;



    /*************************
     * Methods from Android app
     *************************/

    /**
     * Launch app
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialisation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLoginComponents();

        // Creation of entities and linking
        DB = new DataBase(this) ;
        presenter = new Presenter();
        presenter.setGUIOutput(this);
        presenter.setDBOutput(DB);
        DB.setDBListener(presenter);
    }

    /**
     * Create the components for the Login page
     */
    public void setLoginComponents() {
        RadioAdmin  = (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser   = (RadioButton) findViewById(R.id.radio_User);
        username    = (EditText)    findViewById(R.id.editTextLogin);
        description = (EditText)    findViewById(R.id.editTextDesc);
        visio       = (CheckBox)    findViewById(R.id.checkBoxVisio);
        telephone   = (CheckBox)    findViewById(R.id.checkBoxTelephone);
        digilab     = (CheckBox)    findViewById(R.id.checkBoxDigilab);
        secured     = (CheckBox)    findViewById(R.id.checkBoxSecurite);
    }

    /**
     * Resume app after pause
     */
    @Override
     protected void onResume() {
        // Open the DataBase
        DB.open();
        super.onResume();
    }

    /**
     * App in pause
     */
    @Override
    protected void onPause() {
        DB.close();
        super.onPause();
    }



    /*************************
     * AUTHENTICATION
     *************************/

    /**
     * On opening, the client chooses his nickname and selects User or Admin
     * Then clicks on LOG IN button
     * @param view the activity_main view
     */
    public void connectOnclick(View view) {
        String name = String.valueOf(username.getText());

        // Tester ici si le champs user est vide
        if (name.equals("")) {
            showAlert("Please fill Login");
        } else {
            // Depending on User or Admin, check if the client has the right to access to the appropriated page
            if (RadioAdmin.isChecked()) {
                // The client is an admin
                presenter.performAuthentication(name, false);
            } else if (RadioUser.isChecked()) {
                // The client is a User
                presenter.performAuthentication(name, true);
            } else {
                showAlert("Please check Admin or User");
            }
        }
    }



    /*************************
     * ROOM BOOKING
     *************************/

    /**
     * Connexion as a User
     */
    @Override
    public void showSearchScreenAfterConnect() {
        setContentView(R.layout.searchscreenlayout);
        setSearchComponents();
        setTimeandDate();
    }

    /**
     * Create the components for the Search Rooms page
     */
    public void setSearchComponents() {
        description     = (EditText) findViewById(R.id.editTextDesc);
        numOfCollab     = (EditText)findViewById(R.id.editTextNbCollab);
        numOfCollab.setText("0");
        visio           = (CheckBox) findViewById(R.id.checkBoxVisio);
        telephone       = (CheckBox) findViewById(R.id.checkBoxTelephone);
        digilab         = (CheckBox) findViewById(R.id.checkBoxDigilab);
        secured         = (CheckBox) findViewById(R.id.checkBoxSecurite);
        dateBegin       = (TextView) findViewById(R.id.editDateBegin);
        timeBegin       = (TextView) findViewById(R.id.editTimeBegin);
        timeEnd         = (TextView) findViewById(R.id.editTimeEnd);
    }

    /**
     * Date of the day
     */
    public void setTimeandDate(){
        /*
        dateBegin = (TextView) findViewById(R.id.editDateBegin);
        // DatePicker and TimePicker
        // datePickerBegin = (DatePicker)findViewById(R.id.datePickerBegin);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        timeBegin=(TextView) findViewById(R.id.editTimeBegin);
        timeEnd=(TextView) findViewById(R.id.editTimeEnd);
        Calendar mcurrentTime = Calendar.getInstance();
        hourstart = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minutestart = mcurrentTime.get(Calendar.MINUTE);
        hourend=hourstart+1;
        minuteend=minutestart;
        timeBegin.setText( hourstart + ":" + minutestart);
        timeEnd.setText( (hourend) + ":" + minuteend);
        */

        // Date
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateBegin.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
        //todo : probleme : le mois est initialise a 0

        // Time
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        timeBegin.setText(hour + ":" + minute);
        timeEnd.setText((hour + 1) + ":" + minute);
    }

    /**
     * The user selects the date when he wants to book a room
     * @param view searchscreenlayout
     */
    public void setDate(View view) {
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            /*public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                month=selectedmonth;
                year=selectedyear;
                day=selectedday;
                dateBegin.setText("" + day + "/" + month + "/" + year);
            }*/
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                dateBegin.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
            }
        }, year, month, day);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    /**
     * The user selects the begin date of the reservation
     * @param view the searchscreenlayout view
     */
    public void setTimeBegin(View view){
        Calendar mcurrentTime = Calendar.getInstance();
        hourstart = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minutestart = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hourstart=selectedHour;
                minutestart=selectedMinute;
                timeBegin.setText( hourstart + ":" + minutestart);
            }
        }, hourstart, minutestart, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();


//        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//        int minute = mcurrentTime.get(Calendar.MINUTE);
//            @Override
//            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                timeBegin.setText( selectedHour + ":" + selectedMinute);
//            }
//        }, hour, minute, true);//Yes 24 hour time
    }

    /**
     * The user selects the end date of the reservation
     * @param view the searchscreenlayout view
     */
    public void setTimeEnd(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        hourend = mcurrentTime.get(Calendar.HOUR_OF_DAY)+1;
        minuteend = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hourend=selectedHour;
                minuteend=selectedMinute;
                timeEnd.setText(hourend + ":" + minuteend);
            }
        }, hourend, minuteend, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    /**
     * The user clicks on SEARCH button
     * @param view searchscreenlayout
     */
    public void clickOnSearchRooms(View view){

        /*// Test the parametter the User must selects
        if(duration.getText().equals("")) {
            showAlert("Please select a duration");
        }
        else if(description.getText().equals("")) {
            showAlert("Please select a description");
        }
        else if(numOfCollab.getText().equals("")) {
            showAlert("Please select a number of collaborators");
        }
        else {*/
            Calendar calb = Calendar.getInstance();
            calb.set(Calendar.HOUR_OF_DAY, hourstart);
            calb.set(Calendar.MINUTE, minutestart);
            calb.set(Calendar.DAY_OF_MONTH, day);
            calb.set(Calendar.MONTH, month);
            calb.set(Calendar.YEAR, year);

            Calendar calend = Calendar.getInstance();
            calend.set(Calendar.HOUR_OF_DAY, hourend);
            calend.set(Calendar.MINUTE, minuteend);
            calend.set(Calendar.DAY_OF_MONTH, day);
            calend.set(Calendar.MONTH, month);
            calend.set(Calendar.YEAR, year);

            java.util.Date utilDatebegin = calb.getTime();
            java.util.Date utilDateend = calend.getTime();

            java.sql.Date datebegin = new java.sql.Date(utilDatebegin.getTime());
            java.sql.Date dateend = new java.sql.Date(utilDateend.getTime());
            String desc = description.toString();
            int numC = Integer.parseInt(numOfCollab.getText().toString());

            if (utilDatebegin.after(utilDateend)) {
                new AlertDialog.Builder(this).setTitle("Warning").setMessage("date begin must be > date end").setNeutralButton("Close", null).show();
            } else {
                presenter.performSearchRoom(desc, datebegin, dateend, numC, visio.isChecked(), telephone.isChecked(), secured.isChecked(), digilab.isChecked());
            }
        //}
    }

    /**
     * Returns the list of acailable rooms according to the user specifications
     * @param rooms list of avilable rooms
     */
    @Override
    public void listOfAvailableRooms(List rooms) {

    }

    /**
     * The selected room is booked
     */
    @Override
    public void roomBooked() {

    }



    /*************************
     * PROFIL MANAGEMENT
     *************************/

    /**
     * When we are on the search creen layout,
     * and we want to change the site of ref by clicking on PM button
     * @param view searchcreenlayout
     */
    public void showManageScreen(View view) {
        setContentView(R.layout.manageprofilelayout);

        ArrayList<Site> lsite = presenter.getSiteList();
        ArrayList<String> modelsite = new ArrayList<>();
        for(Site s: lsite){
            modelsite.add(s.getName_site());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        ListView listSites = (ListView) findViewById(R.id.listSites);

        // Assign adapter to ListView
        listSites.setAdapter(adapter);
    }

    /**
     * On a enregistre le site de reference choisi par l'utilisateur
     */
    @Override
    public void localisationSaved() {
        // On affiche la page de recherche de salle
        setContentView(R.layout.searchscreenlayout);
    }


    /**
     * When we are on the manage profile layout,
     * we have selected a site of ref
     * and we save this site by clicking on R button
     * @param view manageprofilelayout
     */
    public void changeSiteOfReference(View view) {
        //todo : change site of ref presenter with the new site
        //presenter.performSaveLocalisationSite();

        //todo : remove the following when done
        localisationSaved();
    }



    /*************************
     * GENERAL INFO
     *************************/

     /**
     * Show the general info page after connexion as an admin and calculate the ratios
     * @param nbSites number of sites
     * @param nbRooms number of rooms
     * @param nbReservations number of reservations
     * @param reservationRate reservation rate
     */
    @Override
    public void showGeneralInfoPageAfterCalcul(int nbSites, int nbRooms, int nbReservations, int reservationRate) {
        // On affiche la page avec les resultats
        setContentView(R.layout.general_info);
    }



    /*************************
     * SITE MANAGEMENT
     *************************/

    @Override
    public void suppressionSiteSucceed() {

    }

    @Override
    public void infoSite(String name_site,  int nb_salles_site, String address_sites) {

    }



    /*************************
     * ADD/MODIFY SITE
     *************************/

    @Override
    public void siteAddedOrModified() {

    }



    /*************************
     * ROOM MANAGEMENT
     *************************/

    @Override
    public void suppressionRoomSucceed() {

    }

    @Override
    public void infoRoom(int num_room, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }



    /*************************
     * ADD/MODIFY ROOM
     *************************/

    @Override
    public void roomAddedOrModified() {

    }



    /*************************
     * USER INTERACTION
     *************************/

    public void showAlert(String message){
        new AlertDialog.Builder(this).setTitle("Warning").setMessage(message).setNeutralButton("Close", null).show();
    }

    @Override
    public void testUserInput() {

    }
}
