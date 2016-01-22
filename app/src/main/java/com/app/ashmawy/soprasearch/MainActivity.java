package com.app.ashmawy.soprasearch;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
    private TextView dateBeginText;
    private TextView timeBegin;
    private TextView timeEnd;
    private TextView titlePageSite;
    private TextView titlePageRoom;
    private EditText description;
    private EditText numOfCollab;
    private CheckBox visio;
    private CheckBox secured;
    private CheckBox digilab;
    private CheckBox telephone;
    private Button bookBtn;
    private Button infoSiteBtn;
    private Button modifySiteBtn;
    private Button deleteSiteBtn;
    private Button infoRoomBtn;
    private Button modifyRoomBtn;
    private Button deleteRoomBtn;
    private Button saveSiteMngt;
    private Button saveRoomMngt;
    private Button cancelSiteMngt;
    private Button cancelRoomMngt;
    private EditText nameSite;
    private EditText nbRoomsSite;
    private EditText addrSite;
    private int hourstart ;
    private int minutestart;
    private int hourend ;
    private int minuteend;
    private int year, month, day;
    Date datebegin ;
    Date dateend;
    private Presenter presenter;
    private DataBase DB;
    private ListView listRooms;
    private int siteOfRef;
    private String nameSiteMngt;
    private String nameRoomMngt;
    private String RoomToBook;
    private int whichSaveBtnSite = 0;
    private int whichSaveBtnRoom = 0;



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
        DB = new DataBase(this) ;
        presenter = new Presenter();
        presenter.setGUIOutput(this);
        presenter.setDBOutput(DB);
        DB.setDBListener(presenter);
    }

    public void showLoginPage(View view) {
        setContentView(R.layout.login);
        setLoginComponents();
    }

    /**
     * Create the components for the Login page
     */
    public void setLoginComponents() {
        RadioAdmin  = (RadioButton) findViewById(R.id.radio_Admin);
        RadioUser   = (RadioButton) findViewById(R.id.radio_User);
        username    = (EditText)    findViewById(R.id.editTextLogin);
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
     * @param view the login view
     */
    public void connectOnclick(View view) {
        String name = String.valueOf(username.getText());

        // Tester ici si le champs user est vide
        if (name.equals("")) {
            showAlert("Please fill Login","Warning");
        } else {
            // Depending on User or Admin, check if the client has the right to access to the appropriated page
            if (RadioAdmin.isChecked()) {
                // The client is an admin
                presenter.performAuthentication(name, false);
            } else if (RadioUser.isChecked()) {
                // The client is a User
                presenter.performAuthentication(name, true);
            } else {
                showAlert("Please check Admin or User","Warning");
            }
        }
    }


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
        setContentView(R.layout.search_screen);
        setSearchComponents();
    }

    /**
     * Create the components for the Search Rooms page
     */
    public void setSearchComponents() {
        description     = (EditText) findViewById(R.id.editTextDesc);
        description.setText(null);
        numOfCollab     = (EditText)findViewById(R.id.editTextNbCollab);
        numOfCollab.setText("0");
        visio           = (CheckBox) findViewById(R.id.checkBoxVisio);
        visio.setChecked(false);
        telephone       = (CheckBox) findViewById(R.id.checkBoxTelephone);
        telephone.setChecked(false);
        digilab         = (CheckBox) findViewById(R.id.checkBoxDigilab);
        digilab.setChecked(false);
        secured         = (CheckBox) findViewById(R.id.checkBoxSecurite);
        secured.setChecked(false);
        dateBeginText       = (TextView) findViewById(R.id.editDateBegin);
        timeBegin       = (TextView) findViewById(R.id.editTimeBegin);
        timeEnd         = (TextView) findViewById(R.id.editTimeEnd);
        listRooms=(ListView) findViewById(R.id.listAvailableRooms);
        bookBtn = (Button) findViewById(R.id.buttonReserver);

        ArrayList<String> modelroom = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelroom);
        // Assign adapter to ListView
        listRooms.setAdapter(adapter);
        setTimeandDate();
    }

    /**
     * Date of the day
     */
    public void setTimeandDate(){

        // Date
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateBeginText.setText(new StringBuilder().append(day).append("/").append(month+1).append("/").append(year));

        // Time
        Calendar mcurrentTime = Calendar.getInstance();
        hourstart = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minutestart = mcurrentTime.get(Calendar.MINUTE)+5;
        minuteend=minutestart;
        hourend=(hourstart+1)%24;
        timeBegin.setText(hourstart + ":" + minutestart);
        timeEnd.setText((hourend) + ":" + minuteend);
    }

    /**
     * The user selects the date when he wants to book a room
     * @param view search_screen
     */
    public void setDate(View view) {
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                month = selectedmonth;
                day=selectedday;
                year=selectedyear;
                dateBeginText.setText("" + month +1+ "/" + day + "/" + year);
            }
        }, year, month, day);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    /**
     * The user selects the begin date of the reservation
     * @param view the search_screen view
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
     * @param view search_screen
     */
    public void clickOnSearchRooms(View view) {

        /************transformation of the util.date to sql.date**********/
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

        datebegin = new java.sql.Date(utilDatebegin.getTime());
        dateend = new java.sql.Date(utilDateend.getTime());
        /****************************************************************************/

        String desc = String.valueOf(description.getText());
        int numC = Integer.parseInt(numOfCollab.getText().toString());
        if (utilDatebegin.before(Calendar.getInstance().getTime())) {
            showAlert("Date must be > Today","Warning");
        } else if ((hourstart>hourend) || ((hourstart==hourend) && (minutestart>minuteend))){
            showAlert("Date begin must be < Date end","Warning");
        } else if (desc.isEmpty()) {
            showAlert("Description can't be empty","Warning");
        } else if (numC < 3) {
            showAlert("Can't book a room if you are less then 3 coworkers","Warning");
        } else {
            presenter.performSearchRoom(desc, datebegin,hourstart,minutestart, dateend,hourend,minuteend, numC, visio.isChecked(), telephone.isChecked(), secured.isChecked(), digilab.isChecked());
        }
    }

    /**
     * Returns the list of acailable rooms according to the user specifications
     * @param rooms list of avilable rooms
     */
    @Override
    public void listOfAvailableRooms(ArrayList<String> rooms) {
        bookBtn.setEnabled(true);
        ArrayList<String> modelroom = new ArrayList<>();
        for(String r: rooms){
            modelroom.add(r);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelroom);
        listRooms = (ListView) findViewById(R.id.listAvailableRooms);
        // Assign adapter to ListView
        listRooms.setAdapter(adapter);
        listRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoomToBook = (String) (listRooms.getItemAtPosition(position));
            }
        });
    }

    public void bookRoom(View view) {
        presenter.performBookRoom(RoomToBook, String.valueOf(description.getText()), datebegin, hourstart, minutestart, dateend, hourend, minuteend, Integer.parseInt(String.valueOf(numOfCollab.getText())));
    }

    /**
     * The selected room is booked
     */
    @Override
    public void roomBooked() {
        bookBtn.setEnabled(false);
        showAlert("Room Successfully Booked", "DONE");
        setSearchComponents();
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
        setContentView(R.layout.profile_management);
        setManageComponents();

        ArrayList<Site> lsite = presenter.getSiteList();
        ArrayList<String> modelsite = new ArrayList<>();
        for(Site s: lsite){
            modelsite.add(s.getName_site());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        ListView listSites = (ListView) findViewById(R.id.listSites);

        // Assign adapter to ListView
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
    }

    /**
     * Create the components for the Search Rooms page
     */
    public void setManageComponents() {
        // Set the current site
        TextView currentSite = (TextView) findViewById(R.id.textCurrentSite);
        currentSite.setText(presenter.getCurrentSite());
    }

    /**
     * On a enregistre le site de reference choisi par l'utilisateur
     */
    @Override
    public void localisationSaved() {
        // On affiche la page de recherche de salle
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
        // Calculate the informations
        ArrayList<Integer> info = presenter.performGeneralInfo();
        int nbSites = info.get(0);
        int nbRooms = info.get(1);
        int nbReservations = info.get(2);

        // On affiche la page avec les resultats
        setContentView(R.layout.general_info);
        setGeneralInfoComponents(nbSites, nbRooms, nbReservations);
    }

    /**
     * Create the components for the Login page
     */
    public void setGeneralInfoComponents(int nbSite, int nbRooms, int nbReservations) {
        TextView nbSitesView        = (TextView) findViewById(R.id.editTextNbSites);
        TextView nbRoomsView        = (TextView) findViewById(R.id.editTextNbSalles);
        TextView nbReservationView  = (TextView) findViewById(R.id.editTextNbReservations);

        // Set texts of info
        nbSitesView.setText(String.valueOf(nbSite));
        nbRoomsView.setText(String.valueOf(nbRooms));
        nbReservationView.setText(String.valueOf(nbReservations));
    }





    /*************************
     * SITE MANAGEMENT
     *************************/

    public void showSiteManagementPage(View view) {
        setContentView(R.layout.site_management);
        setSiteManagementComponents();

        ArrayList<Site> lsite = presenter.getSiteList();
        final ArrayList<String> modelsite = new ArrayList<>();
        for(Site s: lsite){
            modelsite.add(s.getName_site());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, modelsite);
        ListView listSites = (ListView) findViewById(R.id.listViewSM);

        // Assign adapter to ListView
        listSites.setAdapter(adapter);

        // Set the listener when we select a site from the list
        listSites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String site = modelsite.get(position);
                System.out.println("NAME SITE : " + site);
                setNameSiteManagement(site);

                modifySiteBtn.setEnabled(true);
                infoSiteBtn.setEnabled(true);
                deleteSiteBtn.setEnabled(true);
            }
        });
    }

    public void setSiteManagementComponents() {
        modifySiteBtn = (Button) findViewById(R.id.buttonMofifySM);
        infoSiteBtn = (Button) findViewById(R.id.buttonInfoSM);
        deleteSiteBtn = (Button) findViewById(R.id.buttonDelSM);
    }

    public void setNameSiteManagement(String name_site) {
        this.nameSiteMngt = name_site;
    }

    public void clickOnDeleteSite(View view) {
        presenter.performDeleteSite(nameSiteMngt);
    }

    @Override
    public void suppressionSiteSucceed() {
        showAlert("Site successfully deleted", "DONE");
        showSiteManagementPage(null);
    }

    @Override
    public void infoSite(String name_site,  int nb_salles_site, String address_sites) {

    }



    /*************************
     * ADD/MODIFY/INFO SITE
     *************************/

    public void showAddSitePage(View view) {
        setContentView(R.layout.add_modify_info_site);
        setAddSiteComponents();
        this.whichSaveBtnSite = 1;
    }

    public void showModifySitePage(View view) {
        setContentView(R.layout.add_modify_info_site);
        setModifySiteComponents();
        this.whichSaveBtnSite = 2;
    }

    public void showInfoSitePage(View view) {
        setContentView(R.layout.add_modify_info_site);
        setInfoSiteComponents();
    }

    public void setAddSiteComponents() {
        titlePageSite = (TextView) findViewById(R.id.titlePageSite);
        titlePageSite.setText(getResources().getString(R.string.add_site));
        saveSiteMngt = (Button) findViewById(R.id.buttonSaveSM);
        saveSiteMngt.setText("Save");
        cancelSiteMngt = (Button) findViewById(R.id.buttonCancelSM);
        cancelSiteMngt.setText("Cancel");
        nameSite = (EditText) findViewById(R.id.editTextNameSiteSM);
        nbRoomsSite = (EditText) findViewById(R.id.editTextNb_RoomsSM);
        addrSite = (EditText) findViewById(R.id.editTextAddrSM);
    }

    public void setModifySiteComponents() {
        titlePageSite = (TextView) findViewById(R.id.titlePageSite);
        titlePageSite.setText(getResources().getString(R.string.modify_site));
        saveSiteMngt = (Button) findViewById(R.id.buttonSaveSM);
        saveSiteMngt.setText("Save");
        cancelSiteMngt = (Button) findViewById(R.id.buttonCancelSM);
        cancelSiteMngt.setText("Cancel");
        nameSite = (EditText) findViewById(R.id.editTextNameSiteSM);
        nbRoomsSite = (EditText) findViewById(R.id.editTextNb_RoomsSM);
        addrSite = (EditText) findViewById(R.id.editTextAddrSM);
    }

    public void setInfoSiteComponents() {
        titlePageSite = (TextView) findViewById(R.id.titlePageSite);
        titlePageSite.setText(getResources().getString(R.string.info_site));
        saveSiteMngt = (Button) findViewById(R.id.buttonSaveSM);
        saveSiteMngt.setVisibility(View.INVISIBLE);
        cancelSiteMngt = (Button) findViewById(R.id.buttonCancelSM);
        cancelSiteMngt.setText("OK");
    }

    public void clickOnSaveSite(View view) {
        String nameSiteStr = String.valueOf(nameSite.getText());
        int nbRoomsSiteInt = Integer.parseInt(nbRoomsSite.getText().toString());
        String addrSiteStr = String.valueOf(addrSite.getText());

        if(this.whichSaveBtnSite == 1) {
            presenter.performNewSite(nameSiteStr, nbRoomsSiteInt, addrSiteStr);
        }
        else if(this.whichSaveBtnSite == 2) {
            presenter.performModifySite(this.nameSiteMngt, nameSiteStr, nbRoomsSiteInt, addrSiteStr);
        }
        else {
            System.out.println("ERROR");
        }
    }

    @Override
    public void siteAddedOrModified() {
        if(this.whichSaveBtnSite == 1) {
            showAlert("Site successfully added", "DONE");
        }
        else if(this.whichSaveBtnSite == 2) {
            showAlert("Site successfully modified", "DONE");
        }
        showSiteManagementPage(null);
    }



    /*************************
     * ROOM MANAGEMENT
     *************************/

    public void showRoomManagementPage(View view) {
        setContentView(R.layout.room_management);
        setRoomManagementComponents();
    }

    public void setRoomManagementComponents() {
        modifyRoomBtn = (Button) findViewById(R.id.buttonMofifyRM);
        infoRoomBtn = (Button) findViewById(R.id.buttonInfoRM);
        deleteRoomBtn = (Button) findViewById(R.id.buttonDelRM);
    }

    public void setNameRoomManagement(String name_room) {
        this.nameRoomMngt = name_room;
    }

    public void clickOnDeleteRoom(View view) {
        presenter.performDeleteRoom(/*TODO id_room*/ 1);
    }

    @Override
    public void suppressionRoomSucceed() {
        showAlert("Room successfully deleted","DONE");
    }

    @Override
    public void infoRoom(int num_room, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }



    /*************************
     * ADD/MODIFY/INFO ROOM
     *************************/

    public void showAddRoomPage(View view) {
        setContentView(R.layout.add_modify_info_room);
        setAddRoomComponents();
    }

    public void showModifyRoomPage(View view) {
        setContentView(R.layout.add_modify_info_room);
        setModifyRoomComponents();
    }

    public void showInfoRoomPage(View view) {
        setContentView(R.layout.add_modify_info_room);
        setInfoRoomComponents();
    }

    public void setAddRoomComponents() {
        titlePageRoom = (TextView) findViewById(R.id.titlePageRoom);
        titlePageRoom.setText(getResources().getString(R.string.add_room));
    }

    public void setModifyRoomComponents() {
        titlePageRoom = (TextView) findViewById(R.id.titlePageRoom);
        titlePageRoom.setText(getResources().getString(R.string.modify_room));
    }

    public void setInfoRoomComponents() {
        titlePageRoom = (TextView) findViewById(R.id.titlePageRoom);
        titlePageRoom.setText(getResources().getString(R.string.info_room));
    }

    @Override
    public void roomAddedOrModified() {
        showAlert("Room successfully added or modified", "DONE");
    }



    /*************************
     * USER INTERACTION
     *************************/

    public void showAlert(String message,String Title){
        new AlertDialog.Builder(this).setTitle(Title).setMessage(message).setNeutralButton("Close", null).show();
    }

    @Override
    public void testUserInput() {

    }
}
