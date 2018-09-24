package POJOs;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Data {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("avatar_id")
    @Expose
    private String avatarId;
    @SerializedName("cover_id")
    @Expose
    private String coverId;
    @SerializedName("cover_position")
    @Expose
    private String coverPosition;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verification_key")
    @Expose
    private String emailVerificationKey;
    @SerializedName("email_verified")
    @Expose
    private String emailVerified;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("last_logged")
    @Expose
    private String lastLogged;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("session_token")
    @Expose
    private String sessionToken;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("apnId")
    @Expose
    private String apnId;
    @SerializedName("gcmId")
    @Expose
    private String gcmId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("fbConnect")
    @Expose
    private String fbConnect;
    @SerializedName("googleConnect")
    @Expose
    private String googleConnect;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("avatar")
    @Expose
    private List<Object> avatar = new ArrayList<Object>();
    @SerializedName("orignal_avatar_url")
    @Expose
    private String orignalAvatarUrl;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("actual_cover_url")
    @Expose
    private String actualCoverUrl;
    @SerializedName("online")
    @Expose
    private Boolean online;
    @SerializedName("isFriendBy")
    @Expose
    private Boolean isFriendBy;
    @SerializedName("isFriendRequested")
    @Expose
    private Boolean isFriendRequested;
    @SerializedName("followers")
    @Expose
    private String followers;
    @SerializedName("followings")
    @Expose
    private String followings;
    @SerializedName("friends")
    @Expose
    private String friends;
    @SerializedName("posts")
    @Expose
    private String posts;
    @SerializedName("numPageLikes")
    @Expose
    private String numPageLikes;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The about
     */
    public String getAbout() {
        return about;
    }

    /**
     *
     * @param about
     * The about
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     *
     * @return
     * The active
     */
    public String getActive() {
        return active;
    }

    /**
     *
     * @param active
     * The active
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     *
     * @return
     * The avatarId
     */
    public String getAvatarId() {
        return avatarId;
    }

    /**
     *
     * @param avatarId
     * The avatar_id
     */
    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    /**
     *
     * @return
     * The coverId
     */
    public String getCoverId() {
        return coverId;
    }

    /**
     *
     * @param coverId
     * The cover_id
     */
    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    /**
     *
     * @return
     * The coverPosition
     */
    public String getCoverPosition() {
        return coverPosition;
    }

    /**
     *
     * @param coverPosition
     * The cover_position
     */
    public void setCoverPosition(String coverPosition) {
        this.coverPosition = coverPosition;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The emailVerificationKey
     */
    public String getEmailVerificationKey() {
        return emailVerificationKey;
    }

    /**
     *
     * @param emailVerificationKey
     * The email_verification_key
     */
    public void setEmailVerificationKey(String emailVerificationKey) {
        this.emailVerificationKey = emailVerificationKey;
    }

    /**
     *
     * @return
     * The emailVerified
     */
    public String getEmailVerified() {
        return emailVerified;
    }

    /**
     *
     * @param emailVerified
     * The email_verified
     */
    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     *
     * @return
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The lastLogged
     */
    public String getLastLogged() {
        return lastLogged;
    }

    /**
     *
     * @param lastLogged
     * The last_logged
     */
    public void setLastLogged(String lastLogged) {
        this.lastLogged = lastLogged;
    }

    /**
     *
     * @return
     * The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The dateCreated
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     *
     * @param dateCreated
     * The date_created
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @return
     * The dateModified
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     *
     * @param dateModified
     * The date_modified
     */
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    /**
     *
     * @return
     * The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @param timezone
     * The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     * The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     * The ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     *
     * @param ipAddress
     * The ip_address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     *
     * @return
     * The sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     *
     * @param sessionToken
     * The session_token
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     *
     * @return
     * The lastLogin
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param lastLogin
     * The last_login
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     *
     * @return
     * The apnId
     */
    public String getApnId() {
        return apnId;
    }

    /**
     *
     * @param apnId
     * The apnId
     */
    public void setApnId(String apnId) {
        this.apnId = apnId;
    }

    /**
     *
     * @return
     * The gcmId
     */
    public String getGcmId() {
        return gcmId;
    }

    /**
     *
     * @param gcmId
     * The gcmId
     */
    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The provider
     */
    public String getProvider() {
        return provider;
    }

    /**
     *
     * @param provider
     * The provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     *
     * @return
     * The fbConnect
     */
    public String getFbConnect() {
        return fbConnect;
    }

    /**
     *
     * @param fbConnect
     * The fbConnect
     */
    public void setFbConnect(String fbConnect) {
        this.fbConnect = fbConnect;
    }

    /**
     *
     * @return
     * The googleConnect
     */
    public String getGoogleConnect() {
        return googleConnect;
    }

    /**
     *
     * @param googleConnect
     * The googleConnect
     */
    public void setGoogleConnect(String googleConnect) {
        this.googleConnect = googleConnect;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The avatar
     */
    public List<Object> getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(List<Object> avatar) {
        this.avatar = avatar;
    }

    /**
     *
     * @return
     * The orignalAvatarUrl
     */
    public String getOrignalAvatarUrl() {
        return orignalAvatarUrl;
    }

    /**
     *
     * @param orignalAvatarUrl
     * The orignal_avatar_url
     */
    public void setOrignalAvatarUrl(String orignalAvatarUrl) {
        this.orignalAvatarUrl = orignalAvatarUrl;
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
     * The thumbnail_url
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     *
     * @return
     * The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     *
     * @param avatarUrl
     * The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     *
     * @return
     * The coverUrl
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     *
     * @param coverUrl
     * The cover_url
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    /**
     *
     * @return
     * The actualCoverUrl
     */
    public String getActualCoverUrl() {
        return actualCoverUrl;
    }

    /**
     *
     * @param actualCoverUrl
     * The actual_cover_url
     */
    public void setActualCoverUrl(String actualCoverUrl) {
        this.actualCoverUrl = actualCoverUrl;
    }

    /**
     *
     * @return
     * The online
     */
    public Boolean getOnline() {
        return online;
    }

    /**
     *
     * @param online
     * The online
     */
    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     *
     * @return
     * The isFriendBy
     */
    public Boolean getIsFriendBy() {
        return isFriendBy;
    }

    /**
     *
     * @param isFriendBy
     * The isFriendBy
     */
    public void setIsFriendBy(Boolean isFriendBy) {
        this.isFriendBy = isFriendBy;
    }

    /**
     *
     * @return
     * The isFriendRequested
     */
    public Boolean getIsFriendRequested() {
        return isFriendRequested;
    }

    /**
     *
     * @param isFriendRequested
     * The isFriendRequested
     */
    public void setIsFriendRequested(Boolean isFriendRequested) {
        this.isFriendRequested = isFriendRequested;
    }

    /**
     *
     * @return
     * The followers
     */
    public String getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers
     * The followers
     */
    public void setFollowers(String followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     * The followings
     */
    public String getFollowings() {
        return followings;
    }

    /**
     *
     * @param followings
     * The followings
     */
    public void setFollowings(String followings) {
        this.followings = followings;
    }

    /**
     *
     * @return
     * The friends
     */
    public String getFriends() {
        return friends;
    }

    /**
     *
     * @param friends
     * The friends
     */
    public void setFriends(String friends) {
        this.friends = friends;
    }

    /**
     *
     * @return
     * The posts
     */
    public String getPosts() {
        return posts;
    }

    /**
     *
     * @param posts
     * The posts
     */
    public void setPosts(String posts) {
        this.posts = posts;
    }

    /**
     *
     * @return
     * The numPageLikes
     */
    public String getNumPageLikes() {
        return numPageLikes;
    }

    /**
     *
     * @param numPageLikes
     * The numPageLikes
     */
    public void setNumPageLikes(String numPageLikes) {
        this.numPageLikes = numPageLikes;
    }

}
