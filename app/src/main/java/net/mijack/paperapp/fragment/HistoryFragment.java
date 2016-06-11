package net.mijack.paperapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.mijack.paperapp.R;
import net.mijack.paperapp.adapter.HistoryAdapter;
import net.mijack.paperapp.adapter.Holder;
import net.mijack.paperapp.db.HistoryDAO;
import net.mijack.paperapp.ui.MainActivity;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class HistoryFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
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
        loadData();
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        return listView;
    }

    private void loadData() {
        Cursor c = createCursor();
        historyAdapter = new HistoryAdapter(getActivity(), c);
        listView.setAdapter(historyAdapter);
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

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        Object tag = v.getTag();
        if (tag != null && tag instanceof Holder) {
            final Holder h = (Holder) tag;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("删除")
                    .setMessage(getString(R.string.delete, h.url))
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (HistoryDAO.delete(getActivity(), h.getId()) > 0) {
                                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                        }
                    })
                    .create()
                    .show();
            return true;
        }
        return false;
    }
}
