package friendsPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Datum {
    @SerializedName("friendId")
    @Expose
    private String friendId;
    @SerializedName("friendUrl")
    @Expose
    private String friendUrl;
    @SerializedName("friendUsername")
    @Expose
    private String friendUsername;
    @SerializedName("friendName")
    @Expose
    private String friendName;
    @SerializedName("friendThumbnailUrl")
    @Expose
    private String friendThumbnailUrl;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendUrl() {
        return friendUrl;
    }

    public void setFriendUrl(String friendUrl) {
        this.friendUrl = friendUrl;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendThumbnailUrl() {
        return friendThumbnailUrl;
    }

    public void setFriendThumbnailUrl(String friendThumbnailUrl) {
        this.friendThumbnailUrl = friendThumbnailUrl;
    }



}
