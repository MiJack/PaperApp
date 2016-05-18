package net.mijack.paperapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mijack.paperapp.R;
import net.mijack.paperapp.bean.PermissionResult;
import net.mijack.paperapp.holder.PermissionHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class PermissionAdapter extends RecyclerView.Adapter<PermissionHolder> {
    private List<PermissionResult> permissions;

    public PermissionAdapter(Map<String, PermissionResult> permissionMap) {
        this.permissions =new ArrayList<>();
        permissions.addAll(permissionMap.values());
    }

    @Override
    public PermissionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_permission,parent,false);
        return new PermissionHolder(view);
    }

    @Override
    public void onBindViewHolder(PermissionHolder holder, int position) {
        holder.bindData(permissions.get(position),position);
    }

    @Override
    public int getItemCount() {
        return permissions==null?0:permissions.size();
    }
}
