package interfaces;

import POJOs.Regis;
import googleLoginPOJO.googleSignin;
import googlePOJO.googleLogin;
import loginPOJO.logo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface google {
    @FormUrlEncoded
    @POST("eventmanagement/api/register")
    Call<googleLogin> registerWithgoogle(
            @Field("provider") String provider,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String pass,
            @Field("pid") String password
    );




    @FormUrlEncoded
    @POST("eventmanagement/api/login")
    Call<googleSignin> loginwithgoogle(
            @Field("provider") String provider,
            @Field("email") String username,
            @Field("pid") String password
    );


}
