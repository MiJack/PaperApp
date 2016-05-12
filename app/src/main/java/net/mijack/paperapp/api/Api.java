package net.mijack.paperapp.api;

import net.mijack.paperapp.bean.CreateResult;
import net.mijack.paperapp.bean.QueryResult;

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
    Observable<QueryResult> queryTask(@Query("md5") String md5);
}
