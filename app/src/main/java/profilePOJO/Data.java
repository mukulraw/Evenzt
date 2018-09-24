package profilePOJO;


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
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("isBlocked")
    @Expose
    private Boolean isBlocked;
    @SerializedName("avatar")
    @Expose
    private Avatar avatar;
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
    @SerializedName("friends")
    @Expose
    private String friends;
    @SerializedName("postedEvents")
    @Expose
    private String postedEvents;
    @SerializedName("joinedEvents")
    @Expose
    private String joinedEvents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailVerificationKey() {
        return emailVerificationKey;
    }

    public void setEmailVerificationKey(String emailVerificationKey) {
        this.emailVerificationKey = emailVerificationKey;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getLastLogged() {
        return lastLogged;
    }

    public void setLastLogged(String lastLogged) {
        this.lastLogged = lastLogged;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getApnId() {
        return apnId;
    }

    public void setApnId(String apnId) {
        this.apnId = apnId;
    }

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFbConnect() {
        return fbConnect;
    }

    public void setFbConnect(String fbConnect) {
        this.fbConnect = fbConnect;
    }

    public String getGoogleConnect() {
        return googleConnect;
    }

    public void setGoogleConnect(String googleConnect) {
        this.googleConnect = googleConnect;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getOrignalAvatarUrl() {
        return orignalAvatarUrl;
    }

    public void setOrignalAvatarUrl(String orignalAvatarUrl) {
        this.orignalAvatarUrl = orignalAvatarUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getActualCoverUrl() {
        return actualCoverUrl;
    }

    public void setActualCoverUrl(String actualCoverUrl) {
        this.actualCoverUrl = actualCoverUrl;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getIsFriendBy() {
        return isFriendBy;
    }

    public void setIsFriendBy(Boolean isFriendBy) {
        this.isFriendBy = isFriendBy;
    }

    public Boolean getIsFriendRequested() {
        return isFriendRequested;
    }

    public void setIsFriendRequested(Boolean isFriendRequested) {
        this.isFriendRequested = isFriendRequested;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getPostedEvents() {
        return postedEvents;
    }

    public void setPostedEvents(String postedEvents) {
        this.postedEvents = postedEvents;
    }

    public String getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(String joinedEvents) {
        this.joinedEvents = joinedEvents;
    }
}
