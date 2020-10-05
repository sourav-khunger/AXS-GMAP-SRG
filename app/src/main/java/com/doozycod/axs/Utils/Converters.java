package com.doozycod.axs.Utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.doozycod.axs.POJO.AllowedCompanyInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<AllowedCompanyInfo> fromString(String value) {
        Type listType = new TypeToken<ArrayList<AllowedCompanyInfo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<AllowedCompanyInfo> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
