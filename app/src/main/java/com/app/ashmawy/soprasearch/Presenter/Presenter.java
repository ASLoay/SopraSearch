package com.app.ashmawy.soprasearch.Presenter;

import com.app.ashmawy.soprasearch.Model.Room;
import com.app.ashmawy.soprasearch.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;
import com.app.ashmawy.soprasearch.Interfaces.DB_Output;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Listener;
import com.app.ashmawy.soprasearch.Interfaces.GUI_Output;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */

public class Presenter implements GUI_Listener, DB_Listener {

    /*************************
     * Attributes
     *************************/

    private DB_Output DB;
    private GUI_Output GUI;
    private int id_site;
    private int id_client;
    private ArrayList<Site> siteList;
    private ArrayList<String> availableRooms=new ArrayList<>();
    private ArrayList<Integer> idAvailaibleRooms=new ArrayList<>();
    private boolean userOrAdmin;
    private ArrayList<Room> roomsList;


    /*************************
     * Constructor
     *************************/

    public Presenter() {
        this.id_site = 0;
        this.id_client = 0;
    }



    /*************************
     * Setter, getter & init
     *************************/

    public void setDBOutput(DB_Output DB) {
        this.DB = DB;
    }

    public void setGUIOutput(GUI_Output GUI) {
        this.GUI = GUI;
    }



    /*************************
     * AUTHENTIFICATION
     *************************/

    @Override
    public void performAuthentication(String nickname, boolean userOrAdmin) {
        this.userOrAdmin = userOrAdmin;
        DB.inClientList(nickname, userOrAdmin);
        DB.searchSites();
    }

    @Override
    public void processResponseAuthentication (boolean accessGranted) {
        if (accessGranted){
            if(userOrAdmin) {
                GUI.showSearchScreenAfterConnect();
            }
            else {
                GUI.showGeneralInfoPageAfterCalcul();
            }
        } else {
            GUI.showAlert("Access not authorized","Warning");
        }
    }

    @Override
    public void processIdSite(int id_site) {
        this.id_site = id_site;
        GUI.setLocalSiteOfRef(id_site);
    }

    @Override
    public void processIdClient(int id_client) {
        this.id_client = id_client;
    }



    /*************************
     * ROOM BOOKING
     *************************/

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
    public void performSearchRoom(String desc, Date begin, int hourstart, int minutestart, Date end,int hourend, int minuteend, int num_collab, boolean visio, boolean phone, boolean secu, boolean digilab) {

        if(id_site == 0) {
            GUI.showAlert("No site specified","Warning");
        } else {
            // Convert booleans to int
            int particularities = ((visio) ? 8 : 0) + ((phone) ? 4 : 0) + ((secu) ? 2 : 0) + ((digilab) ? 1 : 0);

            // Make the request

            //converting date
            String datedepart=begin.toString()+" ";
            String dateend=end.toString()+" ";
            if (hourstart<10){
                datedepart+="0"+hourstart+":";
            }else{
                datedepart+=hourstart+":";
            }
            if (minutestart<10){
                datedepart+="0"+minutestart+":00";
            }else{
                datedepart+=minutestart+":00";
            }

            if (hourend<10){
                dateend+="0"+hourend+":";
            }else{
                dateend+=hourend+":";
            }
            if (minuteend<10){
                dateend+="0"+minuteend+":00";
            }else{
                dateend+=minuteend+":00";
            }
            try {
                DB.searchAvailableRooms(id_site, desc, datedepart, dateend, num_collab, particularities);
            } catch (SQLException e) {
                GUI.showAlert("Error DataBase","Warning");
                e.printStackTrace();
            }
        }

    }

    /**
     * The User has selected the room he wants to Book
     * @param r the room to book
     */
    @Override
    public void performBookRoom(String r,String desc, Date begin, int hourstart, int minutestart, Date end,int hourend, int minuteend, int num_collab) {
        int idRoom = idAvailaibleRooms.get(availableRooms.indexOf(r));
        //converting date
        String datedepart=begin.toString()+" ";
        String dateend=end.toString()+" ";
        if (hourstart<10){
            datedepart+="0"+hourstart+":";
        }else{
            datedepart+=hourstart+":";
        }
        if (minutestart<10){
            datedepart+="0"+minutestart+":00";
        }else{
            datedepart+=minutestart+":00";
        }

        if (hourend<10){
            dateend+="0"+hourend+":";
        }else{
            dateend+=hourend+":";
        }
        if (minuteend<10){
            dateend+="0"+minuteend+":00";
        }else{
            dateend+=minuteend+":00";
        }
        DB.searchAndBookRoom(idRoom, id_site, desc, datedepart, dateend, num_collab, id_client);
    }

    @Override
    public void processAvailableRooms(ArrayList<Integer> id, ArrayList<String> rooms) {
        availableRooms=new ArrayList<>();
        idAvailaibleRooms=new ArrayList<>();
        for (String r :rooms)
            availableRooms.add(r);
        for (int i : id)
            idAvailaibleRooms.add(i);

        GUI.listOfAvailableRooms(availableRooms);
    }

    @Override
    public void processRoomNotAvailable() {
        GUI.showAlert("No rooms available !Please change search criteria", "No room");
    }

    @Override
    public void processRoomBooked() {
        GUI.roomBooked();
    }

    @Override
    public void processRoomNotBooked() {
        GUI.showAlert("Cannot book room", "Internal Error");
    }


    /*************************
     * PROFIL MANAGEMENT
     *************************/

    /**
     * On enregistre le site de référence choisi par l'utilisateur dans la DataBase
     * @param id_site site's id in the database
     */
    @Override
    public void performSaveLocalisationSite(int id_site) {
        this.id_site = id_site;
        try {
            DB.updateProfile(id_client, id_site);
        } catch (SQLException e) {
            GUI.showAlert("Error DataBase","Warning");
            e.printStackTrace();
        }
    }

    /**
     * On a enregistré le site de référence choisi par l'utilisateur
     */
    @Override
    public void processUpdateProfile() {
        GUI.localisationSaved();
    }



    /*************************
     * GENERAL INFO
     *************************/

    @Override
    public ArrayList<Integer> performGeneralInfo() {
        System.out.println("PERFORM GENERAL INFO");
        ArrayList<Integer> info = new ArrayList<>();
        try {
            info.add(DB.getSitesNb());
            info.add(DB.getRoomsNb());
            info.add(DB.getReservationsNb());
        } catch (SQLException e) {
            GUI.showAlert("Error DataBase","Warning");
            e.printStackTrace();
        }
        return info;
    }



    /*************************
     * SITE MANAGEMENT
     *************************/

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

    public String getCurrentSite(){
        return DB.getCurrentSite(id_site);
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



    /*************************
     * ADD/MODIFY SITE
     *************************/

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



    /*************************
     * ROOM MANAGEMENT
     *************************/

    @Override
    public void performDeleteRoom(String roomname) {
        for (Room r: roomsList){
            if (r.getName_room()==roomname){
                DB.deleteRoomFromDatabase(r.getId_room());
            }
        }

    }

    @Override
    public void performInfoRoom(String roomname) {
        for (Room r: roomsList){
            if (r.getName_room()==roomname){
                DB.infoRoom(r.getId_room());
            }
        }

    }

    @Override
    public void processListOfRoom(ArrayList<Room> rooms) {
        this.roomsList = new ArrayList<>();
        for(Room r : rooms){
            roomsList.add(r);
        }
    }

    @Override
    public void processRoomDeleted() {
        GUI.suppressionRoomSucceed();
    }

    @Override
    public void processRoomNotDeleted() {
        GUI.showAlert("Operation failed", "ERROR");
    }

    @Override
    public void processInfoRoom(String name_room, int capacity, int floor,  int particularities, int nb_reservations, String name_site) {
        boolean visio,phone,secu,digilab;
        visio=phone=secu=digilab=false;

        switch(particularities) {
            case (0):
                visio = false; phone = false; secu = false; digilab = false;
                break;
            case (1):
                visio = false; phone = false; secu = false; digilab = true;
                break;
            case (2):
                visio = false; phone = false; secu = true; digilab = false;
                break;
            case (3):
                visio = false; phone = false; secu = true; digilab = true;
                break;
            case (4):
                visio = false; phone = true; secu = false; digilab = false;
                break;
            case (5):
                visio = false; phone = true; secu = false; digilab = true;
                break;
            case (6):
                visio = false; phone = true; secu = true; digilab = false;
                break;
            case (7):
                visio = false; phone = true; secu = true; digilab = true;
                break;
            case (8):
                visio = true; phone = false; secu = false; digilab = false;
                break;
            case (9):
                visio = true; phone = false; secu = false; digilab = true;
                break;
            case (10):
                visio = true; phone = false; secu = true; digilab = false;
                break;
            case (11):
                visio = true; phone = false; secu = true; digilab = true;
                break;
            case (12):
                visio = true; phone = true; secu = false; digilab = false;
                break;
            case (13):
                visio = true; phone = true; secu = false; digilab = true;
                break;
            case (14):
                visio = true; phone = true; secu = true; digilab = false;
                break;
            case (15):
                visio = true; phone = true; secu = true; digilab = true;
                break;
        }

        GUI.infoRoom(nb_reservations, name_room, capacity, floor, visio, phone, secu, digilab);
    }




    /*************************
     * ADD/MODIFY ROOM
     *************************/
    @Override
    public int getRoomId(String roomName){
        int id=0;
        for(Room r : roomsList){
            if(r.getName_room()==roomName){
                id=r.getId_room();
            }
        }
        return id;
    }
    @Override
    public void performNewRoom(String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab) {
        int particularities = ((visio) ? 8 : 0) + ((phone) ? 4 : 0) + ((secu) ? 2 : 0) + ((digilab) ? 1 : 0);
        DB.addNewRoom(name_room,floor,capacity,particularities);
    }

    @Override
    public void performModifyRoom(int id_room,String name_room, int floor, int capacity, boolean visio, boolean phone, boolean secu, boolean digilab) {
        int particularities = ((visio) ? 8 : 0) + ((phone) ? 4 : 0) + ((secu) ? 2 : 0) + ((digilab) ? 1 : 0);
        DB.modifyRoom(id_room,name_room,floor,capacity,particularities);
    }

    @Override
    public void processRoomAddedOrModified() {
        GUI.roomAddedOrModified();
    }

    @Override
    public void processRoomNotAddedOrModified() {
        GUI.showAlert("Operation Failed", "Error");
    }
}
