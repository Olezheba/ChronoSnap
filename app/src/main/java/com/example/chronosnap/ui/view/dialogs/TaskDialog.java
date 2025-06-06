package com.example.chronosnap.ui.view.dialogs;

import androidx.fragment.app.DialogFragment;

public class TaskDialog extends DialogFragment {
    private SaveListener listener;


    public interface SaveListener{
        void onSave();
        void Delete();
    }
    public void setSaveListener(SaveListener listener){
        this.listener = listener;
    }

}
