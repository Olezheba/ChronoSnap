package com.example.chronosnap.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chronosnap.data.repository.UserRepository;
import com.example.chronosnap.domain.entities.NotificationParameters;
import com.example.chronosnap.domain.entities.User;
import com.example.chronosnap.domain.usecases.GetAllCategoriesUseCase;
import com.example.chronosnap.domain.usecases.UpdateAllCategoriesUseCase;
import com.example.chronosnap.domain.usecases.UpdateUserUseCase;
import com.example.chronosnap.ui.view.activities.AuthActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.TreeMap;

public class SettingsVM extends ViewModel {
    private final String uid = FirebaseAuth.getInstance().getUid();
    private final UserRepository repo = new UserRepository(uid);
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<TreeMap<String, Integer>> categories = repo.getCategoriesLiveData();
    private final MutableLiveData<NotificationParameters> settings = new MutableLiveData<>();

    public SettingsVM() {
        loadUser();
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser (User updatedUser , Context context) {
        user.setValue(updatedUser);
        updateUser(context);
    }

    public void updateUser (Context context) {
        User updatedUser = user.getValue();
        if (updatedUser == null) return;
        repo.updateUser(updatedUser , task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Настройки обновлены", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
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

        prefs.edit().putInt("startHour", ns.getStartHour()).apply();
        prefs.edit().putInt("startMinute", ns.getStartMinute()).apply();
        prefs.edit().putInt("finishHour", ns.getFinishHour()).apply();
        prefs.edit().putInt("finishMinute", ns.getFinishMinute()).apply();
        prefs.edit().putInt("interval", ns.getInterval()).apply();

        settings.setValue(ns);
    }

    public void enableNotification(Context context, boolean enabled) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        prefs.edit().putBoolean("enabled", enabled).apply();
        updateNotification(enabled);
    }

    private void updateNotification(boolean enabled) {
        NotificationParameters np = settings.getValue();
        if (np != null) {
            np.setEnabled(enabled);
            settings.setValue(np);
        }
    }

    public LiveData<TreeMap<String, Integer>> getCategories() {
        return categories;
    }


    public void onSave(TreeMap<String, Integer> updatedCategories) {
        repo.updateAllCategories(updatedCategories, task -> {
            if (!task.isSuccessful()) {
                Log.e("SettingsVM", "Ошибка обновления категорий");
            }
        });
    }
}