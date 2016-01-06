package com.app.ashmawy.soprasearch.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.ashmawy.soprasearch.Interfaces.DB_Listener;

/**
 * Created by Joris on 06/01/16.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    /**
     * Attributes
     */

    protected final static int VERSION = 1; // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static String NAME_DB = "SopraSearch_RT11";private DB_Listener DBListener;
    protected SQLiteDatabase SopraDB;

    /** SITE */
    public static final String TABLE_SITES = "sites";
    public static final String ID_SITE = "_id";
    public static final String NAME_SITE = "name_site";
    public static final String ADDRESS = "address";
    public static final String NB_ROOMS = "nb_rooms";
    public static final String NB_RESERVATION_SITE = "nb_reservation";
    public static final String SITES_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SITES + "(" +
                    ID_SITE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_SITE + " TEXT NOT NULL, " +
                    ADDRESS + "address TEXT NOT NULL, " +
                    NB_ROOMS + " INTEGER NOT NULL, " +
                    NB_RESERVATION_SITE + "nb_reservation INTEGER NOT NULL);";
    public static final String SITES_TABLE_DROP = "DROP TABLE IF EXISTS " + SITES_TABLE_CREATE + ";";

    /** ROOM */
    public static final String TABLE_ROOMS = "rooms";
    public static final String ID_ROOM = "_id";
    public static final String NAME_ROOM = "name_room";
    public static final String CAPACITY = "capacity";
    public static final String FLOOR = "floor";
    public static final String PARTICULARITIES = "particularities";
    public static final String NB_RESERVATION_ROOM = "nb_reservation";
    public static final String SITE_OF_ROOM = "site";
    public static final String ROOMS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS "+ TABLE_ROOMS + "(" +
                    ID_ROOM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_ROOM + " TEXT NOT NULL, " +
                    CAPACITY + " INTEGER NOT NULL, " +
                    FLOOR + " INTEGER NOT NULL, " +
                    PARTICULARITIES + " INTEGER NOT NULL, " +
                    NB_RESERVATION_ROOM + " INTEGER NOT NULL, " +
                    SITE_OF_ROOM + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + SITE_OF_ROOM + ") REFERENCES " + TABLE_SITES + "(" + ID_SITE + "));";
    public static final String ROOMS_TABLE_DROP = "DROP TABLE IF EXISTS " + ROOMS_TABLE_CREATE + ";";

    /** CLIENT */
    public static final String TABLE_CLIENTS = "clients";
    public static final String ID_CLIENT = "_id";
    public static final String NICKNAME = "nickname";
    public static final String CLIENTS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTS + "(" +
                    ID_CLIENT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NICKNAME + " TEXT NOT NULL);";
    public static final String CLIENTS_TABLE_DROP = "DROP TABLE IF EXISTS " + CLIENTS_TABLE_CREATE + ";";

    /** USER */
    public static final String TABLE_USERS = "users";
    public static final String USER_ID = "user";
    public static final String SITE_ID = "site";
    public static final String USERS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SITE_ID + " INTEGER," +
                    " FOREIGN KEY (" + USER_ID + ") REFERENCES " + TABLE_CLIENTS + "(" + ID_CLIENT + ")," +
                    " FOREIGN KEY (" + SITE_ID + ") REFERENCES " + TABLE_SITES + "(" + ID_SITE + "));";
    public static final String USERS_TABLE_DROP = "DROP TABLE IF EXISTS " + USERS_TABLE_CREATE + ";";

    /** ADMIN */
    public static final String TABLE_ADMINS = "admins";
    public static final String ADMIN_ID = "admin";
    public static final String ADMINS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ADMINS + "(" +
                    ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " FOREIGN KEY (" + ADMIN_ID + ") REFERENCES " + TABLE_CLIENTS + "(" + ID_CLIENT + "));";
    public static final String ADMINS_TABLE_DROP = "DROP TABLE IF EXISTS " + ADMINS_TABLE_CREATE + ";";

    /** RESERVATION */
    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String ID_RESERVATION = "_id";
    public static final String DATE_BEGIN = "date_begin";
    public static final String DATE_END = "date_end";
    public static final String NB_COLLABORATORS = "nb_collaborateurs";
    public static final String DESCRIPTION = "description";
    public static final String USER = "user";
    public static final String ROOM = "room";
    public static final String RESERVATIONS_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_RESERVATIONS + "(" +
                    ID_RESERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DATE_BEGIN + " DATETIME NOT NULL," +
                    DATE_END + " DATETIME NOT NULL," +
                    NB_COLLABORATORS + " INTEGER NOT NULL," +
                    DESCRIPTION + " TEXT NOT NULL," +
                    USER + " INTEGER NOT NULL," +
                    ROOM + " INTEGER NOT NULL," +
                    "FOREIGN KEY(user) REFERENCES USERS(user)," +
                    "FOREIGN KEY(room) REFERENCES ROOMS(id_room));";
    public static final String RESERVATIONS_TABLE_DROP = "DROP TABLE IF EXISTS " + RESERVATIONS_TABLE_CREATE + ";";



    /**
     * Constructor
     */

    public DataBaseHandler(Context context) {
        super(context, NAME_DB, null, VERSION);
    }



    /**
     * Getter, Setter & methods overriden
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        SopraDB = db;
        SopraDB.execSQL(CLIENTS_TABLE_CREATE);
        SopraDB.execSQL(USERS_TABLE_CREATE);
        SopraDB.execSQL(ADMINS_TABLE_CREATE);
        SopraDB.execSQL(SITES_TABLE_CREATE);
        SopraDB.execSQL(ROOMS_TABLE_CREATE);
        SopraDB.execSQL(RESERVATIONS_TABLE_CREATE);

        // TEST : on ajoute l'utilisateur toto
        SopraDB.execSQL("INSERT or REPLACE INTO CLIENTS(NICKNAME) VALUES('toto');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SopraDB = db;
        Log.w(DataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        SopraDB.execSQL(CLIENTS_TABLE_DROP);
        SopraDB.execSQL(USERS_TABLE_DROP);
        SopraDB.execSQL(ADMINS_TABLE_DROP);
        SopraDB.execSQL(SITES_TABLE_DROP);
        SopraDB.execSQL(ROOMS_TABLE_DROP);
        SopraDB.execSQL(RESERVATIONS_TABLE_DROP);
        onCreate(SopraDB);
    }

    public void open() {
        SopraDB = this.getWritableDatabase();
    }

    public void close() {
        SopraDB.close();
    }
}
