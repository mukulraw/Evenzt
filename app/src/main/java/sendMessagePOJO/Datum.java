package sendMessagePOJO;

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
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("time")
    @Expose
    private String time;

    /**
     *
     * @return
     * The messageText
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     *
     * @param messageText
     * The message_text
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     *
     * @return
     * The messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     *
     * @param messageId
     * The message_id
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     *
     * @return
     * The messageTime
     */
    public String getMessageTime() {
        return messageTime;
    }

    /**
     *
     * @param messageTime
     * The message_time
     */
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    /**
     *
     * @return
     * The messageUserUrl
     */
    public String getMessageUserUrl() {
        return messageUserUrl;
    }

    /**
     *
     * @param messageUserUrl
     * The message_user_url
     */
    public void setMessageUserUrl(String messageUserUrl) {
        this.messageUserUrl = messageUserUrl;
    }

    /**
     *
     * @return
     * The messageUserAvatarUrl
     */
    public String getMessageUserAvatarUrl() {
        return messageUserAvatarUrl;
    }

    /**
     *
     * @param messageUserAvatarUrl
     * The message_user_avatar_url
     */
    public void setMessageUserAvatarUrl(String messageUserAvatarUrl) {
        this.messageUserAvatarUrl = messageUserAvatarUrl;
    }

    /**
     *
     * @return
     * The messageUserName
     */
    public String getMessageUserName() {
        return messageUserName;
    }

    /**
     *
     * @param messageUserName
     * The message_user_name
     */
    public void setMessageUserName(String messageUserName) {
        this.messageUserName = messageUserName;
    }

    /**
     *
     * @return
     * The messageMedia
     */
    public Boolean getMessageMedia() {
        return messageMedia;
    }

    /**
     *
     * @param messageMedia
     * The message_media
     */
    public void setMessageMedia(Boolean messageMedia) {
        this.messageMedia = messageMedia;
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

}
