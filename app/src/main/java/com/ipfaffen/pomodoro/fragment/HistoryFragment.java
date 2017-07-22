package com.ipfaffen.pomodoro.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ipfaffen.pomodoro.R;
import com.ipfaffen.pomodoro.entity.DaoSession;
import com.ipfaffen.pomodoro.entity.Pomodoro;
import com.ipfaffen.pomodoro.entity.PomodoroDao;
import com.ipfaffen.pomodoro.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaias Pfaffenseller
 */
public class HistoryFragment extends FragmentBase {

    protected ListView listView;
    protected ArrayAdapter<Pomodoro> listAdapter;

    private PomodoroDao pomodoroDao;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoSession daoSession = app.getDaoSession();
        pomodoroDao = daoSession.getPomodoroDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        // Configure list view.
        listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setEmptyView(rootView.findViewById(R.id.empty_view));
        listView.setAdapter(listAdapter = new PomodoroAdapter(new ArrayList<Pomodoro>()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Do something...
                // Pomodoro pomodoro = listAdapter.getItem(position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Remove pomodoro...
                return true;
            }
        });

        return rootView;
    }
    
    public void list() {
        listAdapter.clear();
        listAdapter.addAll(pomodoroDao.queryBuilder().where(PomodoroDao.Properties.EndDate.isNotNull()).orderDesc(PomodoroDao.Properties.StartDate).list());
        listAdapter.notifyDataSetChanged();
    }

    public class PomodoroAdapter extends ArrayAdapter<Pomodoro> {

        private final List<Pomodoro> pomodoros;

        /**
         * @param pomodoros
         */
        public PomodoroAdapter(List<Pomodoro> pomodoros) {
            super(getActivity(), R.layout.list_item_pomodoro, pomodoros);
            this.pomodoros = pomodoros;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                view = vi.inflate(R.layout.list_item_pomodoro, null);
            }

            Pomodoro pomodoro = getItem(position);
            if(pomodoro == null) {
                return view;
            }

            TextView usedTimeText = (TextView) view.findViewById(R.id.used_time_text);
            usedTimeText.setText(Util.formatToHourMinute(pomodoro.getUsedTimeMillis()));

            TextView stateText = (TextView) view.findViewById(R.id.state_text);
            stateText.setText(getStringByName("pomodoro_state_".concat(pomodoro.getState().toLowerCase())));

            TextView endDateText = (TextView) view.findViewById(R.id.end_date_text);
            endDateText.setText(getPrettyTime().format(pomodoro.getEndDate()));

            return view;
        }
    }
}