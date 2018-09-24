package chatPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("message_text")
    @Expose
    private String messageText;
    @SerializedName("message_id")
    @Expose
    private String messageId;
    @SerializedName("message_time")
    @Expose
    private String messageTime;
    @SerializedName("isQuery")
    @Expose
    private String isQuery;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("message_user_url")
    @Expose
    private String messageUserUrl;
    @SerializedName("message_user_avatar_url")
    @Expose
    private String messageUserAvatarUrl;
    @SerializedName("message_user_name")
    @Expose
    private String messageUserName;
    @SerializedName("message_media")
    @Expose
    private Boolean messageMedia;
    @SerializedName("self")
    @Expose
    private Boolean self;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("time")
    @Expose
    private String time;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(String isQuery) {
        this.isQuery = isQuery;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMessageUserUrl() {
        return messageUserUrl;
    }

    public void setMessageUserUrl(String messageUserUrl) {
        this.messageUserUrl = messageUserUrl;
    }

    public String getMessageUserAvatarUrl() {
        return messageUserAvatarUrl;
    }

    public void setMessageUserAvatarUrl(String messageUserAvatarUrl) {
        this.messageUserAvatarUrl = messageUserAvatarUrl;
    }

    public String getMessageUserName() {
        return messageUserName;
    }

    public void setMessageUserName(String messageUserName) {
        this.messageUserName = messageUserName;
    }

    public Boolean getMessageMedia() {
        return messageMedia;
    }

    public void setMessageMedia(Boolean messageMedia) {
        this.messageMedia = messageMedia;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
