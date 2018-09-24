package notificationPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("notif_id")
    @Expose
    private String notifId;
    @SerializedName("notif_text")
    @Expose
    private String notifText;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("notif_time")
    @Expose
    private String notifTime;
    @SerializedName("notifier_avatar_url")
    @Expose
    private String notifierAvatarUrl;
    @SerializedName("notifier_name")
    @Expose
    private String notifierName;

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getNotifText() {
        return notifText;
    }

    public void setNotifText(String notifText) {
        this.notifText = notifText;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotifTime() {
        return notifTime;
    }

    public void setNotifTime(String notifTime) {
        this.notifTime = notifTime;
    }

    public String getNotifierAvatarUrl() {
        return notifierAvatarUrl;
    }

    public void setNotifierAvatarUrl(String notifierAvatarUrl) {
        this.notifierAvatarUrl = notifierAvatarUrl;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }


}
