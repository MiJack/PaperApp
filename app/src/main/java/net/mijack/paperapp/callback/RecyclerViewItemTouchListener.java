package net.mijack.paperapp.callback;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by MiJack on 2016/4/25.
 */
public class RecyclerViewItemTouchListener implements RecyclerView.OnItemTouchListener {
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void onFling(View view, int position);
    }

    public static  class SimpleItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }

        @Override
        public void onFling(View view, int position) {

        }
    }

    private RecyclerView recyclerView;
    private OnItemClickListener mListener;
    private GestureDetector mGestureListener;

    public RecyclerViewItemTouchListener(final RecyclerView recyclerView, OnItemClickListener listener) {
        this.recyclerView = recyclerView;
        mListener = listener;

        mGestureListener = new GestureDetector(recyclerView.getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.d("Gesture", "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d("Gesture", "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d("Gesture", "onSingleTapUp");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d("Gesture", "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d("Gesture", "onLongPress");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d("Gesture", "onFling");
                View childView = recyclerView.findChildViewUnder(e1.getX(), e1.getY());
                if (childView != null && mListener != null) {
                    mListener.onFling(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return false;
            }
        });
    }

    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        mGestureListener.onTouchEvent(e);
        return false;
    }

    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
