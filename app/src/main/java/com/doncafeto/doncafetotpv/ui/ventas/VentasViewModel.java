package com.doncafeto.doncafetotpv.ui.ventas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VentasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VentasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}