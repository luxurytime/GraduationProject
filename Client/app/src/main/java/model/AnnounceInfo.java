package model;

import java.io.Serializable;

/**
 * Created by Ling on 2017/3/19 0019.
 */

public class AnnounceInfo implements Serializable {
    private String announcemsg;
    public AnnounceInfo(){
        announcemsg = "";
    }

    public void setAnnouncemsg(String announcemsg) {
        this.announcemsg = announcemsg;
    }
    public String getAnnoucemsg() {
        return announcemsg;
    }
}
