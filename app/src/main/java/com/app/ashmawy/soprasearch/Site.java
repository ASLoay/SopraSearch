package com.app.ashmawy.soprasearch;

/**
 * Created by Joris on 05/01/16.
 */
public class Site {

    /**
     * Attributes
     */

    private int id_site;
    private String name_site;
    private int nb_salles_site;
    private String address_site;
    private int nbReservation;



    /**
     * Constructeur
     */

    public Site(int id_site, String name_site, int nb_salles_site, String address_site, int nbReservation) {
        this.id_site = id_site;
        this.name_site = name_site;
        this.nb_salles_site = nb_salles_site;
        this.address_site = address_site;
        this.nbReservation = nbReservation;
    }


    /**
     * Getter et setter
     */

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public String getName_site() {
        return name_site;
    }

    public void setName_site(String name_site) {
        this.name_site = name_site;
    }

    public int getNb_salles_site() {
        return nb_salles_site;
    }

    public void setNb_salles_site(int nb_salles_site) {
        this.nb_salles_site = nb_salles_site;
    }

    public String getAddress_site() {
        return address_site;
    }

    public void setAddress_site(String address_site) {
        this.address_site = address_site;
    }

    public int getNbReservation() {
        return nbReservation;
    }

    public void setNbReservation(int nbReservation) {
        this.nbReservation = nbReservation;
    }
}
