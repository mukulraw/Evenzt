package eventDetailPOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("eventId")
    @Expose
    private String eventId;

    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("eventDesc")
    @Expose
    private String eventDesc;
    @SerializedName("moreDetail")
    @Expose
    private String moreDetail;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("eventTime")
    @Expose
    private String eventTime;
    @SerializedName("catId")
    @Expose
    private String catId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("eventEnd")
    @Expose
    private String eventEnd;
    @SerializedName("moc")
    @Expose
    private String moc;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("imageUrl")
    @Expose
    private List<ImageUrl> imageUrl = null;
    @SerializedName("color")
    @Expose
    private Object color;
    @SerializedName("isJoined")
    @Expose
    private Boolean isJoined;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("isRequested")
    @Expose
    private Boolean isRequested;
    @SerializedName("isRejected")
    @Expose
    private Boolean isRejected;
    @SerializedName("isLiked")
    @Expose
    private Boolean isLiked;
    @SerializedName("event_timeline_id")
    @Expose
    private String eventTimelineId;
    @SerializedName("event_timeline_url")
    @Expose
    private String eventTimelineUrl;
    @SerializedName("event_timeline_username")
    @Expose
    private String eventTimelineUsername;
    @SerializedName("event_timeline_name")
    @Expose
    private String eventTimelineName;
    @SerializedName("event_timeline_thumbnail_url")
    @Expose
    private String eventTimelineThumbnailUrl;
    @SerializedName("event_num_likes")
    @Expose
    private String eventNumLikes;
    @SerializedName("event_num_invites")
    @Expose
    private String eventNumInvites;
    @SerializedName("event_num_joines")
    @Expose
    private String eventNumJoines;




    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getMoreDetail() {
        return moreDetail;
    }

    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getMoc() {
        return moc;
    }

    public void setMoc(String moc) {
        this.moc = moc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public List<ImageUrl> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<ImageUrl> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public Boolean getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(Boolean isJoined) {
        this.isJoined = isJoined;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getIsRequested() {
        return isRequested;
    }

    public void setIsRequested(Boolean isRequested) {
        this.isRequested = isRequested;
    }

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public String getEventTimelineId() {
        return eventTimelineId;
    }

    public void setEventTimelineId(String eventTimelineId) {
        this.eventTimelineId = eventTimelineId;
    }

    public String getEventTimelineUrl() {
        return eventTimelineUrl;
    }

    public void setEventTimelineUrl(String eventTimelineUrl) {
        this.eventTimelineUrl = eventTimelineUrl;
    }

    public String getEventTimelineUsername() {
        return eventTimelineUsername;
    }

    public void setEventTimelineUsername(String eventTimelineUsername) {
        this.eventTimelineUsername = eventTimelineUsername;
    }

    public String getEventTimelineName() {
        return eventTimelineName;
    }

    public void setEventTimelineName(String eventTimelineName) {
        this.eventTimelineName = eventTimelineName;
    }

    public String getEventTimelineThumbnailUrl() {
        return eventTimelineThumbnailUrl;
    }

    public void setEventTimelineThumbnailUrl(String eventTimelineThumbnailUrl) {
        this.eventTimelineThumbnailUrl = eventTimelineThumbnailUrl;
    }

    public String getEventNumLikes() {
        return eventNumLikes;
    }

    public void setEventNumLikes(String eventNumLikes) {
        this.eventNumLikes = eventNumLikes;
    }

    public String getEventNumInvites() {
        return eventNumInvites;
    }

    public void setEventNumInvites(String eventNumInvites) {
        this.eventNumInvites = eventNumInvites;
    }

    public String getEventNumJoines() {
        return eventNumJoines;
    }

    public void setEventNumJoines(String eventNumJoines) {
        this.eventNumJoines = eventNumJoines;
    }
}
