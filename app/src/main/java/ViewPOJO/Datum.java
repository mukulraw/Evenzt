package ViewPOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
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
    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
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

    /**
     *
     * @return
     * The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     *
     * @param eventId
     * The eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     *
     * @return
     * The eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     *
     * @param eventName
     * The eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     *
     * @return
     * The eventDesc
     */
    public String getEventDesc() {
        return eventDesc;
    }

    /**
     *
     * @param eventDesc
     * The eventDesc
     */
    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    /**
     *
     * @return
     * The moreDetail
     */
    public String getMoreDetail() {
        return moreDetail;
    }

    /**
     *
     * @param moreDetail
     * The moreDetail
     */
    public void setMoreDetail(String moreDetail) {
        this.moreDetail = moreDetail;
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
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     * The endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return
     * The eventTime
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     *
     * @param eventTime
     * The eventTime
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     *
     * @return
     * The eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     *
     * @param eventType
     * The eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     *
     * @return
     * The venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     *
     * @param venue
     * The venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The eventTimelineId
     */
    public String getEventTimelineId() {
        return eventTimelineId;
    }

    /**
     *
     * @param eventTimelineId
     * The event_timeline_id
     */
    public void setEventTimelineId(String eventTimelineId) {
        this.eventTimelineId = eventTimelineId;
    }

    /**
     *
     * @return
     * The eventTimelineUrl
     */
    public String getEventTimelineUrl() {
        return eventTimelineUrl;
    }

    /**
     *
     * @param eventTimelineUrl
     * The event_timeline_url
     */
    public void setEventTimelineUrl(String eventTimelineUrl) {
        this.eventTimelineUrl = eventTimelineUrl;
    }

    /**
     *
     * @return
     * The eventTimelineUsername
     */
    public String getEventTimelineUsername() {
        return eventTimelineUsername;
    }

    /**
     *
     * @param eventTimelineUsername
     * The event_timeline_username
     */
    public void setEventTimelineUsername(String eventTimelineUsername) {
        this.eventTimelineUsername = eventTimelineUsername;
    }

    /**
     *
     * @return
     * The eventTimelineName
     */
    public String getEventTimelineName() {
        return eventTimelineName;
    }

    /**
     *
     * @param eventTimelineName
     * The event_timeline_name
     */
    public void setEventTimelineName(String eventTimelineName) {
        this.eventTimelineName = eventTimelineName;
    }

    /**
     *
     * @return
     * The eventTimelineThumbnailUrl
     */
    public String getEventTimelineThumbnailUrl() {
        return eventTimelineThumbnailUrl;
    }

    /**
     *
     * @param eventTimelineThumbnailUrl
     * The event_timeline_thumbnail_url
     */
    public void setEventTimelineThumbnailUrl(String eventTimelineThumbnailUrl) {
        this.eventTimelineThumbnailUrl = eventTimelineThumbnailUrl;
    }

    /**
     *
     * @return
     * The eventNumLikes
     */
    public String getEventNumLikes() {
        return eventNumLikes;
    }

    /**
     *
     * @param eventNumLikes
     * The event_num_likes
     */
    public void setEventNumLikes(String eventNumLikes) {
        this.eventNumLikes = eventNumLikes;
    }

    /**
     *
     * @return
     * The eventNumInvites
     */
    public String getEventNumInvites() {
        return eventNumInvites;
    }

    /**
     *
     * @param eventNumInvites
     * The event_num_invites
     */
    public void setEventNumInvites(String eventNumInvites) {
        this.eventNumInvites = eventNumInvites;
    }

    /**
     *
     * @return
     * The eventNumJoines
     */
    public String getEventNumJoines() {
        return eventNumJoines;
    }

    /**
     *
     * @param eventNumJoines
     * The event_num_joines
     */
    public void setEventNumJoines(String eventNumJoines) {
        this.eventNumJoines = eventNumJoines;
    }

}
