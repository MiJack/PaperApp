package net.mijack.paperapp.api;

import android.app.Application;
import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.mijack.paperapp.util.PreferenceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class ApiService {

    public static String baseUrl;
    private static Retrofit retrofit;
    private static Map<String, Object> serviceMap;
    private static OkHttpClient client;
    private static ObjectMapper mapper;

    public static void init(Application application) {
        baseUrl = PreferenceUtil.getBaseUrl(application);
        if (TextUtils.isEmpty(baseUrl)){
            baseUrl="http://192.168.155.1:8080/PaperServer/";
        }
        serviceMap = new HashMap<>();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        StethoInterceptor stethoInterceptor = new StethoInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(stethoInterceptor)
                .addInterceptor(logging);
        client = builder.build();

        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        mapper.setDateFormat(dateFormat);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(client)
                .build();
    }

    public static <T> T getService(Class<T> service) {
        T instance = null;
        String key = service.getName();
        try {
            instance = (T) serviceMap.get(key);
        } catch (Exception e) {
        }
        if (instance == null) {
            instance = retrofit.create(service);
            serviceMap.put(key, instance);
        }
        return instance;
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
