package com.app.ashmawy.soprasearch.Presenter;

import com.app.ashmawy.soprasearch.DataBase.Model.Room;
import com.app.ashmawy.soprasearch.DataBase.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;
import com.app.ashmawy.soprasearch.Interfaces.DB_Output;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Listener;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASHMAWY on 04-Dec-15.
 */
public class Presenter implements GUI_Listener, DB_Listener {

    /**
     * Attributes
     */

    private DB_Output DB;
    private GUI_Output GUI;
    private int id_site;
    private ArrayList<Site> siteList;

    // Special for General Info page
    private int sitesNb = -1;
    private int roomNb = -1;
    private int reservationNb = -1;


    /**
     * Constructor
     */

    public Presenter() {
        int id_site = 0;
    }



    /**
     * Setter, getter & init
     */

    public void setDBOutput(DB_Output DB) {
        this.DB = DB;
    }

    public void setGUIOutput(GUI_Output GUI) {
        this.GUI = GUI;
    }

    public void processIdSite(int id_site) {
        this.id_site = id_site;
    }



    /*************************
     * METHODS FROM INTERFACE
     *************************/



    /**
     * AUTHENTIFICATION
     */

    @Override
    public void performAuthentication(String nickname, boolean UserOrAdmin) {
        DB.inClientList(nickname, UserOrAdmin);
        DB.searchSites();
    }

    @Override
    public void processResponseAuthentication (boolean accessGranted) {
        if (accessGranted){
            GUI.showSearchScreenAfterConnect();
        } else {
            GUI.showAlert("Access not authorized");
        }
    }



    /**
     * ROOM BOOKING
     */

    /**
     * The User has select the parameters and has clicked on SEARCH button
     * @param desc          description
     * @param begin         date begin
     * @param end           data end
     * @param num_collab    number of collaborators
     * @param visio         visio
     * @param phone         phone
     * @param secu          secu
     * @param digilab       digilab
     */
    @Override
    public void performSearchRoom(String desc, Date begin, Date end, int num_collab, boolean visio, boolean phone, boolean secu, boolean digilab) {
        /*
        if(id_site == 0) {
            GUI.showAlert("No site specified");
        } else {
            // Convert booleans to int
            int particularities = ((visio) ? 8 : 0) + ((phone) ? 4 : 0) + ((secu) ? 2 : 0) + ((digilab) ? 1 : 0);

            // Make the request
            try {
                DB.searchAvailableRooms(id_site, desc, begin, end, num_collab, particularities);
            } catch (SQLException e) {
                GUI.showAlert("Error DataBase");
                e.printStackTrace();
            }
        }
        */
    }

    /**
     * The User has selected the room he wants to Book
     * @param r the room to book
     */
    @Override
    public void performBookRoom(Room r) {

    }

    @Override
    public void processAvailableRooms(int[] id, String[] rooms) {

    }

    @Override
    public void processRoomNotAvailable() {

    }

    @Override
    public void processRoomBooked() {

    }

    @Override
    public void processRoomNotBooked() {

    }


    /**
     * PROFIL MANAGEMENT
     */

    /**
     * On enregistre le site de référence choisi par l'utilisateur dans la DataBase
     * @param id_user
     * @param id_site
     */
    @Override
    public void performSaveLocalisationSite(int id_user, int id_site) {
        DB.updateProfile(id_user, id_site);
    }

    /**
     * On a enregistré le site de référence choisi par l'utilisateur
     */
    @Override
    public void processUpdateProfile() {
        GUI.localisationSaved();
    }



    /**
     * GENERAL INFO
     */

    @Override
    public void performGeneralInfo() {

    }

    @Override
    public void processSitesNb(int nbSites) {

    }

    @Override
    public void processRoomsNb(int nbRooms) {

    }

    @Override
    public void processReservationsNb(int nbReservations) {

    }

    @Override
    public void calcReservationRate() {

    }



    /**
     * SITE MANAGEMENT
     */

    @Override
    public void performDeleteSite(int id_site) {

    }

    @Override
    public void performInfoSite(int id_site) {

    }

    @Override
    public void processListOfSites(ArrayList<Site> sites) {
        this.siteList = new ArrayList<>();
        for(Site s : sites){
            siteList.add(s);
        }
    }

    public ArrayList<Site> getSiteList(){
        return siteList;
    }

    @Override
    public void processSiteDeleted() {

    }

    @Override
    public void processSiteNotDeleted() {

    }

    @Override
    public void processInfoSite(String name_site, int nb_salles_site, String address_site) {

    }



    /**
     * ADD/MODIFY SITE
     */

    @Override
    public void performNewSite(String name_site, int nb_salles_site, String address_site) {

    }

    @Override
    public void performModifySite(int id_site, String name_site, int nb_salles_site, String address_site) {

    }

    @Override
    public void processSiteAddedOrModified() {

    }

    @Override
    public void processSiteNotAddedOrModified() {

    }



    /**
     * ROOM MANAGEMENT
     */

    @Override
    public void performDeleteRoom(int id_room) {

    }

    @Override
    public void performInfoRoom(int id_room) {

    }

    @Override
    public void processListOfRoom(List rooms) {

    }

    @Override
    public void processRoomDeleted() {

    }

    @Override
    public void processRoomNotDeleted() {

    }

    @Override
    public void processInfoRoom(String name_room, int capacity, int floor,  int particularities, int nb_reservations, String name_site) {

    }




    /**
     * ADD/MODIFY ROOM
     */

    @Override
    public void performNewRoom(int num_room, String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }

    @Override
    public void performModifyRoom(int id_room, int num_room, String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }

    @Override
    public void processRoomAddedOrModified() {

    }

    @Override
    public void processRoomNotAddedOrModified() {

    }
}
