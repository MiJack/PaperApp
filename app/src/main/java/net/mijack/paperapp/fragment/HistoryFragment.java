package net.mijack.paperapp.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.adapter.HistoryAdapter;
import net.mijack.paperapp.adapter.Holder;
import net.mijack.paperapp.db.HistoryDAO;
import net.mijack.paperapp.ui.MainActivity;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class HistoryFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private HistoryAdapter historyAdapter;
    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = (ListView) inflater.inflate(R.layout.listview, container, false);
        Cursor c = createCursor();
        historyAdapter = new HistoryAdapter(getActivity(), c);
        listView.setAdapter(historyAdapter);
        listView.setOnItemClickListener(this);
        return listView;
    }

    private Cursor createCursor() {
        return HistoryDAO.getQueryCursor(getActivity());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Holder h = (Holder) view.getTag();
        if (activity != null) {
            activity.changeUrl(h.url);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}
