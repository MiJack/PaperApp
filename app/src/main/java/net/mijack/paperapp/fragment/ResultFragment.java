package net.mijack.paperapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.adapter.PermissionGroupAdapter;
import net.mijack.paperapp.bean.PermissionGroupResult;
import net.mijack.paperapp.bean.QueryResult;
import net.mijack.paperapp.callback.RecyclerViewItemTouchListener;
import net.mijack.paperapp.view.ColorItemDecoration;
import net.mijack.paperapp.view.PermissionsResultDialog;

import retrofit2.Response;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class ResultFragment extends BaseFragment {
    private View view;
    private TextView textView;
    private TextView appName;
    private TextView appPackageName;
    private TextView appVersionCode;
    private TextView appVersionName;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private PermissionGroupAdapter groupAdapter;
    private RecyclerViewItemTouchListener.OnItemClickListener itemClickListener=new RecyclerViewItemTouchListener.SimpleItemClickListener(){
        @Override
        public void onItemClick(View view, int position) {
            Context context = getActivity();
            PermissionGroupResult item = groupAdapter.getItem(position);
            PermissionsResultDialog dialog=new PermissionsResultDialog(context);
            dialog.setItem(item);
            dialog.show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        textView = (TextView) view.findViewById(R.id.textView1);
        appName = (TextView) view.findViewById(R.id.appName);
        appPackageName = (TextView) view.findViewById(R.id.appPackageName);
        appVersionCode = (TextView) view.findViewById(R.id.appVersionCode);
        appVersionName = (TextView) view.findViewById(R.id.appVersionName);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        groupAdapter = new PermissionGroupAdapter();
        recyclerView.setAdapter(groupAdapter);
        ColorItemDecoration decoration = new ColorItemDecoration();
         recyclerView.addItemDecoration(decoration);
        RecyclerViewItemTouchListener listener = new RecyclerViewItemTouchListener(recyclerView, itemClickListener);
        recyclerView.addOnItemTouchListener(listener);
        return view;
    }

    public void update(Response<QueryResult> response) {
        QueryResult queryResult = response.body();
        textView.setText(queryResult.toString());
        appName.setText(queryResult.getApkLabel());
        appPackageName.setText(queryResult.getPackageName());
        appVersionCode.setText(queryResult.getVersionCode());
        appVersionName.setText(queryResult.getVersionName());
        groupAdapter.setData(queryResult);
    }
}
