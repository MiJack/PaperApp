package net.mijack.paperapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.bean.QueryResult;

import org.w3c.dom.Text;

import retrofit2.Response;

/**
 * @author MiJack
 * @since 2016/5/18
 */
public class ResultFragment extends Fragment {
    private View view;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        textView = (TextView) view.findViewById(R.id.textView1);
        return view;
    }

    public void update(Response<QueryResult> response) {
        textView.setText(response.body().toString());
    }
}
