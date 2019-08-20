package com.yanhua.mvvmlibrary.http.encode;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.yanhua.mvvmlibrary.http.utils.RetrofitClient;
import com.yanhua.mvvmlibrary.ssm.PublicGmResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class EncodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private Gson gson;
    private TypeAdapter<T> adapter;

    public EncodeRequestBodyConverter(T p0, TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    public EncodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T o) throws IOException {
        if (RetrofitClient.enCodeType == 1) {
            return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), (String) PublicGmResult.success(adapter.toJson(o), RetrofitClient.encryptionRule).getData());
        } else {
            return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), adapter.toJson(o));
        }
    }
}