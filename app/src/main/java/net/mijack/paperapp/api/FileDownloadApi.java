package net.mijack.paperapp.api;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by MiJack on 2016/5/30.
 */

public class FileDownloadApi {
    public static Observable<Uri> downloadFile(final String md5, final String dir) {
        return Observable.create(new Observable.OnSubscribe<Uri>() {
            @Override
            public void call(Subscriber<? super Uri> subscriber) {
                OkHttpClient client = ApiService.getClient();
                Call call = client.newCall(new Request.Builder().url(ApiService.baseUrl + "downloadFile?md5=" + md5).build());
                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        String apkName = response.header("File-Name");
                        String filePath = getSDPath().getPath() + dir + apkName;
                        writeResponseBodyToDisk(response.body(),filePath);
                        Uri uri = Uri.parse("file://" + filePath);
                        subscriber.onNext(uri);
                        subscriber.onCompleted();
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                    return;
                }
            }
        });
    }
    private static boolean writeResponseBodyToDisk(ResponseBody body, String filePath) {
        try {
            File apkFile = new File(filePath);
            makeSureFile(apkFile);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(apkFile,false);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }

        return sdDir;

    }
    public static boolean makeSureFile(File file) {
        if (file.exists()) {
            return true;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }

    }
}
