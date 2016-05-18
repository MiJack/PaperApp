package net.mijack.paperapp.holder;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.bean.PermissionResult;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class PermissionHolder extends RecyclerView.ViewHolder {
    private TextView itemLabel;
    private TextView itemName;
    private TextView itemDesc;
    private TextView itemStatisticName;

    public PermissionHolder(View itemView) {
        super(itemView);
        itemLabel = (TextView) itemView.findViewById(R.id.itemLabel);
        itemName = (TextView) itemView.findViewById(R.id.itemName);
        itemDesc = (TextView) itemView.findViewById(R.id.itemDesc);
        itemStatisticName = (TextView) itemView.findViewById(R.id.itemStatisticName);
    }

    public void bindData(PermissionResult result, int position) {
        Resources resources = itemStatisticName.getResources();
        itemLabel.setText("(" + String.valueOf(position + 1) + ")" + result.getLabel());
        itemName.setText(result.getName());
        itemDesc.setText(result.getDescription());
        if (result.getCount() > 0) {
            itemStatisticName.setText(resources.getString(R.string.api_statistic,
                    result.getCount()));
            itemStatisticName.setVisibility(View.VISIBLE);
        } else {
            itemStatisticName.setVisibility(View.GONE);
        }
    }
}
