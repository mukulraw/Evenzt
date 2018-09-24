package reviewPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum {

    @SerializedName("comment_id")
    @Expose
    private String commentId;
    @SerializedName("comment_text")
    @Expose
    private String commentText;
    @SerializedName("rating")
    @Expose
    private String rating;
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentTimelineId() {
        return commentTimelineId;
    }

    public void setCommentTimelineId(String commentTimelineId) {
        this.commentTimelineId = commentTimelineId;
    }

    public String getCommentTimelineUrl() {
        return commentTimelineUrl;
    }

    public void setCommentTimelineUrl(String commentTimelineUrl) {
        this.commentTimelineUrl = commentTimelineUrl;
    }

    public String getCommentTimelineUsername() {
        return commentTimelineUsername;
    }

    public void setCommentTimelineUsername(String commentTimelineUsername) {
        this.commentTimelineUsername = commentTimelineUsername;
    }

    public String getCommentTimelineName() {
        return commentTimelineName;
    }

    public void setCommentTimelineName(String commentTimelineName) {
        this.commentTimelineName = commentTimelineName;
    }

    public String getCommentTimelineThumbnailUrl() {
        return commentTimelineThumbnailUrl;
    }

    public void setCommentTimelineThumbnailUrl(String commentTimelineThumbnailUrl) {
        this.commentTimelineThumbnailUrl = commentTimelineThumbnailUrl;
    }
}
