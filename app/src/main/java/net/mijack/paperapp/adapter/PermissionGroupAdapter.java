package net.mijack.paperapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mijack.paperapp.R;
import net.mijack.paperapp.bean.PermissionGroupResult;
import net.mijack.paperapp.bean.QueryResult;
import net.mijack.paperapp.holder.GroupHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class PermissionGroupAdapter extends RecyclerView.Adapter<GroupHolder> {
    private List<PermissionGroupResult> datas;

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.holder_group, parent, false);
        GroupHolder holder = new GroupHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupHolder holder, int position) {
        PermissionGroupResult result = getItem(position);
        holder.bindData(result);
    }

    public PermissionGroupResult getItem(int position) {
        return datas.get(position);
    }
    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setData(QueryResult data) {
        datas = new ArrayList<>();
        Map<String, PermissionGroupResult> groupResultMap = data.getPermissionGroupResultMap();
        if (groupResultMap != null && groupResultMap.size() > 0) {
            datas.addAll(groupResultMap.values());
        }
        this.notifyDataSetChanged();
    }

}
