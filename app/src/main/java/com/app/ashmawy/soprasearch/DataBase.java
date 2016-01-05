package com.app.ashmawy.soprasearch;

import android.database.sqlite.SQLiteDatabase;
import java.util.Date;
import static android.database.sqlite.SQLiteDatabase.*;

/**
 * Created by cyriljantingstell on 04/12/15.
 */
public class DataBase implements DB_Output {
    public int oldVersion;
    public int newVersion;
    SQLiteDatabase SopraDB;
    SQLiteDatabase.CursorFactory factory;
    DB_Listener db_listener;

    public void initDB() {
        SopraDB = openOrCreateDatabase("DATABASE", factory, null);
    }

    public void initDB(SQLiteDatabase SopraDB) {
        SopraDB = openOrCreateDatabase("DATABASE", factory, null);
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS CLIENTS(" +
                "id_client INTEGER PRIMARY KEY UNIQUE," +
                "nickname TEXT NOT NULL);"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS USERS(" +
                        "user INTEGER PRIMARY KEY UNIQUE," +
                        "FOREIGN KEY(user) REFERENCES CLIENTS(id_client)," +
                        "site INTEGER" +
                        "FOREIGN KEY(site) REFERENCES SITES(id_site)"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS ADMINS(" +
                        "admin INTEGER UNIQUE," +
                        "FOREIGN KEY(admin) REFERENCES CLIENTS(id_client)"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS SITES(" +
                        "id_site INTEGER PRIMARY KEY UNIQUE," +
                        "name_site TEXT NOT NULL" +
                        "address TEXT NOT NULL" +
                        "nb_rooms INTEGER NOT NULL" +
                        "nb_reservation INTEGER NOT NULL"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS ROOMS(" +
                        "id_room INTEGER PRIMARY KEY UNIQUE," +
                        "name_room TEXT NOT NULL" +
                        "capacity INTEGER NOT NULL" +
                        "floor INTEGER NOT NULL" +
                        "particularities INTEGER NOT NULL" +
                        "nb_reservation INTEGER NOT NULL" +
                        "site INTEGER NOT NULL" +
                        "FOREIGN KEY(site) REFERENCES SITES(id_site)"
        );
        SopraDB.execSQL(
                "CREATE TABLE IF NOT EXISTS RESERVATIONS(" +
                        "id_reservation INTEGER PRIMARY KEY UNIQUE," +
                        "date_begin TEXT NOT NULL" +
                        "date_end TEXT NOT NULL" +
                        "nb_collaborateurs INTEGER NOT NULL" +
                        "description TEXT NOT NULL" +
                        "user INTEGER NOT NULL" +
                        "room INTEGER PRIMARY KEY NOT NULL" +
                        "FOREIGN KEY(user) REFERENCES USERS(user)" +
                        "FOREIGN KEY(room) REFERENCES ROOMS(id_room)"
        );


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void setDbListener(DB_Listener dbListener) {
        this.db_listener = db_listener;
    }

    @Override
    public void InClientList(String nickname, boolean userOrAdmin) {

        db_listener.ProcessResponseAuthentication(true);
    }

    @Override
    public void SearchAvailableRooms(int id_site, String desc, Date begin, Date end, int num_collab, int particul) {
        db_listener.ProcessAvailableRooms("salle ad");
    }

    @Override
    public void SearchAndBookRoom(int id_room) {
    }

    @Override
    public void UpdateProfile(int id_user, int id_site) {
        db_listener.ProcessUpdateProfile();
    }
}
