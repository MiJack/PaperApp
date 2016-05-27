package net.mijack.paperapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class SettingFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, container, false);
        textView.setText(this.getClass().getName());
        return textView;
    }
}
