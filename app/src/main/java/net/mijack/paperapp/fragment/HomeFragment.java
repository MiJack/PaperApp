package net.mijack.paperapp.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import net.mijack.paperapp.R;
import net.mijack.paperapp.api.Api;
import net.mijack.paperapp.api.ApiService;
import net.mijack.paperapp.api.FileDownloadApi;
import net.mijack.paperapp.bean.CreateResult;
import net.mijack.paperapp.bean.QueryResult;
import net.mijack.paperapp.bean.STATUS;
import net.mijack.paperapp.db.HistoryDAO;
import net.mijack.paperapp.rx.BaseSubscriber;
import net.mijack.paperapp.ui.ScannerActivity;

import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private View view;
    private static final int REQUEST_CODE_SCAN = 1;
    Api api;
    private Button button;
    private Button button1;
    private Button button2;
    private ResultFragment resultFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        resultFragment = new ResultFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container, resultFragment)
                .commit();
        api = ApiService.getService(Api.class);
        button = (Button) view.findViewById(R.id.button);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan(getActivity());
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInstall(true);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInstall(false);
            }
        });
        return view;
    }

    private void postInstall(final boolean install) {
        if (resultFragment == null) {
            return;
        }
        QueryResult result = resultFragment.getQueryResult();
        String fileMd5 = result!=null?result.getFileMd5():"0A27B86BEEBEA5D1166E4B8E28940CD3";
        api.statistics(fileMd5, install)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Map<String, String>, Boolean>() {
                    @Override
                    public Boolean call(Map<String, String> stringStringMap) {
                        String result = stringStringMap.get("install");
                        boolean b = "true".equalsIgnoreCase(result);
                        if (!b) {
                            Toast.makeText(getActivity(), "感谢你的反馈", Toast.LENGTH_SHORT).show();
                        }
                        return b;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Map<String, String>, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Map<String, String> stringStringMap) {
                        String md5 = null;
                        if (stringStringMap != null) {
                            md5 = stringStringMap.get("md5");
                            if (TextUtils.isEmpty(md5)) {
                                return null;
                            }
                        }
                        return FileDownloadApi.downloadFile(md5, "/paperapp/");
                    }
                })
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
                        getActivity().startActivity(intent);
                    }
                })
        ;
    }

    public void scan(Context context) {
        Intent intent = new Intent(context, ScannerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SCAN) {
            String result = data.getStringExtra("result");
//            textView.setText(result);
//            发起网络请求
            analyze(result);
        }
    }

    public void analyze(String url) {
        api.createTask(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CreateResult, Boolean>() {
                    @Override
                    public Boolean call(CreateResult createResult) {
                        String apkUrl = createResult.getUrl();
                        String fileMd5 = createResult.getFileMD5();
                        String createTime = String.valueOf(System.currentTimeMillis());
                        HistoryDAO.insertOrUpdateHistory(getActivity(), apkUrl, fileMd5, createTime);
                        STATUS status = createResult.getStatus();
                        switch (status) {
                            case WAIT:
                                Toast.makeText(getActivity(), "任务已提交，等待下载", Toast.LENGTH_SHORT).show();
                                break;
                            case DOING:
                                Toast.makeText(getActivity(), "任务已下载", Toast.LENGTH_SHORT).show();
                                break;
                            case UNKNOWN:
                                Toast.makeText(getActivity(), "提交异常", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return status.equals(STATUS.FINISH);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Func1<CreateResult, Observable<Response<QueryResult>>>() {
                    @Override
                    public Observable<Response<QueryResult>> call(CreateResult createResult) {
                        return api.queryTask(createResult.getFileMD5());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Response<QueryResult>>() {
                    @Override
                    public void onNext(Response<QueryResult> response) {
                        resultFragment.update(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error", e);
                    }
                })
        ;
    }
}
