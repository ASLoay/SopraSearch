package com.app.ashmawy.soprasearch.DataBase;

import android.content.Context;
import android.database.Cursor;

import com.app.ashmawy.soprasearch.Model.Room;
import com.app.ashmawy.soprasearch.Model.Site;
import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;
import com.app.ashmawy.soprasearch.Interfaces.DB_Output;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by RT1_1
 * INSA of Toulouse
 * BE SOPRA
 * Mangel, Ashmawy, Boulanger, Janting
 */

public class DataBase extends DataBaseHandler implements DB_Output {

    /*************************
     * Attributes
     *************************/

    private DB_Listener presenter;


    /*************************
     * Constructor
     *************************/

    public DataBase(Context context) {
        super(context);
    }


    /*************************
     * Getter, Setter & methods overriden
     *************************/

    public void setDBListener(DB_Listener DBListener) {
        this.presenter = DBListener;
    }


    /*************************
     * AUTHENTICATION
     *************************/

    @Override
    public void inClientList(String nickname, boolean userOrAdmin) {

        boolean result = false;
        Cursor c;

        // En fonction de si User ou Admin
        if (userOrAdmin) {
            // Effectuer la requete dans la table Users
            c = SopraDB.rawQuery("SELECT " + ID_USER + "," + SITE_REF + " FROM " + TABLE_USERS + " WHERE " + NICKNAME_USER + " = ?", new String[]{nickname});
        } else {
            // Effectuer la requete dans la table Admin
            c = SopraDB.rawQuery("SELECT " + ID_ADMIN + " FROM " + TABLE_ADMINS + " WHERE " + NICKNAME_ADMIN + " = ?", new String[]{nickname});
        }

        // On vérifie si la requête a retourné un resultat
        if (c.getCount() < 1) {
            // UserName Not Exist
            System.out.println("Aucun utilisateur...");
        } else {
            c.moveToFirst();

            // On met à jour le résultat
            result = true;

            // On met à jour l'id du client pour le presenter
            presenter.processIdClient(c.getInt(0));

            // On récupère l'ID du site
            if (userOrAdmin) {

                // On met à jour le site de ref pour le presenter (id_site)
                presenter.processIdSite(c.getInt(1));
            }
        }
        c.close();

        // On retourne au presenter en fonction du résultat
        presenter.processResponseAuthentication(result);
    }


    /*************************
     * ROOM BOOKING
     *************************/

    @Override
    public void searchAvailableRooms(int id_site, String desc, String begin, String end, int num_collab, int particul) throws SQLException {

        ArrayList<Integer> room_ids = new ArrayList<>();
        ArrayList<String> room_names = new ArrayList<>();
        int size;
        System.out.println(particul);
        String particularities = "";
        String query_begin = "SELECT " + ID_ROOM + "," + NAME_ROOM
                + " FROM " + TABLE_ROOMS
                + " WHERE (" + SITE_OF_ROOM + " = " + id_site
                + " AND " + CAPACITY + " >= " + num_collab;
        //Digilab=1
        //Secu=2
        //Tel=4
        //Visio=8
        switch (particul) {
            case (1):
                particularities = " AND (" + PARTICULARITIES + " = 1 OR " + PARTICULARITIES + " = 3 OR " + PARTICULARITIES + " = 5 OR " + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 9 OR " + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 15)";
                break;
            case (2):
                particularities = " AND (" + PARTICULARITIES + " = 2 OR " + PARTICULARITIES + " = 6 OR " + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 10 OR " + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (3):
                particularities = " AND (" + PARTICULARITIES + " = 3 OR " + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 15)";
                break;
            case (4):
                particularities = " AND (" + PARTICULARITIES + " = 4 OR " + PARTICULARITIES + " = 5 OR " + PARTICULARITIES + " = 6 OR " + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 12 OR " + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (5):
                particularities = " AND (" + PARTICULARITIES + " = 5 OR " + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 15)";
                break;
            case (6):
                particularities = " AND (" + PARTICULARITIES + " = 6 OR " + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (7):
                particularities = " AND (" + PARTICULARITIES + " = 7 OR " + PARTICULARITIES + " = 15)";
                break;
            case (8):
                particularities = " AND (" + PARTICULARITIES + " = 8 OR " + PARTICULARITIES + " = 9 OR " + PARTICULARITIES + " = 10 OR " + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 12 OR " + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (9):
                particularities = " AND (" + PARTICULARITIES + " = 9 OR " + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 15)";
                break;
            case (10):
                particularities = " AND (" + PARTICULARITIES + " = 10 OR " + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (11):
                particularities = " AND (" + PARTICULARITIES + " = 11 OR " + PARTICULARITIES + " = 15)";
                break;
            case (12):
                particularities = " AND (" + PARTICULARITIES + " = 12 OR " + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (13):
                particularities = " AND (" + PARTICULARITIES + " = 13 OR " + PARTICULARITIES + " = 15)";
                break;
            case (14):
                particularities = " AND (" + PARTICULARITIES + " = 14 OR " + PARTICULARITIES + " = 15)";
                break;
            case (15):
                particularities = " AND " + PARTICULARITIES + " = 15";
                break;
        }
        String query_end = " AND " + ID_ROOM + " NOT IN ("
                + " SELECT " + ROOM_RES
                + " FROM " + TABLE_RESERVATIONS
                + " WHERE (('" + begin + "' > " + DATE_BEGIN
                + " AND '" + begin + "' < " + DATE_END + ") OR ('" + end + "' > " + DATE_BEGIN
                + " AND '" + end + "' < " + DATE_END + ") OR ('" + begin + "' = " + DATE_BEGIN
                + " AND '" + end + "' = " + DATE_END + "))"
                +  "));";
        Cursor c = SopraDB.rawQuery(query_begin + particularities + query_end, null);
        c.moveToFirst();
        size = c.getCount();
        System.out.println(size);

        if (size != 0) {
            System.out.println("TTTEEESSSTTT ::: ");
            do {
                System.out.println(size);
                room_ids.add(c.getInt(0));
                System.out.println("id[i] = " + c.getInt(0));
                room_names.add(c.getString(1));
                System.out.println("room_name[i] = " + c.getString(1));

            } while (c.moveToNext());
            c.close();

            presenter.processAvailableRooms(room_ids, room_names);
        } else {
            presenter.processRoomNotAvailable();
        }
    }

    @Override
    public void searchAndBookRoom(int id_room, int id_site, String desc, String begin, String end, int num_collab, int id_client) {

        String query = "INSERT INTO " + TABLE_RESERVATIONS + " (" + DATE_BEGIN + "," + DATE_END + "," + NB_COLLABORATORS + ","  + DESCRIPTION + "," + USER_RES + "," + ROOM_RES + ") VALUES ('" + begin + "','" + end + "'," + num_collab + ",'" + desc + "'," + id_client + "," + id_room + ");";
        SopraDB.execSQL(query);

        query = "SELECT " + NB_RESERVATION_SITE + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = "  + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        int nb_reservation = c.getInt(0);
        nb_reservation++;

        query = "UPDATE "+ TABLE_SITES + " SET " + NB_RESERVATION_SITE + " = " + nb_reservation + " WHERE " + ID_SITE + " = " + id_site + ";";
        SopraDB.execSQL(query);

        query = "SELECT " + NB_RESERVATION_ROOM + " FROM " + TABLE_ROOMS + " WHERE " + ID_ROOM + " = "  + id_room + ";";
        c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        nb_reservation = c.getInt(0);
        nb_reservation++;

        query = "UPDATE "+ TABLE_ROOMS + " SET " + NB_RESERVATION_ROOM + " = " + nb_reservation + " WHERE " + ID_ROOM + " = " + id_room + ";";
        SopraDB.execSQL(query);

        query = "SELECT * FROM " + TABLE_RESERVATIONS + ";";
        c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        do{
            System.out.println(c.getInt(0) + "  " + c.getString(1) + "  " +  c.getString(2) + "  " + c.getInt(3) + "  " + c.getString(4) + "  " + c.getInt(5) + "  " + c.getInt(6));
        }
        while(c.moveToNext());

        c.close();

        presenter.processRoomBooked();
    }



    /*************************
     * PROFIL MANAGEMENT
     *************************/

    /**
     * On enregistre le site de référence choisi par l'utilisateur dans la DataBase
     * @param id_user id of user
     * @param id_site id of site
     */
    @Override
    public void updateProfile(int id_user, int id_site) throws SQLException {
        SopraDB.execSQL("UPDATE "+ TABLE_USERS + " SET " + SITE_REF + " = " + id_site + " WHERE " + ID_USER + " = " + id_user + ";");
        presenter.processUpdateProfile();
    }

    @Override
    public String getCurrentSite(int id_site) {
        String query = "SELECT " + NAME_SITE + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        String current_site = c.getString(0);
        c.close();
        return current_site;
    }


    /*************************
     * GENERAL INFO
     *************************/

    @Override
    public int getSitesNb() throws SQLException {
        String query = "SELECT " + ID_SITE + " FROM " + TABLE_SITES + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        int size = c.getCount();
        c.close();
        return(size);
    }

    @Override
    public int getRoomsNb() throws SQLException {
        String query = "SELECT " + ID_ROOM + " FROM " + TABLE_ROOMS + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        int size = c.getCount();
        c.close();
        return(size);
    }

    @Override
    public int getReservationsNb() throws SQLException {
        String query = "SELECT " + ID_RESERVATION + " FROM " + TABLE_RESERVATIONS + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        int size = c.getCount();
        c.close();
        return(size);
    }



    /*************************
     * SITE MANAGEMENT
     *************************/

    @Override
    public void searchSites() {
        ArrayList<Site> sites = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SITES + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        //Tester si le cursor est vide
        if (c.getCount() < 1) {
            System.out.println("No site");
        }
        else {
            c.moveToFirst();
            do {
                Site site = new Site(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3), c.getInt(4));
                sites.add(site);
            }
            while (c.moveToNext());
        }
        c.close();

        presenter.processListOfSites(sites);
    }

    @Override
    public void deleteSiteFromDatabase(String name_site) throws SQLException {
        // Get id_site according to name_site
        Cursor c = SopraDB.rawQuery("SELECT " + ID_SITE + " FROM " + TABLE_SITES + " WHERE " + NAME_SITE + " = ?;", new String[]{name_site});
        c.moveToFirst();
        int id_site = c.getInt(0);
        System.out.println("ID SIIIIIIITE DB = "+id_site);
        c.close();

        // Delete all associated reservations
        String query = "DELETE FROM " + TABLE_RESERVATIONS + " WHERE " + ROOM_RES + " IN (SELECT " + ID_ROOM + " FROM " + TABLE_ROOMS + " WHERE " + SITE_OF_ROOM + "=" + id_site + ") ;";
        SopraDB.execSQL(query);

        // Delete all associated rooms
        query = "DELETE FROM " + TABLE_ROOMS + " WHERE " + SITE_OF_ROOM + " = " + id_site + ";";
        SopraDB.execSQL(query);

        // Delete the site
        SopraDB.execSQL("DELETE FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";");
        presenter.processSiteDeleted();
    }

    @Override
    public void infoSite(int id_site) {

        String name_site;
        int nb_rooms;
        String address;
        String query = "SELECT " + NAME_SITE + "," + NB_ROOMS + "," + ADDRESS + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        name_site = c.getString(0);
        nb_rooms = c.getInt(1);
        address = c.getString(2);
        c.close();

        presenter.processInfoSite(name_site, nb_rooms, address);
    }



    /*************************
     * ADD/MODIFY SITE
     *************************/

    @Override
    public void addNewSite(String name_site, int nb_rooms, String address) throws SQLException {
        String query = "INSERT INTO " + TABLE_SITES + " (" + NAME_SITE + "," + ADDRESS + "," + NB_ROOMS + "," + NB_RESERVATION_SITE + ") VALUES('" + name_site + "','" + address + "'," + nb_rooms + ",'0');";
        SopraDB.execSQL(query);
        presenter.processSiteAddedOrModified();
    }

    @Override
    public void modifySite(String nameSiteMngt, String name_site, int nb_rooms, String address) throws SQLException {
        // Get id_site according to name_site
        Cursor c = SopraDB.rawQuery("SELECT " + ID_SITE + " FROM " + TABLE_SITES + " WHERE " + NAME_SITE + " = ?;", new String[]{nameSiteMngt});
        c.moveToFirst();
        int id_site = c.getInt(0);
        c.close();

        // Make the modifications
        String query = "UPDATE " + TABLE_SITES + " SET " + NAME_SITE + " = '" + name_site + "'," + ADDRESS + " = '" + address + "'," + NB_ROOMS + " = " + nb_rooms + " WHERE " + ID_SITE + " = " + id_site + ";";
        SopraDB.execSQL(query);

        presenter.processSiteAddedOrModified();
    }



    /*************************
     * ROOM MANAGEMENT
     *************************/

    @Override
    public void searchRoom(int id_site) {
        ArrayList<Room> rooms = new ArrayList<>();
        boolean visio = false;
        boolean phone = false;
        boolean secu = false;
        boolean digilab = false;

        String query = "SELECT * FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();

        Site site = new Site(c.getInt(1),c.getString(2),c.getInt(3),c.getString(4),c.getInt(5));

        query = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + SITE_OF_ROOM + " = " + id_site + ";";
        c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        do {
            int p = c.getInt(4);
            switch(p) {
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
            Room room = new Room(site,c.getInt(0),c.getString(1),c.getInt(2),c.getInt(3),visio,phone,secu,digilab,c.getInt(5));
            rooms.add(room);
        }
        while(c.moveToNext());
        c.close();

        presenter.processListOfRoom(rooms);
    }

    @Override
    public void deleteRoomFromDatabase(int id_room) {

        String query = "DELETE FROM " + TABLE_RESERVATIONS + " WHERE " + ROOM_RES + " = " + id_room + ";";
        SopraDB.execSQL(query);

        query = "DELETE FROM " + TABLE_ROOMS + " WHERE " + ID_ROOM + " = " + id_room + ";";
        SopraDB.execSQL(query);

        presenter.processRoomDeleted();
    }

    @Override
    public void infoRoom(int id_room) {

        String query = "SELECT " + NAME_ROOM + "," + CAPACITY + "," + FLOOR + "," + PARTICULARITIES + "," + NB_RESERVATION_ROOM + "," + SITE_OF_ROOM + " FROM " + TABLE_ROOMS + " WHERE " + ID_ROOM + " = " + id_room + ";";
        Cursor c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        String name_room = c.getString(0);
        int capacity = c.getInt(1);
        int floor = c.getInt(2);
        int particularities = c.getInt(3);
        int nb_reservations = c.getInt(4);
        int id_site = c.getInt(5);

        query = "SELECT " + NAME_SITE + " FROM " + TABLE_SITES + " WHERE " + ID_SITE + " = " + id_site + ";";
        c = SopraDB.rawQuery(query, null);
        c.moveToFirst();
        String name_site = c.getString(0);

        c.close();

        presenter.processInfoRoom(name_room, capacity, floor, particularities, nb_reservations, name_site);
    }



    /*************************
     * ADD/MODIFY ROOM
     *************************/

    @Override
    public void addNewRoom(String name_room, int floor, int capacity, int particularities) {
        String query = "INSERT INTO " + TABLE_ROOMS + " (" + NAME_ROOM + "," + FLOOR + "," + CAPACITY + "," + PARTICULARITIES + ") VALUES('" + name_room + "'," + floor + "," + particularities + ",'0');";
        SopraDB.execSQL(query);
        presenter.processSiteAddedOrModified();
    }


    @Override
    public void modifyRoom(int id_room, String name_room, int floor, int capacity, int particularities) {

        String query = "UPDATE " + TABLE_ROOMS + " SET " + NAME_ROOM + " = '" + name_room + "'," + FLOOR + " = " + floor + "," + CAPACITY + " = " + capacity + "," + PARTICULARITIES + " = " + particularities + " WHERE " + ID_ROOM + " = " + id_room + ";";
        SopraDB.execSQL(query);

        presenter.processRoomAddedOrModified();
    }
}
