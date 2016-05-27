package net.mijack.paperapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import net.mijack.paperapp.api.ApiService;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class PaperApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ApiService.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
