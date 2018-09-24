package messagePOJO;

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
    @SerializedName("send")
    @Expose
    private Integer send;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("chatType")
    @Expose
    private Object chatType;
    @SerializedName("unreadMsg")
    @Expose
    private String unreadMsg;
    @SerializedName("canChat")
    @Expose
    private Boolean canChat;

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
     * The friendUrl
     */
    public String getFriendUrl() {
        return friendUrl;
    }

    /**
     *
     * @param friendUrl
     * The friendUrl
     */
    public void setFriendUrl(String friendUrl) {
        this.friendUrl = friendUrl;
    }

    /**
     *
     * @return
     * The friendUsername
     */
    public String getFriendUsername() {
        return friendUsername;
    }

    /**
     *
     * @param friendUsername
     * The friendUsername
     */
    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    /**
     *
     * @return
     * The friendName
     */
    public String getFriendName() {
        return friendName;
    }

    /**
     *
     * @param friendName
     * The friendName
     */
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    /**
     *
     * @return
     * The friendThumbnailUrl
     */
    public String getFriendThumbnailUrl() {
        return friendThumbnailUrl;
    }

    /**
     *
     * @param friendThumbnailUrl
     * The friendThumbnailUrl
     */
    public void setFriendThumbnailUrl(String friendThumbnailUrl) {
        this.friendThumbnailUrl = friendThumbnailUrl;
    }

    /**
     *
     * @return
     * The send
     */
    public Integer getSend() {
        return send;
    }

    /**
     *
     * @param send
     * The send
     */
    public void setSend(Integer send) {
        this.send = send;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     * The media
     */
    public String getMedia() {
        return media;
    }

    /**
     *
     * @param media
     * The media
     */
    public void setMedia(String media) {
        this.media = media;
    }

    /**
     *
     * @return
     * The senderId
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     *
     * @param senderId
     * The sender_id
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     *
     * @return
     * The receiverId
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     *
     * @param receiverId
     * The receiver_id
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The chatType
     */
    public Object getChatType() {
        return chatType;
    }

    /**
     *
     * @param chatType
     * The chatType
     */
    public void setChatType(Object chatType) {
        this.chatType = chatType;
    }

    /**
     *
     * @return
     * The unreadMsg
     */
    public String getUnreadMsg() {
        return unreadMsg;
    }

    /**
     *
     * @param unreadMsg
     * The unreadMsg
     */
    public void setUnreadMsg(String unreadMsg) {
        this.unreadMsg = unreadMsg;
    }

    /**
     *
     * @return
     * The canChat
     */
    public Boolean getCanChat() {
        return canChat;
    }

    /**
     *
     * @param canChat
     * The canChat
     */
    public void setCanChat(Boolean canChat) {
        this.canChat = canChat;
    }

}
