package com.app.ashmawy.soprasearch;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.ArrayList;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    private TextView dateBegin;

    private TextView timeEnd;
    private EditText duration;
    private EditText description;
    private EditText numOfCollab;
    private CheckBox visio;
    private CheckBox secured;
    private CheckBox digilab;
    private CheckBox telephone;
    private TextView timeBegin;

    private int hourstart ;
    private int minutestart;
    private int hourend ;
    private int minuteend;
    private Calendar calendar;
    private int year, month, day;
    private ListView listSites;
    private ListView listRooms;
    private ArrayList<Site> Lsite;
    private ArrayList<String> modelsite;
    private ArrayAdapter<String> adapter;
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
     * Create the components : buttons, texts...
     */
    public void createComponents() {

        // Radio buttons
        RadioAdmin= (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser= (RadioButton) findViewById(R.id.radio_User);

        // Buttons
        connect = (Button) findViewById(R.id.button_login);

        // Texts
        username = (EditText) findViewById(R.id.editTextLogin);
        description = (EditText) findViewById(R.id.editTextDesc);


        // Check boxes
        visio = (CheckBox)findViewById(R.id.checkBoxVisio);
        telephone = (CheckBox)findViewById(R.id.checkBoxTelephone);
        digilab = (CheckBox)findViewById(R.id.checkBoxDigilab);
        secured = (CheckBox)findViewById(R.id.checkBoxSecurite);
    }

    public void setDate(View view) {
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                month=selectedmonth;
                year=selectedyear;
                day=selectedday;
                dateBegin.setText("" + day + "/" + month + "/" + year);
            }
        }, year, month, day);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
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




    public void showSearch(View view) {
        setContentView(R.layout.searchscreenlayout);
        setTimeandDate();
    }
    
    /**
     * When we are on the manage profile layout,
     * we have selected a site of ref
     * and we save this site by clicking on R button
     * @param view manageprofilelayout
     */
    public void showManageScreen(View view) {
        setContentView(R.layout.manageprofilelayout);

        Lsite = presenter.getSiteList();
        modelsite = new ArrayList<>();
        for(Site s: Lsite){
            modelsite.add(s.getName_site());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        listSites = (ListView)findViewById(R.id.listSites);

        // Assign adapter to ListView
        listSites.setAdapter(adapter);
    }


    /**
     * When we are on the search room layout,
     * we click on the PM button
     * @param view
     */
    public void calculGeneralInfoAfterConnect(View view) {
        // On clacul les infos a afficher
        presenter.performGeneralInfo();
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
        } else {
            showAlert("Please check Admin or User");
        }
    }

    /**
     * The user selects the begin date of the reservation
     */
    private void showDate(int year, int month, int day) {
        dateBegin.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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
            // Contient la date et l'heure au moment de sa creation
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

    public void setTimeandDate(){
        /******************la Date************************/
        dateBegin = (TextView) findViewById(R.id.editDateBegin);
        // DatePicker and TimePicker
        // datePickerBegin = (DatePicker)findViewById(R.id.datePickerBegin);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        /************************************************/
        timeBegin=(TextView) findViewById(R.id.editTimeBegin);
        timeEnd=(TextView) findViewById(R.id.editTimeEnd);
        Calendar mcurrentTime = Calendar.getInstance();
        hourstart = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minutestart = mcurrentTime.get(Calendar.MINUTE);
        hourend=hourstart+1;
        minuteend=minutestart;
        timeBegin.setText( hourstart + ":" + minutestart);

        timeEnd.setText( (hourend) + ":" + minuteend);
    }


    public void setSearchComponents(){
        description = (EditText) findViewById(R.id.editTextDesc);

        manageProfile = (Button) findViewById(R.id.buttonGP);
        numOfCollab=(EditText)findViewById(R.id.editTextNbCollab);
        numOfCollab.setText("0");
        // Check boxes
        visio = (CheckBox)findViewById(R.id.checkBoxVisio);
        telephone = (CheckBox)findViewById(R.id.checkBoxTelephone);
        digilab = (CheckBox)findViewById(R.id.checkBoxDigilab);
        secured = (CheckBox)findViewById(R.id.checkBoxSecurite);
    }

    @Override
    public void showSearchScreenAfterConnect() {
        //todo : checker si un site est selectionne
        // if(listSite != null)
        setContentView(R.layout.searchscreenlayout);
        setTimeandDate();
        setSearchComponents();

    }

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
    }

    /**
     * ROOM BOOKING
     */

    public void searchRooms(View view){

        Calendar calb = Calendar.getInstance();
        calb.set(Calendar.HOUR_OF_DAY,hourstart);
        calb.set(Calendar.MINUTE,minutestart);
        calb.set(Calendar.DAY_OF_MONTH,day);
        calb.set(Calendar.MONTH,month);
        calb.set(Calendar.YEAR,year);

        Calendar calend = Calendar.getInstance();
        calend.set(Calendar.HOUR_OF_DAY,hourend);
        calend.set(Calendar.MINUTE,minuteend);
        calend.set(Calendar.DAY_OF_MONTH,day);
        calend.set(Calendar.MONTH,month);
        calend.set(Calendar.YEAR,year);


        java.util.Date utilDatebegin = calb.getTime();

        java.util.Date utilDateend = calend.getTime();



        java.sql.Date  datebegin = new java.sql.Date(utilDatebegin.getTime());
        java.sql.Date  dateend = new java.sql.Date(utilDateend.getTime());
        String desc= description.toString();
        int numC= Integer.parseInt(numOfCollab.getText().toString());

        if (utilDatebegin.after(utilDateend) ){
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("date begin must be > date end").setNeutralButton("Close", null).show();
        }else {
            presenter.performSearchRoom(desc, datebegin, dateend, numC, visio.isChecked(), telephone.isChecked(), secured.isChecked(), digilab.isChecked());
        }

    }

    @Override
    public void listOfAvailableRooms(List rooms) {

    }

    @Override
    public void roomBooked() {

    }



    /**
     * PROFIL MANAGEMENT
     */

    /**
     * On a enregistre le site de reference choisi par l'utilisateur
     */
    @Override
    public void localisationSaved() {
        // On affiche la page de recherche de salle
        setContentView(R.layout.searchscreenlayout);
    }



    /**
     * GENERAL INFO
     */

    /**
     * On affiche la page des informations generales apres connexion en tant qu'Admin
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

    public void showAlert(String message){
        new AlertDialog.Builder(this).setTitle("Warning").setMessage(message).setNeutralButton("Close", null).show();
    }

    @Override
    public void testUserInput() {

    }
}
