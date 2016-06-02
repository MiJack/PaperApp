package net.mijack.paperapp.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import net.mijack.paperapp.api.ApiService;

import static net.mijack.paperapp.util.Constant.BASE_URL;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class PreferenceUtil {
    public static final String name = "setting";

    public static void saveBaseUrl(Application application, String baseUrl) {
        SharedPreferences sp = application.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(BASE_URL, baseUrl);
        editor.commit();
        ApiService.baseUrl=baseUrl;

    }

    public static String getBaseUrl(Application application) {
        SharedPreferences sp = application.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(BASE_URL,null);
    }
}
