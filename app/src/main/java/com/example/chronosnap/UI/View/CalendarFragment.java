package com.example.chronosnap.UI.View;

import static com.example.chronosnap.Utils.CalendarUtils.daysInWeekArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chronosnap.Domain.Entities.ActivityEntry;
import com.example.chronosnap.R;
import com.example.chronosnap.UI.View.Adapters.CalendarAdapter;
import com.example.chronosnap.Utils.CalendarUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ImageButton back;
    private ImageButton forward;
    private LinearLayout timeColumn;
    private FrameLayout activityColumn;

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        monthYearText = view.findViewById(R.id.month_year_text);
        calendarRecyclerView = view.findViewById(R.id.calendar_view);
        back = view.findViewById(R.id.calendar_back_arrow);
        forward = view.findViewById(R.id.calendar_forward_arrow);
        timeColumn = view.findViewById(R.id.time_column);
        activityColumn = view.findViewById(R.id.activity_column);

        back.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        });

        forward.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();
        });

        if (CalendarUtils.selectedDate == null) {
            CalendarUtils.selectedDate = LocalDate.now();
        }
        setWeekView();

        fillTimeColumn(getContext(), timeColumn);
        fillActivityColumn(getContext(), activityColumn);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setWeekView() {
        if (monthYearText == null || calendarRecyclerView == null || CalendarUtils.selectedDate == null) {
            return;
        }

        monthYearText.setText(CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = date;
            setWeekView();
        }
    }

    private void fillTimeColumn(Context context, LinearLayout layout){
        String[] timeArray = getResources().getStringArray(R.array.time);
        float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i<24; i++){
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    Math.round(density*60)
            );
            textView.setLayoutParams(params);
            textView.setText(timeArray[i]);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
            layout.addView(textView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fillActivityColumn(Context context, FrameLayout layout){
        //TODO метод получения списка активностей
        List<ActivityEntry> activityEntries = null;
        float density = getResources().getDisplayMetrics().density;
        long currentDate = 0;
        if (activityEntries!=null){
            LocalDate date = LocalDate.parse((CharSequence) activityEntries.get(0),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            currentDate = date.atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
            for (int i = 0; i<activityEntries.size(); i++){
                View view = new View(context);
                long height = Math.round(activityEntries.get(i).getLongitude()/60000f*density);
                long topMargin = Math.round((activityEntries.get(i).getStartTime()-currentDate)/60000f*density);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        (int) height
                );
                params.setMargins(0, (int) topMargin, 0, 0);
                view.setLayoutParams(params);
                layout.addView(view);
            }
        }

    }

}