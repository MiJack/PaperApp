package net.mijack.paperapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.mijack.paperapp.R;
import net.mijack.paperapp.api.Api;
import net.mijack.paperapp.api.ApiService;
import net.mijack.paperapp.bean.CreateResult;
import net.mijack.paperapp.bean.QueryResult;
import net.mijack.paperapp.bean.STATUS;
import net.mijack.paperapp.rx.BaseSubscriber;
import net.mijack.paperapp.ui.ScannerActivity;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private View view;
    private static final int REQUEST_CODE_SCAN = 1;
    Api api;
    private Button button;
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScannerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SCAN) {
            String result = data.getStringExtra("result");
//            textView.setText(result);
//            发起网络请求
            api.createTask(result)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(new Func1<CreateResult, Boolean>() {
                        @Override
                        public Boolean call(CreateResult createResult) {
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
                            String localPath = createResult.getLocalPath();
                            int start = localPath.lastIndexOf("\\");
                            String md5 = localPath.substring(start + 1, localPath.length() - 4);
                            return api.queryTask(md5);
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
}
