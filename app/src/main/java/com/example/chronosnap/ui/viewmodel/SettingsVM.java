package com.example.chronosnap.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.data.repository.UserRepository;
import com.example.chronosnap.domain.entities.NotificationParameters;
import com.example.chronosnap.domain.entities.User;
import com.example.chronosnap.domain.usecases.UpdateUserUseCase;
import com.example.chronosnap.ui.view.activities.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsVM extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<NotificationParameters> settings = new MutableLiveData<>();

    public SettingsVM() {
        loadUser();
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User updatedUser, Context context) {
        user.setValue(updatedUser);
        updateUser(context);
    }

    public void updateUser(Context context) {
        User updatedUser = user.getValue();
        if (updatedUser == null) return;

        UpdateUserUseCase.execute(updatedUser, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Настройки обновлены", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadUser() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            user.setValue(User.fromFirebaseUser(fbUser));
        }
    }

    public void logOut(Context context) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(context, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public LiveData<NotificationParameters> getSettings() {
        return settings;
    }

    public void loadSettingsFromPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        NotificationParameters ns = new NotificationParameters();

        ns.setStartHour((byte) prefs.getInt("startHour", 9));
        ns.setStartMinute((byte) prefs.getInt("startMinute", 0));
        ns.setFinishHour((byte) prefs.getInt("finishHour", 21));
        ns.setFinishMinute((byte) prefs.getInt("finishMinute", 0));
        ns.setInterval(prefs.getInt("interval", 30));
        ns.setEnabled(prefs.getBoolean("enabled", true));

        settings.setValue(ns);
    }

    public void updateSettingsPreferences(Context context, NotificationParameters ns) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        prefs.edit().putInt("startHour", 9).apply();
        prefs.edit().putInt("startMinute", 0).apply();
        prefs.edit().putInt("finishHour", 21).apply();
        prefs.edit().putInt("finishMinute", 0).apply();
        prefs.edit().putInt("interval", 30).apply();

        settings.setValue(ns);
    }

    public void enableNotification(Context context, boolean b) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        prefs.edit().putBoolean("enabled", b);
        updateNotification(b);
    }

    private void updateNotification(boolean b) {
        NotificationParameters np = settings.getValue();
        np.setEnabled(b);
        settings.setValue(np);
    }

    public List<Map.Entry<String, Integer>> getAllCategoriesList() {
        Map<String, Integer> categories = new TreeMap<>();
        categories.put("ExampleCategory1", 0);
        categories.put("ExampleCategory2", 0);
        //TODO получение всех категорий / use case

        List<Map.Entry<String, Integer>> categoriesList = new ArrayList<>(categories.entrySet());
        return categoriesList;
    }

    public void updateAllCategories(Map<String, Integer> map) {
        //TODO обновление списка категорий /use case
    }
}
