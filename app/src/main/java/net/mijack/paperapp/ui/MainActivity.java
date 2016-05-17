package net.mijack.paperapp.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.mijack.paperapp.R;
import net.mijack.paperapp.api.Api;
import net.mijack.paperapp.api.ApiService;
import net.mijack.paperapp.bean.CreateResult;
import net.mijack.paperapp.bean.QueryResult;
import net.mijack.paperapp.bean.STATUS;
import net.mijack.paperapp.fragment.HistoryFragment;
import net.mijack.paperapp.fragment.HomeFragment;
import net.mijack.paperapp.fragment.SettingFragment;
import net.mijack.paperapp.rx.BaseSubscriber;
import net.mijack.paperapp.util.PreferenceUtil;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener /*implements DialogInterface.OnClickListener */ {
    private static final int REQUEST_CODE_SCAN = 1;
    NavigationView navigationView;
    FrameLayout frameLayout;
    Fragment currentFragment = null;
    SparseArray<Fragment> sparseArray;
    Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        sparseArray = new SparseArray<>();
        changeFragment(R.id.action_main);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_scan:
                Intent intent = new Intent(this, ScannerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                changeFragment(R.id.action_main);
                return true;
            case R.id.action_main:
            case R.id.action_history:
            case R.id.action_setting:
                changeFragment(itemId);
                return true;
            default:
                return false;
        }
    }

    private void changeFragment(int itemId) {
        drawerLayout.closeDrawers();
        Fragment fragment = sparseArray.get(itemId);
        boolean createFragment = fragment == null;
        if (createFragment) {
            switch (itemId) {
                case R.id.action_main:
                    fragment = new HomeFragment();
                    break;
                case R.id.action_history:
                    fragment = new HistoryFragment();
                    break;
                case R.id.action_setting:
                    fragment = new SettingFragment();
                    break;
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (createFragment) {
            transaction.add(R.id.frameLayout, fragment);
        } else {
            transaction.show(fragment);
        }
        currentFragment = fragment;
        transaction.commit();
    }

//    private static final int REQUEST_CODE_SCAN = 1;
//    TextView textView;
//    Button button;
//    private Api api;
//    private EditText editText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        api = ApiService.getService(Api.class);
//        textView = (TextView) findViewById(R.id.text);
//        button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SCAN);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SCAN) {
//            String result = data.getStringExtra("result");
//            textView.setText(result);
////            发起网络请求
//            api.createTask(result)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .filter(new Func1<CreateResult, Boolean>() {
//                        @Override
//                        public Boolean call(CreateResult createResult) {
//                            STATUS status = createResult.getStatus();
//                            switch (status) {
//                                case WAIT:
//                                    Toast.makeText(MainActivity.this, "任务已提交，等待下载", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case DOING:
//                                    Toast.makeText(MainActivity.this, "任务已下载", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case UNKNOWN:
//                                    Toast.makeText(MainActivity.this, "提交异常", Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                            return status.equals(STATUS.FINISH);
//                        }
//                    })
//                    .observeOn(Schedulers.io())
//                    .flatMap(new Func1<CreateResult, Observable<QueryResult>>() {
//                        @Override
//                        public Observable<QueryResult> call(CreateResult createResult) {
//                            String localPath = createResult.getLocalPath();
//                            int start = localPath.lastIndexOf("\\");
//                            String md5 = localPath.substring(start + 1, localPath.length() - 4);
//                            return api.queryTask(md5);
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new BaseSubscriber<QueryResult>(){
//                        @Override
//                        public void onNext(QueryResult queryResult) {
//
//                        }
//                    })
//            ;
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.setting) {
//            showSetting();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showSetting() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view = LayoutInflater.from(this)
//                .inflate(R.layout.dialog_setting, null);
//        editText = (EditText) view.findViewById(R.id.editText);
//        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        ClipData cd = cm.getPrimaryClip();
//        String string = null;
//        try {
//            string = cd.getItemAt(0).getText().toString();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//        if (!TextUtils.isEmpty(string)) {
//            editText.setText(string);
//        }
//        builder.setTitle(getString(R.string.set_base_url))
//                .setView(view)
//                .setNegativeButton("取消", this)
//                .setPositiveButton("确定", this)
//                .create()
//                .show();
//    }
//
//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//        if (which == DialogInterface.BUTTON_POSITIVE) {
//            if (editText != null) {
//                String baseUrl = editText.getText().toString();
//                PreferenceUtil.saveBaseUrl(getApplication(), baseUrl);
//            }
//        }
//    }
}
