package commentPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("comment_text")
    @Expose
    private String commentText;
    @SerializedName("comment_time")
    @Expose
    private String commentTime;
    @SerializedName("comment_timeline_id")
    @Expose
    private String commentTimelineId;
    @SerializedName("comment_timeline_url")
    @Expose
    private String commentTimelineUrl;
    @SerializedName("comment_timeline_username")
    @Expose
    private String commentTimelineUsername;
    @SerializedName("comment_timeline_name")
    @Expose
    private String commentTimelineName;
    @SerializedName("comment_timeline_thumbnail_url")
    @Expose
    private String commentTimelineThumbnailUrl;

    /**
     *
     * @return
     * The commentId
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     *
     * @param commentId
     * The comment_id
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    /**
     *
     * @return
     * The commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     *
     * @param commentText
     * The comment_text
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     *
     * @return
     * The commentTime
     */
    public String getCommentTime() {
        return commentTime;
    }

    /**
     *
     * @param commentTime
     * The comment_time
     */
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    /**
     *
     * @return
     * The commentTimelineId
     */
    public String getCommentTimelineId() {
        return commentTimelineId;
    }

    /**
     *
     * @param commentTimelineId
     * The comment_timeline_id
     */
    public void setCommentTimelineId(String commentTimelineId) {
        this.commentTimelineId = commentTimelineId;
    }

    /**
     *
     * @return
     * The commentTimelineUrl
     */
    public String getCommentTimelineUrl() {
        return commentTimelineUrl;
    }

    /**
     *
     * @param commentTimelineUrl
     * The comment_timeline_url
     */
    public void setCommentTimelineUrl(String commentTimelineUrl) {
        this.commentTimelineUrl = commentTimelineUrl;
    }

    /**
     *
     * @return
     * The commentTimelineUsername
     */
    public String getCommentTimelineUsername() {
        return commentTimelineUsername;
    }

    /**
     *
     * @param commentTimelineUsername
     * The comment_timeline_username
     */
    public void setCommentTimelineUsername(String commentTimelineUsername) {
        this.commentTimelineUsername = commentTimelineUsername;
    }

    /**
     *
     * @return
     * The commentTimelineName
     */
    public String getCommentTimelineName() {
        return commentTimelineName;
    }

    /**
     *
     * @param commentTimelineName
     * The comment_timeline_name
     */
    public void setCommentTimelineName(String commentTimelineName) {
        this.commentTimelineName = commentTimelineName;
    }

    /**
     *
     * @return
     * The commentTimelineThumbnailUrl
     */
    public String getCommentTimelineThumbnailUrl() {
        return commentTimelineThumbnailUrl;
    }

    /**
     *
     * @param commentTimelineThumbnailUrl
     * The comment_timeline_thumbnail_url
     */
    public void setCommentTimelineThumbnailUrl(String commentTimelineThumbnailUrl) {
        this.commentTimelineThumbnailUrl = commentTimelineThumbnailUrl;
    }

}
