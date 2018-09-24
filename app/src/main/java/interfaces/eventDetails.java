package interfaces;


import com.evenzt.deleteBean;

import PostedPOJO.PostedBean;
import acceptPOJO.acceptBean;
import allRequestsPOJO.allRequestBean;
import blockPOJO.blockBean;
import blockUserListPOJO.blockListBean;
import categoryPOJO.categoryBean;
import chatPOJO.chatBean;
import commentPOJO.commentBean;
import eventDetailPOJO.eventDetailBean;
import eventRequestPOJO.erBean;
import friendsPOJO.friendsBean;
import inviteBean.sendInviteBean;
import joinPOJO.joinBean;
import joinedUsersPOJO.joinedUsersBean;
import leavePOJO.leaveBean;
import messCountPOJO.messCountBean;
import messagePOJO.messageBean;
import nearbyPOJO.nearbyBean;
import notificationPOJO.notificationBean;
import profilePOJO.profileBean;
import removeImagePOJO.removeImageBean;
import reportPOJO.reportBean;
import reportUserPOJO.reportUserBean;
import requestPOJO.requestBean;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import reviewPOJO.reviewBean;
import searchPOJO.searchBean;
import sendMessagePOJO.sendMessageBean;
import userSearchPOJO.userSearchBean;
import usersPOJO.usersBean;

public interface eventDetails {


    @Multipart
    @POST("eventmanagement/api/eventdetail")
    Call<eventDetailBean> getAllEvents(@Part("eventId") String userId , @Part("userId") String pno);

    @Multipart
    @POST("eventmanagement/api/likeevent")
    Call<eventDetailBean> likeEvent(@Part("eventId") String userId , @Part("userId") String pno);

    @Multipart
    @POST("eventmanagement/api/joinevent")
    Call<joinBean> joinEvent(@Part("eventId") String userId , @Part("userId") String pno);

    @Multipart
    @POST("eventmanagement/api/leaveevent")
    Call<leaveBean> leaveEvent(@Part("eventId") String userId , @Part("userId") String pno);

    @Multipart
    @POST("eventmanagement/api/profiledetail")
    Call<profileBean> userDetail(@Part("profileId") String userId , @Part("userId") String pno);

    @Multipart
    @POST("eventmanagement/api/editprofile")
    Call<profileBean> editProfile(@Part("userId") String userId , @Part("country") String country , @Part("interest") String interest , @Part("occupation") String occupation , @Part("education") String education , @Part("bio") String bio , @Part("phone") String phone , @Part("age") String age , @Part("organization") String organ , @Part("gender") String gender , @Part("fullName") String fullName);

    @Multipart
    @POST("eventmanagement/api/myevents")
    Call<PostedBean> myEvents(@Part("userId") String userId , @Part("pno") String pno);

    @Multipart
    @POST("eventmanagement/api/joinedevents")
    Call<PostedBean> joinedEvents(@Part("userId") String userId , @Part("pno") String pno);

    @Multipart
    @POST("eventmanagement/api/friends")
    Call<friendsBean> getFriends(@Part("userId") String userId);

    @Multipart
    @POST("eventmanagement/api/users")
    Call<usersBean> getUsers(@Part("userId") String userId , @Part("pno") String pno);

    @Multipart
    @POST("eventmanagement/api/eventjoined")
        Call<joinedUsersBean> getJoinedUsers(@Part("userId") String userId , @Part("eventId") String evnetId);

    @Multipart
    @POST("eventmanagement/api/addfriend")
    Call<requestBean> addFriend(@Part("userId") String userId , @Part("friendId") String friendId);

    @Multipart
    @POST("eventmanagement/api/requests")
    Call<allRequestBean> getAllRequests(@Part("userId") String userId);

    @Multipart
    @POST("eventmanagement/api/acceptrequest")
    Call<profileBean> acceptRequest(@Part("userId") String userId , @Part("friendId") String friendId);

    @Multipart
    @POST("eventmanagement/api/canclerequest")
    Call<profileBean> declineRequest(@Part("userId") String userId , @Part("friendId") String friendId);

    @Multipart
    @POST("eventmanagement/api/searchevent")
    Call<searchBean> searchQuery(@Part("userId") String userId , @Part("search") String search);

    @Multipart
    @POST("eventmanagement/api/searchevent")
    Call<nearbyBean> stateFilter(@Part("userId") String userId , @Part("search") String search);

    @Multipart
    @POST("eventmanagement/api/categories")
    Call<categoryBean> getAllCategories(@Part("userId") String userId);


    @Multipart
    @POST("eventmanagement/api/conversation")
    Call<messageBean> getAllMessages(@Part("userId") String userId);

    @Multipart
    @POST("eventmanagement/api/loadmessage")
    Call<chatBean> loadMessages(@Part("userId") String userId , @Part("friendId") String friendId);

    @Multipart
    @POST("eventmanagement/api/sendchat")
    Call<sendMessageBean> sendMessage(@Part("userId") String userId , @Part("friendId") String friendId , @Part("text") String message , @Part("isQuery") String query , @Part("eventName") String eventName);

    @Multipart
    @POST("eventmanagement/api/AlluserComment")
    Call<reviewBean> getReviews(@Part("userId") String userId);

    @Multipart
    @POST("eventmanagement/api/UserComment")
    Call<commentBean> comment(@Part("userId") String userId , @Part("timelineId") String timelineId , @Part("text") String text , @Part("rating") String rating);

    @Multipart
    @POST("eventmanagement/api/eventrequests")
    Call<erBean> getEventRequests(@Part("userId") String userId , @Part("eventId") String eventId);


    @Multipart
    @POST("eventmanagement/api/sendinvite")
    Call<String> sendInvite(@Part("userId") String userId , @Part("eventId") String eventId);


    @Multipart
    @POST("eventmanagement/api/notifications")
    Call<notificationBean> getNotification(@Part("userId") String userId , @Part("new") String New);

    @Multipart
    @POST("eventmanagement/api/eventaccept")
    Call<acceptBean> acceptEvent(@Part("userId") String userId , @Part("eventId") String eventId);

    @Multipart
    @POST("eventmanagement/api/eventreject")
    Call<acceptBean> rejectEvent(@Part("userId") String userId , @Part("eventId") String eventId);

    @Multipart
    @POST("eventmanagement/api/blockuser")
    Call<blockBean> blockUser(@Part("userId") String userId , @Part("friendId") String friendId);

    @Multipart
    @POST("eventmanagement/api/unblockuser")
    Call<blockBean> unblockUser(@Part("userId") String userId , @Part("friendId") String friendId);

    @Multipart
    @POST("eventmanagement/api/blockuserlist")
    Call<blockListBean> blockList(@Part("userId") String userId);

    @Multipart
    @POST("eventmanagement/api/searchuser")
    Call<userSearchBean> searchUser(@Part("userId") String userId , @Part("search") String search);

    @Multipart
    @POST("eventmanagement/api/advsearch")
    Call<searchBean> advSearch(@Part("userId") String userId , @Part("country") String country , @Part("state") String state , @Part("city") String city , @Part("keywords") String keywords , @Part("category") String category , @Part("name") String title);

    @Multipart
    @POST("eventmanagement/api/deleteevent")
    Call<deleteBean> deleteEvent(@Part("userId") String userId , @Part("eventId") String eventId);

    @Multipart
    @POST("eventmanagement/api/syncuser")
    Call<profileBean> syncUser(@Part("userId") String userId , @Part("pid") String pid , @Part("email") String email , @Part("provider") String provider);

    @Multipart
    @POST("eventmanagement/api/messages")
    Call<messCountBean> getCount(@Part("userId") String userId);

    @Multipart
    @POST("eventmanagement/api/deleteEventImage")
    Call<removeImageBean> removeImage(@Part("userId") String userId , @Part("imageId") String imageId);

    @Multipart
    @POST("eventmanagement/api/reportevent")
    Call<reportBean> reportEvent(@Part("userId") String userId , @Part("eventId") String eventId , @Part("resion") String reason);

    @Multipart
    @POST("eventmanagement/api/reportuser")
    Call<reportUserBean> reportUser(@Part("userId") String userId , @Part("reportedUser") String eventId , @Part("resion") String reason);

    @Multipart
    @POST("eventmanagement/api/SendVerificationCode")
    Call<String> resendVerification(@Part("id") String userId);

}
