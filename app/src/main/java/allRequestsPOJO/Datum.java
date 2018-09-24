package allRequestsPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mukul on 10/25/2016.
 */

public class Datum {


    @SerializedName("friendId")
    @Expose
    private String friendId;
    @SerializedName("requestUrl")
    @Expose
    private String requestUrl;
    @SerializedName("requestUsername")
    @Expose
    private String requestUsername;
    @SerializedName("requestName")
    @Expose
    private String requestName;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;

    /**
     *
     * @return
     * The friendId
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     *
     * @param friendId
     * The friendId
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    /**
     *
     * @return
     * The requestUrl
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     *
     * @param requestUrl
     * The requestUrl
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     *
     * @return
     * The requestUsername
     */
    public String getRequestUsername() {
        return requestUsername;
    }

    /**
     *
     * @param requestUsername
     * The requestUsername
     */
    public void setRequestUsername(String requestUsername) {
        this.requestUsername = requestUsername;
    }

    /**
     *
     * @return
     * The requestName
     */
    public String getRequestName() {
        return requestName;
    }

    /**
     *
     * @param requestName
     * The requestName
     */
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    /**
     *
     * @return
     * The thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     *
     * @param thumbnailUrl
     * The thumbnailUrl
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
