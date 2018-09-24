package googleLoginPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;



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
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verification_key")
    @Expose
    private String emailVerificationKey;
    @SerializedName("email_verified")
    @Expose
    private String emailVerified;
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
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("interest")
    @Expose
    private String interest;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("language")
    @Expose
    private String language;
    //@SerializedName("avatar")
    //@Expose
    //private Avatar avatar;
    @SerializedName("orignal_avatar_url")
    @Expose
    private String orignalAvatarUrl;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    //@SerializedName("cover")
    //@Expose
    //private Cover cover;
    @SerializedName("actual_cover_url")
    @Expose
    private String actualCoverUrl;
    @SerializedName("cover_url")
    @Expose
    private String coverUrl;
    @SerializedName("online")
    @Expose
    private Boolean online;
    @SerializedName("isFriendBy")
    @Expose
    private Boolean isFriendBy;
    @SerializedName("isFriendRequested")
    @Expose
    private Boolean isFriendRequested;
    @SerializedName("friends")
    @Expose
    private String friends;

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
     * The age
     */
    public String getAge() {
        return age;
    }

    /**
     *
     * @param age
     * The age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The interest
     */
    public String getInterest() {
        return interest;
    }

    /**
     *
     * @param interest
     * The interest
     */
    public void setInterest(String interest) {
        this.interest = interest;
    }

    /**
     *
     * @return
     * The education
     */
    public String getEducation() {
        return education;
    }

    /**
     *
     * @param education
     * The education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     *
     * @return
     * The occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     *
     * @param occupation
     * The occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     *
     * @return
     * The organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     * The organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     * The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     *
     * @param bio
     * The bio
     */
    public void setBio(String bio) {
        this.bio = bio;
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
     * The avatar
     */
    //public Avatar getAvatar() {
      //  return avatar;
    //}

    /**
     *
     * @param avatar
     * The avatar
     */
    //public void setAvatar(Avatar avatar) {
      //  this.avatar = avatar;
    //}

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
     * The cover
     */
    //public Cover getCover() {
      //  return cover;
    //}

    /**
     *
     * @param cover
     * The cover
     */
    //public void setCover(Cover cover) {
        //this.cover = cover;
    //}

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
}
