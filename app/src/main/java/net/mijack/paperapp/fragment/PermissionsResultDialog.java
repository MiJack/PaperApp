package net.mijack.paperapp.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.adapter.PermissionAdapter;
import net.mijack.paperapp.bean.PermissionGroupResult;
import net.mijack.paperapp.view.ColorItemDecoration;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class PermissionsResultDialog extends BottomSheetDialog {
    private TextView groupLabel;
    private TextView groupName;
    private TextView groupDesc;
    private TextView groupStatisticName;
    private RecyclerView recyclerView;

    public PermissionsResultDialog(@NonNull Context context) {
        super(context);
    }

    public void setItem(PermissionGroupResult result) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_permission_result, null);
        setContentView(view);
        groupLabel = (TextView) view.findViewById(R.id.groupLabel);
        groupName = (TextView) view.findViewById(R.id.groupName);
        groupDesc = (TextView) view.findViewById(R.id.groupDesc);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        groupStatisticName = (TextView) view.findViewById(R.id.groupStatisticName);
        groupLabel.setText(result.getLabel()+"的权限汇总");
        groupName.setText(result.getName());
        groupDesc.setText(result.getDescription());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ColorItemDecoration());
        recyclerView.setAdapter(new PermissionAdapter(result.getPermissionMap())/* {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false)) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView)holder.itemView).setText("Item "+position);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        }*/);
    }
}
