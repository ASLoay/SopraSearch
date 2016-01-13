package com.app.ashmawy.soprasearch.Model;

/**
 * Created by Joris on 05/01/16.
 */
public class Room {

    /**
     * Attributes
     */

    private Site site;
    private int id_room;
    private String name_room;
    private int capacity;
    private int floor;
    private boolean visio;
    private boolean phone;
    private boolean secu;
    private boolean digilab;
    private int nbReservations;



    /**
     * Constructeur
     */

    public Room(Site site, int id_room, String name_room, int capacity, int floor, boolean visio, boolean phone, boolean secu, boolean digilab, int nbReservations) {
        this.site = site;
        this.id_room = id_room;
        this.name_room = name_room;
        this.capacity = capacity;
        this.floor = floor;
        this.visio = visio;
        this.phone = phone;
        this.secu = secu;
        this.digilab = digilab;
        this.nbReservations = nbReservations;
    }


    /**
     * Getter et setter
     */

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) { this.id_room = id_room; }

    public String getName_room() {
        return name_room;
    }

    public void setName_room(String name_room) {
        this.name_room = name_room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isVisio() {
        return visio;
    }

    public void setVisio(boolean visio) {
        this.visio = visio;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public boolean isSecu() {
        return secu;
    }

    public void setSecu(boolean secu) {
        this.secu = secu;
    }

    public boolean isDigilab() {
        return digilab;
    }

    public void setDigilab(boolean digilab) {
        this.digilab = digilab;
    }

    public int getNbReservations() {
        return nbReservations;
    }

    public void setNbReservations(int nbReservations) {
        this.nbReservations = nbReservations;
    }
}
