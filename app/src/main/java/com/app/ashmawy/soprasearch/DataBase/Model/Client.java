package com.app.ashmawy.soprasearch.DataBase.Model;

/**
 * Created by Joris on 05/01/16.
 */
public class Client {

    /**
    * Attributes
    */

    private String nickname;
    private int userOrAdmin;
    private Site defaultLocationSite;



    /**
     * Constructeur
     */

    public Client(String nickname, int userOrAdmin, Site defaultLocationSite) {
        this.nickname = nickname;
        this.userOrAdmin = userOrAdmin;
        this.defaultLocationSite = defaultLocationSite;
    }



    /**
     * Getter et setter
     */

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserOrAdmin() {
        return userOrAdmin;
    }

    public void setUserOrAdmin(int userOrAdmin) {
        this.userOrAdmin = userOrAdmin;
    }

    public Site getDefaultLocationSite() {
        return defaultLocationSite;
    }

    public void setDefaultLocationSite(Site defaultLocationSite) {
        this.defaultLocationSite = defaultLocationSite;
    }
}
