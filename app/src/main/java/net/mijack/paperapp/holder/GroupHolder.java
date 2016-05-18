package net.mijack.paperapp.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.bean.PermissionGroupResult;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class GroupHolder extends RecyclerView.ViewHolder {
    private TextView groupLabel;
    private TextView groupName;
    private TextView groupDesc;
    private TextView groupStatisticName;

    public GroupHolder(View itemView) {
        super(itemView);
        groupLabel = (TextView) itemView.findViewById(R.id.groupLabel);
        groupName = (TextView) itemView.findViewById(R.id.groupName);
        groupDesc= (TextView) itemView.findViewById(R.id.groupDesc);
        groupStatisticName= (TextView) itemView.findViewById(R.id.groupStatisticName);
    }

    public void bindData(PermissionGroupResult result) {
        groupLabel.setText(result.getLabel());
        groupName.setText(result.getName());
        groupDesc.setText(result.getDescription());
    }
}
