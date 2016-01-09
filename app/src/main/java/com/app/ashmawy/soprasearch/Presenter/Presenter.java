package com.app.ashmawy.soprasearch.Presenter;

import com.app.ashmawy.soprasearch.DataBase.Model.Room;
import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;
import com.app.ashmawy.soprasearch.Interfaces.DB_Output;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Listener;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;

import java.sql.Date;
import java.util.List;

/**
 * Created by lenovo on 04-Dec-15.
 */
public class Presenter implements GUI_Listener, DB_Listener {

    /**
     * Attributes
     */

    private DB_Output DB;
    private GUI_Output GUI;



    /**
     * Constructor
     */

    public Presenter() {

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




    /**
     * AUTHENTIFICATION
     */

    @Override
    public void performAuthentication(String nickname, boolean UserOrAdmin) {
        DB.inClientList(nickname, UserOrAdmin);
    }

    @Override
    public void processResponseAuthentication (boolean accessGranted) {
        if (accessGranted){
            GUI.showSearchScreen();
        }else{
            GUI.showAlert("Access not authorized");
        }
    }



    /**
     * ROOM BOOKING
     */

    @Override
    public void performSearchRoom(String desc, Date begin, Date end, int num_collab, boolean visio, boolean phone, boolean secu, boolean digilab) {

    }

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

    @Override
    public void performSaveLocalisationSite(int id_user, int id_site) {

    }

    @Override
    public void processUpdateProfile() {
        GUI.localisationSaved();
    }



    /**
     * GENERAL INFO
     */

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
    public void processListOfSites(List sites) {

    }

    @Override
    public void processSiteDeleted() {

    }

    @Override
    public void processSiteNotDeleted() {

    }

    @Override
    public void processInfoSite(int num_site, String name_site, int nb_salles_site, String address_site) {

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
    public void processInfoRoom(int num_room, String name_room, int floor, int capacity, int particularities) {

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
