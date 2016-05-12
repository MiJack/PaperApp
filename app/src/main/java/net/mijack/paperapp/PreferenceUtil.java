package net.mijack.paperapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static net.mijack.paperapp.Constant.BASE_URL;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class PreferenceUtil {
    public static void saveBaseUrl(Activity activity, String baseUrl) {
        SharedPreferences sp = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(BASE_URL, baseUrl);
        editor.commit();

    }
}
