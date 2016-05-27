package net.mijack.paperapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.mijack.paperapp.R;
import net.mijack.paperapp.adapter.HistoryAdapter;
import net.mijack.paperapp.db.HistoryDAO;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class HistoryFragment extends BaseFragment {
    private ListView listView;
    private HistoryAdapter historyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = (ListView) inflater.inflate(R.layout.listview, container, false);
        Cursor c = createCursor();
        historyAdapter = new HistoryAdapter(getActivity(), c);
        listView.setAdapter(historyAdapter);
        return listView;
    }

    private Cursor createCursor() {
        return HistoryDAO.getQueryCursor(getActivity());
    }

}
