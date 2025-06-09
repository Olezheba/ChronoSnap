package com.example.chronosnap.ui.view.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chronosnap.ui.view.dialogs.SaveRecordDialogFragment;
import com.example.chronosnap.ui.viewmodel.StopwatchVM;
import com.example.chronosnap.databinding.FragmentStopwatchBinding;
import com.example.chronosnap.ui.viewmodelfactories.StopwatchVMFactory;

public class StopwatchFragment extends Fragment {
    FragmentStopwatchBinding binding;
    StopwatchVM vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vm = new ViewModelProvider(requireActivity(), new StopwatchVMFactory(requireContext())).get(StopwatchVM.class);
        binding = FragmentStopwatchBinding.inflate(inflater, container, false);

        if (vm.isRunning().getValue()) startStopwatch(vm.getStart());

        binding.chronometer.setText("00:00:00");

        binding.chronometer.setOnChronometerTickListener(chronometer -> {
            long currentTime = SystemClock.uptimeMillis() - chronometer.getBase();

            int seconds = (int) (currentTime / 1000) % 60;
            int minutes = (int) ((currentTime / (1000 * 60)) % 60);
            int hours = (int) (currentTime / (1000 * 60 * 60));

            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            binding.chronometer.setText(time);
        });

        binding.stopwatchBtn.setOnClickListener(v -> {
            if (!vm.isRunning().getValue()) {
                long baseTime = SystemClock.uptimeMillis();
                startStopwatch(baseTime);
            } else {
                long duration = SystemClock.uptimeMillis() - vm.getStart();
                if (duration < 60000) {
                    Toast.makeText(getContext(), "Запись должна быть не менее минуты", Toast.LENGTH_SHORT).show();
                } else {
                    SaveRecordDialogFragment dialog = new SaveRecordDialogFragment();
                    dialog.setSaveListener(new SaveRecordDialogFragment.SaveListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onSave(String selectedCategory, int priority) {
                            vm.saveActivityEntry(selectedCategory, priority, duration);
                        }
                        @Override
                        public void onDelete() {}
                    });
                    dialog.show(getChildFragmentManager(), "SaveDialog");
                }

                binding.chronometer.stop();
                binding.chronometer.setBase(SystemClock.elapsedRealtime());
                binding.chronometer.setText("00:00:00");

                vm.setRunning(false);
                vm.setStart(0L);

                binding.onStopwatchText.animate()
                        .alpha(1f)
                        .setDuration(400)
                        .withEndAction(() -> binding.onStopwatchText.setVisibility(View.VISIBLE))
                        .start();
            }
        });
        return binding.getRoot();
    }

    private void startStopwatch(long baseTime) {
        binding.chronometer.setBase(baseTime);
        binding.chronometer.start();

        vm.setStart(baseTime);
        vm.setRunning(true);

        binding.onStopwatchText.animate()
                .alpha(0f)
                .setDuration(400)
                .withEndAction(() -> binding.onStopwatchText.setVisibility(View.GONE))
                .start();
    }
}