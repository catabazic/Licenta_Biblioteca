package com.example.library.Server;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> data = new MutableLiveData<>();

    public LiveData<String> getData() {
        return data;
    }

    public void setData(String updatedData) {
        data.setValue(updatedData);
    }
}
