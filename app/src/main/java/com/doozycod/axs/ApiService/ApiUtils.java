package com.doozycod.axs.ApiService;

public class ApiUtils {
    //      api service for webservices
    private ApiUtils() {}
    public static final String BASE_URL = "http://access.gcphone.pw/accessapi/public/api/";
    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
