package interfaces;

import com.evenzt.forgotBean;

import POJOs.Regis;
import loginPOJO.logo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.Call;


public interface register {
    @FormUrlEncoded
    @POST("eventmanagement/api/register")
    Call<Regis> registerWithEmail(
                @Field("provider") String provider,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );




    @FormUrlEncoded
    @POST("eventmanagement/api/login")
    Call<logo> loginwithemail(
            @Field("provider") String provider,
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("eventmanagement/api/forgotpassword")
    Call<forgotBean> forgot(
            @Field("email") String email
    );

}
