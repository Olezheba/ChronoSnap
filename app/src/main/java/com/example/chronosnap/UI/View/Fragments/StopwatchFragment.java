package com.example.chronosnap.UI.View.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chronosnap.Domain.Entities.ActivityEntry;
import com.example.chronosnap.R;
import com.example.chronosnap.UI.ViewModel.StopwatchVM;
import com.example.chronosnap.databinding.FragmentStatisticsBinding;
import com.example.chronosnap.databinding.FragmentStopwatchBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class StopwatchFragment extends Fragment {
    FragmentStopwatchBinding binding;

    Handler handler;
    boolean isRunning = false;
    long millSec, start = 0L;
    int sec,min,hour;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false);
        handler = new Handler();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.stopwatchBtn.setOnClickListener(v -> {
            if (!isRunning){
                start = System.currentTimeMillis();
                handler.postDelayed(runnable, 0);
                binding.chronometer.start();
                isRunning = true;

                binding.onStopwatchText.animate()
                        .alpha(0f)
                        .setDuration(400)
                        .withEndAction(() -> binding.onStopwatchText.setVisibility(View.GONE))
                        .start();
            }else{
                if (min<1 && hour<1) {
                    Toast.makeText(getContext(), "Запись должна быть не менее минуты", Toast.LENGTH_SHORT).show();
                }else{
                    //TODO диалоговое окно сохранения активности
                }

                binding.onStopwatchText.animate()
                        .alpha(1f)
                        .setDuration(400)
                        .withEndAction(() -> binding.onStopwatchText.setVisibility(View.VISIBLE))
                        .start();

                millSec = 0L;
                start = 0L;
                sec = 0;
                min = 0;
                hour = 0;
                binding.chronometer.setText("00:00:00");
            }
        });

        return binding.getRoot();
    }

    Runnable runnable = new Runnable() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void run() {
            millSec = System.currentTimeMillis() - start;
            sec = (int) (millSec/1000);
            min = sec/60;
            sec%=60;
            hour = min/60;
            min%=60;
            binding.chronometer.setText(String.format("%02d", hour)+":"
            +String.format("%02d", min)+":"+String.format("%02d", sec));
            handler.postDelayed(this, 60000);
        }
    };

}