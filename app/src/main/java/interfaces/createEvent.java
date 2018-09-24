package interfaces;


import com.squareup.okhttp.RequestBody;

import ViewPOJO.viewBean;
import createPOJO.createBean;
import eventDetailPOJO.eventDetailBean;
import nearbyPOJO.nearbyBean;
import okhttp3.MultipartBody;
import profilePOJO.profileBean;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface createEvent {
    @Multipart
    @POST("eventmanagement/api/createevent")
    Call<createBean> createEventWithImage(@Part("userId") String userId, @Part("catId") String catId, @Part("eventName") String eventName , @Part("eventDesc") String eventDesc , @Part("startTime") String startTime , @Part("endTime") String endTime , @Part("eventTime") String eventTime , @Part("venue") String venue , @Part("latitude") String latitude , @Part("longitude") String longitude , @Part("moreDetail") String moreDetail , @Part MultipartBody.Part file , @Part("eventType") String eventType , @Part("eventEnd") String eventEnd , @Part("state") String state , @Part("country") String country , @Part("city") String city , @Part("keywords") String keys);

    @Multipart
    @POST("eventmanagement/api/addeventimage")
    Call<eventDetailBean> editEvent(@Part("userId") String userId, @Part MultipartBody.Part file, @Part("eventId") String eventId);

    //@Multipart
    //@POST("eventmanagement/api/createevent")
    //Call<createBean> createEventWithoutImage(@Part("userId") String userId, @Part("catId") String catId, @Part("eventName") String eventName , @Part("eventDesc") String eventDesc , @Part("startTime") String startTime , @Part("endTime") String endTime , @Part("eventTime") String eventTime , @Part("venue") String venue , @Part("latitude") String latitude , @Part("longitude") String longitude , @Part("moreDetail") String moreDetail , @Part("eventType") String eventType);

    @Multipart
    @POST("eventmanagement/api/allevents")
    Call<viewBean> getAllEvents(@Part("userId") String userId , @Part("pno") String pno);


    @Multipart
    @POST("eventmanagement/api/nearbyevent")
    Call<nearbyBean> nearbyEvents(@Part("userId") String userId , @Part("latitude") String latitude , @Part("longitude") String longitude);


    @Multipart
    @POST("eventmanagement/api/updateprofileimage")
    Call<profileBean> updateProfileImage(@Part("userId") String userId , @Part MultipartBody.Part file);

    @Multipart
    @POST("eventmanagement/api/updatecoverimage")
    Call<profileBean> updateCoverImage(@Part("userId") String userId , @Part MultipartBody.Part file);

    @Multipart
    @POST("eventmanagement/api/editusername")
    Call<profileBean> updateUserName(@Part("userId") String userId , @Part("username") String name);




}
