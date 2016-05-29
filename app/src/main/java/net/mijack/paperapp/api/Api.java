package net.mijack.paperapp.api;

import net.mijack.paperapp.bean.CreateResult;
import net.mijack.paperapp.bean.QueryResult;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public interface Api {
    @GET("createTask")
    Observable<CreateResult> createTask(@Query("apk_url") String apkUrl);

    @GET("queryTask")
    Observable<Response<QueryResult>> queryTask(@Query("md5") String md5);

    @GET("statistics")
    Observable<Map<String, String>> statistics(@Query("md5") String md5, @Query("install") boolean install);
}
