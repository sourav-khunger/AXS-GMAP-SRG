package com.doozycod.axs.ApiService;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.BatchRoutePath;
import com.doozycod.axs.POJO.ConfirmNextModel;
import com.doozycod.axs.POJO.DriverDepartureResponse;
import com.doozycod.axs.POJO.DriverLogDutyResponse;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.StatusReasonResponse;
import com.doozycod.axs.POJO.TaskInfoResponse;
import com.doozycod.axs.POJO.TaskInfoUpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse> getLoginResponse(@Field("username") String username);

    @POST("complete-task")
    Call<TaskInfoUpdateResponse> updateTaskInfo(@Body TaskInfoEntity taskInfoEntity,
                                                @Header("Authorization") String authorizationHeader);

    @POST("confirm-dc-departure")
    @FormUrlEncoded
    Call<DriverDepartureResponse> comfirmDCdeparture(
            @Field("batchId") String batchId,
            @Field("departureTime") String departureTime,
            @Header("Authorization") String authorizationHeader);

    @POST("confirm-nextstop")
    @FormUrlEncoded
    Call<ConfirmNextModel> confirmNextStop(
            @Field("batchId") String batchId,
            @Field("locationKey") String locationKey,
            @Header("Authorization") String authorizationHeader);

    @GET("batch-route-path")
    Call<BatchRoutePath> batchRoutePath(
            @Query("batchId") String batchId,
            @Header("Authorization") String authorizationHeader);

    @POST("driver-logduty")
    @FormUrlEncoded
    Call<DriverLogDutyResponse> driverLogDuty(@Field("companyId") String companyId, @Field("logStatus") String logStatus,
                                              @Header("Authorization") String authorizationHeader);

    @GET("driver-tasklist")
    Call<TaskInfoResponse> getTaskList(@Query("companyId") String companyId, @Query("taskDate") String taskDate,
                                       @Header("Authorization") String authorizationHeader);

    @GET("shipment-statuslist")
    Call<StatusReasonResponse> shipmentStatus(@Query("companyId") String companyId,
                                              @Header("Authorization") String authorizationHeader);
}