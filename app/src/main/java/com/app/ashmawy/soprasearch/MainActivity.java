package com.app.ashmawy.soprasearch;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import com.app.ashmawy.soprasearch.DataBase.DataBase;
import com.app.ashmawy.soprasearch.Model.Reservation;
import com.app.ashmawy.soprasearch.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;
import com.app.ashmawy.soprasearch.Presenter.Presenter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

    // Entities
    private Presenter presenter;
    private DataBase DB;

    // GUI Components
    private Button bookBtn;
    private Button infoSiteBtn;
    private Button modifySiteBtn;
    private Button deleteSiteBtn;
    private Button infoRoomBtn;
    private Button modifyRoomBtn;
    private Button deleteRoomBtn;
    private Button infoReservationBtn;
    private Button deleteReservationBtn;
    private Button saveSiteMngt;
    private Button saveRoomMngt;
    private Button cancelOkSiteMngt;
    private Button cancelOkRoomMngt;
    private Button okReservationMngt;
    private CheckBox visio;
    private CheckBox secured;
    private CheckBox digilab;
    private CheckBox telephone;
    private Date datebegin;
    private Date dateend;
    private EditText username;
    private EditText description;
    private EditText numOfCollab;
    private EditText nameSite;
    private EditText nbRoomsSite;
    private EditText addrSite;
    private EditText siteReserv;
    private EditText roomReserv;
    private EditText collabReserv;
    private EditText descrReserv;
    private EditText nbCollabReserv;
    private EditText dateBeginReserv;
    private EditText dateEndReserv;
    private ListView listRooms;
    private RadioButton RadioAdmin;
    private RadioButton RadioUser;
    private TextView dateBeginText;
    private TextView timeBegin;
    private TextView timeEnd;
    private TextView titlePageSite;
    private TextView titlePageRoom;

    // Others
    private int hourstart, minutestart;
    private int hourend, minuteend;
    private int year, month, day;
    private int siteOfRef;
    private int whichSaveBtnSite = 0;
    private int whichSaveBtnRoom = 0;
    private String nameSiteMngt;
    private String nameRoomMngt;
    private String nameReservationMngt;
    private String roomToBook;


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
        showLoginPage(null);

        // Creation of entities and linking
        DB = new DataBase(this);
        presenter = new Presenter();
        presenter.setGUIOutput(this);
        presenter.setDBOutput(DB);
        DB.setDBListener(presenter);
    }

    /**
     * Show the login page
     * @param view could be all with logo button on each
     */
    public void showLoginPage(View view) {
        // Show login layout
        setContentView(R.layout.login);
        setLoginComponents();
    }

    /**
     * Create the components for the Login page
     */
    public void setLoginComponents() {
        // Get the components
        RadioAdmin = (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser  = (RadioButton) findViewById(R.id.radio_User);
        username   = (EditText)    findViewById(R.id.editTextLogin);
    }

    /**
     * Resume app after pause
     */
    @Override
    protected void onResume() {
        // Open the DataBase
        DB.open();

        // Resume the previous state
        super.onResume();
    }

    /**
     * App in pause
     */
    @Override
    protected void onPause() {
        // Close the DataBase
        DB.close();

        // Pause the app
        super.onPause();
    }



    /*************************
     * AUTHENTICATION
     *************************/

    /**
     * On opening, the client chooses his nickname and selects User or Admin
     * Then clicks on LOG IN button
     * @param view the login view
     */
    public void connectOnclick(View view) {
        String name = String.valueOf(username.getText());

        // Test if the user field is empty
        if (name.equals("")) {
            showAlert("Please fill Login", "Warning");
        }
        else {
            // Depending on User or Admin, check if the client has the right to access to the appropriated page
            if (RadioAdmin.isChecked()) {
                // The client is an Admin
                presenter.performAuthentication(name, false);
            }
            else if (RadioUser.isChecked()) {
                // The client is a User
                presenter.performAuthentication(name, true);
            }
            else {
                showAlert("Please check Admin or User", "Warning");
            }
        }
    }

    /**
     * Set the id site of the User
     * @param id_site user's reference id site
     */
    public void setLocalSiteOfRef(int id_site) {
        this.siteOfRef = id_site;
    }



    /*************************
     * ROOM BOOKING
     *************************/

    /**
     * Connexion as a User
     */
    @Override
    public void showSearchScreenAfterConnect() {
        // Get list of sites
        presenter.performSearchListOfSites();

        // Show search screen layout
        setContentView(R.layout.search_screen);
        setSearchComponents();
    }

    /**
     * Create the components for the Search Rooms page
     */
    public void setSearchComponents() {
        // Get the components
        bookBtn = (Button) findViewById(R.id.buttonReserver);
        visio = (CheckBox) findViewById(R.id.checkBoxVisio);
        telephone = (CheckBox) findViewById(R.id.checkBoxTelephone);
        digilab = (CheckBox) findViewById(R.id.checkBoxDigilab);
        secured = (CheckBox) findViewById(R.id.checkBoxSecurite);
        description = (EditText) findViewById(R.id.editTextDesc);
        numOfCollab = (EditText) findViewById(R.id.editTextNbCollab);
        listRooms = (ListView) findViewById(R.id.listAvailableRooms);
        dateBeginText = (TextView) findViewById(R.id.editDateBegin);
        timeBegin = (TextView) findViewById(R.id.editTimeBegin);
        timeEnd = (TextView) findViewById(R.id.editTimeEnd);

        // Set the components
        telephone.setChecked(false);
        digilab.setChecked(false);
        secured.setChecked(false);
        visio.setChecked(false);
        description.setText(null);
        numOfCollab.setText("0");

        // List of available rooms and assign adapter
        ArrayList<String> modelroom = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelroom);
        listRooms.setAdapter(adapter);

        // Set the current date
        setTimeandDate();
    }

    /**
     * Date of the day
     */
    public void setTimeandDate() {
        // Date
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateBeginText.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year));

        // Time
        Calendar mcurrentTime = Calendar.getInstance();
        hourstart = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minutestart = mcurrentTime.get(Calendar.MINUTE) + 5;
        minuteend = minutestart;
        hourend = (hourstart + 1) % 24;
        timeBegin.setText(hourstart + ":" + minutestart);
        timeEnd.setText((hourend) + ":" + minuteend);
    }

    /**
     * The user selects the date when he wants to book a room
     * @param view search_screen
     */
    public void setDate(View view) {
        DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                month = selectedmonth;
                day = selectedday;
                year = selectedyear;
                dateBeginText.setText("" + month + 1 + "/" + day + "/" + year);
            }
        }, year, month, day);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    /**
     * The user selects the begin date of the reservation
     * @param view the search_screen view
     */
    public void setTimeBegin(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        hourstart = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minutestart = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hourstart = selectedHour;
                minutestart = selectedMinute;
                timeBegin.setText(hourstart + ":" + minutestart);
            }
        }, hourstart, minutestart, true); //Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    /**
     * The user selects the end date of the reservation
     * @param view the search_screen view
     */
    public void setTimeEnd(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        hourend = mcurrentTime.get(Calendar.HOUR_OF_DAY) + 1;
        minuteend = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hourend = selectedHour;
                minuteend = selectedMinute;
                timeEnd.setText(hourend + ":" + minuteend);
            }
        }, hourend, minuteend, true); //Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    /**
     * The user clicks on SEARCH button
     * @param view the search_screen view
     */
    public void clickOnSearchRooms(View view) {
        // Transformation of the util.date to sql.date
        Calendar calbegin = Calendar.getInstance();
        calbegin.set(Calendar.HOUR_OF_DAY, hourstart);
        calbegin.set(Calendar.MINUTE, minutestart);
        calbegin.set(Calendar.DAY_OF_MONTH, day);
        calbegin.set(Calendar.MONTH, month);
        calbegin.set(Calendar.YEAR, year);

        Calendar calend = Calendar.getInstance();
        calend.set(Calendar.HOUR_OF_DAY, hourend);
        calend.set(Calendar.MINUTE, minuteend);
        calend.set(Calendar.DAY_OF_MONTH, day);
        calend.set(Calendar.MONTH, month);
        calend.set(Calendar.YEAR, year);

        java.util.Date utilDatebegin = calbegin.getTime();
        java.util.Date utilDateend = calend.getTime();

        datebegin = new Date(utilDatebegin.getTime());
        dateend = new Date(utilDateend.getTime());

        // Test user inputs
        String desc = String.valueOf(description.getText());
        int numC = Integer.parseInt(numOfCollab.getText().toString());
        if (utilDatebegin.before(Calendar.getInstance().getTime())) {
            showAlert("Date must be > Today", "Warning");
        }
        else if ((hourstart > hourend) || ((hourstart == hourend) && (minutestart > minuteend))) {
            showAlert("Date begin must be < Date end", "Warning");
        }
        else if (desc.isEmpty()) {
            showAlert("Description can't be empty", "Warning");
        }
        else if (numC < 3) {
            showAlert("Can't book a room if you are less then 3 coworkers", "Warning");
        }
        else {
            // Search available rooms according to user inputs
            presenter.performSearchRoom(desc, datebegin, hourstart, minutestart, dateend, hourend, minuteend, numC, visio.isChecked(), telephone.isChecked(), secured.isChecked(), digilab.isChecked());
        }
    }

    /**
     * Returns the list of acailable rooms according to the user specifications
     * @param rooms list of avilable rooms
     */
    @Override
    public void listOfAvailableRooms(ArrayList<String> rooms) {
        // Set the available rooms list
        ArrayList<String> modelroom = new ArrayList<>();
        for (String r : rooms) {
            modelroom.add(r);
        }

        // Assign adapter to the available rooms list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelroom);
        listRooms = (ListView) findViewById(R.id.listAvailableRooms);
        listRooms.setAdapter(adapter);

        // Set the listener when we select an available room from the list
        listRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roomToBook = (String) (listRooms.getItemAtPosition(position));
            }
        });

        // Set Book button enabled
        bookBtn.setEnabled(true);
    }

    /**
     * Click on the Book button after selected an available room
     * @param view search_screen
     */
    public void bookRoom(View view) {
        // Book the room
        presenter.performBookRoom(roomToBook, String.valueOf(description.getText()), datebegin, hourstart, minutestart, dateend, hourend, minuteend, Integer.parseInt(String.valueOf(numOfCollab.getText())));
    }

    /**
     * The selected available room is booked
     */
    @Override
    public void roomBooked() {
        // Set Book button disabled
        bookBtn.setEnabled(false);

        // Display success
        showAlert("Room Successfully Booked", "DONE");
        setSearchComponents();
    }



    /*************************
     * PROFIL MANAGEMENT
     *************************/

    /**
     * When we are on the search creen layout,
     * and we want to change the site of ref by clicking on PM button
     * @param view search_screen
     */
    public void showManageScreen(View view) {
        // Show profile management layout
        setContentView(R.layout.profile_management);
        setManageComponents();

        // Set the sites list
        ArrayList<Site> lsite = presenter.getSiteList();
        ArrayList<String> modelsite = new ArrayList<>();
        for (Site s : lsite) {
            modelsite.add(s.getName_site());
        }

        // Assign adapter to the sites list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        ListView listSites = (ListView) findViewById(R.id.listSites);
        listSites.setAdapter(adapter);

        // Set the listener when we select a site from the list
        listSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setLocalSiteOfRef(position + 1);
            }
        });

        /* Select the current site of ref : doesn't work in touch mode...
        todo: try a solution
        listSites.setFocusable(true);
        listSites.setFocusableInTouchMode(true);
        listSites.invalidate();
        listSites.setItemChecked(siteOfRef - 1, true);
        listSites.setSelection(siteOfRef - 1);
        listSites.performItemClick(listSites, siteOfRef - 1, listSites.getItemIdAtPosition(siteOfRef - 1));
        listSites.requestFocus(); */

        // Todo: add the user's reservations
    }

    /**
     * Create the components for the Search Rooms page
     */
    public void setManageComponents() {
        // Get the components
        TextView currentSite = (TextView) findViewById(R.id.textCurrentSite);

        // Set the components
        currentSite.setText(presenter.getCurrentSite());
    }

    /**
     * We have saved the reference site chosen by the user
     */
    @Override
    public void localisationSaved() {
        // Show search screen layout
        setContentView(R.layout.search_screen);
        setSearchComponents();
    }


    /**
     * When we are on the manage profile layout,
     * we have selected a site of ref
     * and we save this site by clicking on R button
     * @param view profile_management
     */
    public void changeSiteOfReference(View view) {
        // Change site of ref presenter with the new site of ref
        presenter.performSaveLocalisationSite(siteOfRef);
    }



    /*************************
     * GENERAL INFO
     *************************/

    /**
     * Show the general info page after connexion as an admin and calculate the ratios
     */
    @Override
    public void showGeneralInfoPage(View view) {
        // Calculate the stats
        ArrayList<Integer> info = presenter.performGeneralInfo();
        int nbSites = info.get(0);
        int nbRooms = info.get(1);
        int nbReservations = info.get(2);

        // Show general info layout page with stats
        setContentView(R.layout.general_info);
        setGeneralInfoComponents(nbSites, nbRooms, nbReservations);
    }

    /**
     * Create the components for the Login page
     */
    public void setGeneralInfoComponents(int nbSite, int nbRooms, int nbReservations) {
        // Get the components
        TextView nbSitesView = (TextView) findViewById(R.id.editTextNbSites);
        TextView nbRoomsView = (TextView) findViewById(R.id.editTextNbSalles);
        TextView nbReservationView = (TextView) findViewById(R.id.editTextNbReservations);

        // Set the components
        nbSitesView.setText(String.valueOf(nbSite));
        nbRoomsView.setText(String.valueOf(nbRooms));
        nbReservationView.setText(String.valueOf(nbReservations));
    }



    /*************************
     * SITE MANAGEMENT
     *************************/

    public void showSiteManagementPage(View view) {
        // Get list of sites
        presenter.performSearchListOfSites();

        // Show site management layout
        setContentView(R.layout.site_management);
        setSiteManagementComponents();

        // Set the sites list
        ArrayList<Site> lsite = presenter.getSiteList();
        final ArrayList<String> modelsite = new ArrayList<>();
        for (Site s : lsite) {
            modelsite.add(s.getName_site());
        }

        // Assign adapter to the sites list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        ListView listSites = (ListView) findViewById(R.id.listViewSM);
        listSites.setAdapter(adapter);

        // Set the listener when we select a site from the list
        listSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the site's name
                String site = modelsite.get(position);
                setNameSiteManagement(site);

                // Set the buttons to enabled
                modifySiteBtn.setEnabled(true);
                infoSiteBtn.setEnabled(true);
                deleteSiteBtn.setEnabled(true);
            }
        });
    }

    /**
     * Create the components for the Site management page
     */
    public void setSiteManagementComponents() {
        // Get the components
        modifySiteBtn = (Button) findViewById(R.id.buttonMofifySM);
        infoSiteBtn   = (Button) findViewById(R.id.buttonInfoSM);
        deleteSiteBtn = (Button) findViewById(R.id.buttonDelSM);
    }

    /**
     * Set the site's name for management
     * @param name_site site's name
     */
    public void setNameSiteManagement(String name_site) {
        this.nameSiteMngt = name_site;
    }

    /**
     * Click on delete button after select a site
     * @param view site_management view
     */
    public void clickOnDeleteSite(View view) {
        presenter.performDeleteSite(nameSiteMngt);
    }

    /**
     * Selected site has be removed from the DataBase
     */
    @Override
    public void suppressionSiteSucceed() {
        showAlert("Site successfully deleted", "DONE");
        showSiteManagementPage(null);
    }



    /*************************
     * ADD/MODIFY/INFO SITE
     *************************/

    /**
     * Show add site layout
     * @param view site_management layout
     */
    public void showAddSitePage(View view) {
        // Show the add site layout
        setContentView(R.layout.add_modify_info_site);
        setAddSiteComponents();

        // To know which action to perform for the save button : add
        this.whichSaveBtnSite = 1;
    }

    /**
     * Show modify site layout
     * @param view site_management layout
     */
    public void showModifySitePage(View view) {
        // Show the modify site layout
        setContentView(R.layout.add_modify_info_site);
        setModifySiteComponents();

        // To know which action to perform for the save button : modify
        this.whichSaveBtnSite = 2;
    }

    /**
     * Show info site layout
     * @param view site_management layout
     */
    public void showInfoSitePage(View view) {
        // Show the info site layout
        setContentView(R.layout.add_modify_info_site);
        setInfoSiteComponents();
    }

    /**
     * Create the components for the Add site page
     */
    public void setAddSiteComponents() {
        // Get the components
        saveSiteMngt   = (Button)   findViewById(R.id.buttonSaveSM);
        cancelOkSiteMngt = (Button)   findViewById(R.id.buttonCancelSM);
        nameSite       = (EditText) findViewById(R.id.editTextNameSiteSM);
        nbRoomsSite    = (EditText) findViewById(R.id.editTextNb_RoomsSM);
        addrSite       = (EditText) findViewById(R.id.editTextAddrSM);
        titlePageSite  = (TextView) findViewById(R.id.titlePageSite);

        // Set the components
        saveSiteMngt.setText("Save");
        cancelOkSiteMngt.setText("Cancel");
        titlePageSite.setText(getResources().getString(R.string.add_site));
    }

    /**
     * Create the components for the Modify site page
     */
    public void setModifySiteComponents() {
        // Get the components
        saveSiteMngt   = (Button)   findViewById(R.id.buttonSaveSM);
        cancelOkSiteMngt = (Button)   findViewById(R.id.buttonCancelSM);
        nameSite       = (EditText) findViewById(R.id.editTextNameSiteSM);
        nbRoomsSite    = (EditText) findViewById(R.id.editTextNb_RoomsSM);
        addrSite       = (EditText) findViewById(R.id.editTextAddrSM);
        titlePageSite  = (TextView) findViewById(R.id.titlePageSite);

        // Set the components
        saveSiteMngt.setText("Save");
        cancelOkSiteMngt.setText("Cancel");
        titlePageSite.setText(getResources().getString(R.string.modify_site));

        // Get the site's info
        presenter.performInfoSite(this.nameSiteMngt);
    }

    /**
     * Create the components for the Info site page
     */
    public void setInfoSiteComponents() {
        // Get the components
        saveSiteMngt   = (Button)   findViewById(R.id.buttonSaveSM);
        cancelOkSiteMngt = (Button)   findViewById(R.id.buttonCancelSM);
        nameSite       = (EditText) findViewById(R.id.editTextNameSiteSM);
        nbRoomsSite    = (EditText) findViewById(R.id.editTextNb_RoomsSM);
        addrSite       = (EditText) findViewById(R.id.editTextAddrSM);
        titlePageSite  = (TextView) findViewById(R.id.titlePageSite);

        // Set the components
        titlePageSite.setText(getResources().getString(R.string.info_site));
        saveSiteMngt.setVisibility(View.INVISIBLE);
        cancelOkSiteMngt.setText("OK");
        nameSite.setEnabled(false);
        nbRoomsSite.setEnabled(false);
        addrSite.setEnabled(false);

        // Get the site's info
        presenter.performInfoSite(this.nameSiteMngt);
    }

    /**
     * Save the new/modify site
     * @param view add_modify_info site layout
     */
    public void clickOnSaveSite(View view) {
        // Get the fields
        String nameSiteStr    = String.valueOf(nameSite.getText());
        String nbRoomsSiteStr = String.valueOf(nbRoomsSite.getText());
        String addrSiteStr    = String.valueOf(addrSite.getText());

        // Test user inputs
        if(nameSiteStr.isEmpty()) {
            showAlert("Site's name can't be empty", "Warning");
        }
        else if(nbRoomsSiteStr.isEmpty()) {
            showAlert("Site's number of rooms can't be empty", "Warning");
        }
        else if(addrSiteStr.isEmpty()) {
            showAlert("Site's address can't be empty", "Warning");
        }
        else {
            int nbRoomsSiteInt = Integer.parseInt(nbRoomsSite.getText().toString());

            // Perform the action depending on which one : add or modify
            if (this.whichSaveBtnSite == 1) {
                presenter.performNewSite(nameSiteStr, nbRoomsSiteInt, addrSiteStr);
            } else if (this.whichSaveBtnSite == 2) {
                presenter.performModifySite(this.nameSiteMngt, nameSiteStr, nbRoomsSiteInt, addrSiteStr);
            } else {
                System.out.println("ERROR");
            }
        }
    }

    /**
     * Site has been added or modified
     */
    @Override
    public void siteAddedOrModified() {
        // Display success
        if (this.whichSaveBtnSite == 1) {
            showAlert("Site successfully added", "DONE");
        }
        else if (this.whichSaveBtnSite == 2) {
            showAlert("Site successfully modified", "DONE");
        }

        // Show site management layout
        showSiteManagementPage(null);
    }

    /**
     * Process the site info to display
     * @param nb_salles_site site's number of rooms
     * @param address_sites site's address
     */
    @Override
    public void infoSite(int nb_salles_site, String address_sites) {
        nameSite.setText(this.nameSiteMngt);
        nbRoomsSite.setText(String.valueOf(nb_salles_site));
        addrSite.setText(address_sites);
    }



    /*************************
     * ROOM MANAGEMENT
     *************************/

    public void showRoomManagementPage(View view) {
        // Show room management layout
        setContentView(R.layout.room_management);
        setRoomManagementComponents();
    }

    public void setRoomManagementComponents() {
        // Get the components
        modifyRoomBtn = (Button) findViewById(R.id.buttonMofifyRM);
        infoRoomBtn   = (Button) findViewById(R.id.buttonInfoRM);
        deleteRoomBtn = (Button) findViewById(R.id.buttonDelRM);

        // Set the components
    }

    public void setNameRoomManagement(String name_room) {
        this.nameRoomMngt = name_room;
    }

    public void clickOnDeleteRoom(View view) {
        presenter.performDeleteRoom(/*TODO id_room*/ 1);
    }

    @Override
    public void suppressionRoomSucceed() {
        showAlert("Room successfully deleted", "DONE");
    }



    /*************************
     * ADD/MODIFY/INFO ROOM
     *************************/

    /**
     * Show add room layout
     * @param view room_management layout
     */
    public void showAddRoomPage(View view) {
        setContentView(R.layout.add_modify_info_room);
        setAddRoomComponents();
    }

    /**
     * Show modify room layout
     * @param view room_management layout
     */
    public void showModifyRoomPage(View view) {
        setContentView(R.layout.add_modify_info_room);
        setModifyRoomComponents();
    }

    /**
     * Show info room layout
     * @param view room_management layout
     */
    public void showInfoRoomPage(View view) {
        setContentView(R.layout.add_modify_info_room);
        setInfoRoomComponents();
    }

    /**
     * Create the components for the Add room page
     */
    public void setAddRoomComponents() {
        titlePageRoom = (TextView) findViewById(R.id.titlePageRoom);
        titlePageRoom.setText(getResources().getString(R.string.add_room));
    }

    /**
     * Create the components for the Modify room page
     */
    public void setModifyRoomComponents() {
        titlePageRoom = (TextView) findViewById(R.id.titlePageRoom);
        titlePageRoom.setText(getResources().getString(R.string.modify_room));
    }

    /**
     * Create the components for the Info room page
     */
    public void setInfoRoomComponents() {
        titlePageRoom = (TextView) findViewById(R.id.titlePageRoom);
        titlePageRoom.setText(getResources().getString(R.string.info_room));
    }

    /**
     * Save the new/modify room
     * @param view add_modify_info room layout
     */
    public void clickOnSaveRoom(View view) {
        // Get the fields

        // Perform the action depending on which one : add or modify
        if (this.whichSaveBtnRoom == 1) {
            //presenter.performNewRoom();
        }
        else if (this.whichSaveBtnRoom == 2) {
            //presenter.performModifyRoom();
        }
        else {
            System.out.println("ERROR");
        }
    }

    /**
     * Room has been added or modified
     */
    @Override
    public void roomAddedOrModified() {
        // Display success
        if (this.whichSaveBtnRoom == 1) {
            showAlert("Room successfully added", "DONE");
        }
        else if (this.whichSaveBtnRoom == 2) {
            showAlert("Room successfully modified", "DONE");
        }

        // Show room management layout
        showRoomManagementPage(null);
    }

    /**
     * Process the room info to display
     * @param num_room room's number
     * @param name_room room's name
     * @param capacity room's capacity
     * @param floor room's floor
     * @param visio room's visio
     * @param phone room's phone
     * @param secu room's security
     * @param digilab room's digilab
     */
    @Override
    public void infoRoom(int num_room, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }



    /*************************
     * RESERVATION MANAGEMENT
     *************************/

    public void showReservationManagementPage(View view) {
        // Show site management layout
        setContentView(R.layout.reservation_management);
        setReservationManagementComponents();

        // Set the reservation list
        ArrayList<Reservation> lreservation = presenter.getReservationList();
        final ArrayList<String> modelreservation = new ArrayList<>();
        for (Reservation r : lreservation) {
            modelreservation.add(r.getRoom().getName_room() + " - " + r.getDateBegin() + "/" + r.getDateEnd());
        }

        // Assign adapter to the sites list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelreservation);
        ListView listReservations = (ListView) findViewById(R.id.listViewSM);
        listReservations.setAdapter(adapter);

        // Set the listener when we select a site from the list
        listReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the site's name
                String reservation = modelreservation.get(position);
                setNameReservationManagement(reservation);

                // Set the buttons to enabled
            }
        });
    }

    /**
     * Create the components for the Reservation management page
     */
    public void setReservationManagementComponents() {
        // Get the components
        infoReservationBtn   = (Button) findViewById(R.id.buttonInfoRM);
        deleteReservationBtn = (Button) findViewById(R.id.buttonDelRM);
    }

    /**
     * Set the reservation's name for management
     * @param name_reservation reservation's name
     */
    public void setNameReservationManagement(String name_reservation) {
        this.nameReservationMngt = name_reservation;
    }

    /**
     * Click on delete button after select a reservation
     * @param view reservation_management view
     */
    public void clickOnDeleteReservation(View view) {
        presenter.performDeleteReservation(nameReservationMngt);
    }

    /**
     * Selected reservation has be removed from the DataBase
     */
    @Override
    public void suppressionReservationSucceed() {
        showAlert("Reservation successfully deleted", "DONE");
        showReservationManagementPage(null);
    }



    /*************************
     * INFO RESERVATION
     *************************/

    /**
     * Show info reservation layout
     * @param view reservation_management layout
     */
    public void showInfoReservationPage(View view) {
        // Show the add reservation layout
        setContentView(R.layout.info_reservation);
        setInfoReservationComponents();
    }

    /**
     * Create the components for the Info site page
     */
    public void setInfoReservationComponents() {
        // Get the components
        okReservationMngt = (Button)   findViewById(R.id.buttonOkRM);
        siteReserv        = (EditText) findViewById(R.id.textViewSiteIR);
        roomReserv        = (EditText) findViewById(R.id.textViewRoomIR);
        collabReserv      = (EditText) findViewById(R.id.textViewCollabIR);
        descrReserv       = (EditText) findViewById(R.id.textViewDescriptionIR);
        nbCollabReserv    = (EditText) findViewById(R.id.textViewCollabNbIR);
        dateBeginReserv   = (EditText) findViewById(R.id.textViewBeginIR);
        dateEndReserv     = (EditText) findViewById(R.id.textViewEndIR);

        // Get the site's info
        presenter.performInfoSite(this.nameSiteMngt);
    }

    /**
     * Process the reservation info to display
     */
    @Override
    public void infoReservation(String site, String room, String collab, String description, int nbCollab, String dateBegin, String dateEnd) {
        siteReserv.setText(site);
        roomReserv.setText(room);
        collabReserv.setText(collab);
        descrReserv.setText(description);
        nbCollabReserv.setText(String.valueOf(nbCollab));
        dateBeginReserv.setText(dateBegin);
        dateEndReserv.setText(dateEnd);
    }



    /*************************
     * USER INTERACTION
     *************************/

    public void showAlert(String message, String Title) {
        new AlertDialog.Builder(this).setTitle(Title).setMessage(message).setNeutralButton("Close", null).show();
    }
}
