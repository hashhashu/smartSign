package com.example.signclass.ui.sign;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mCno;
    private MutableLiveData<String> mCnt;
    private MutableLiveData<String> mCsigntime;
    private MutableLiveData<String> mCsigntimelast;
    private MutableLiveData<String> mCsignposi;
    private MutableLiveData<String> mCsigned;
    private MutableLiveData<String> mCnotsigned;

    public SignViewModel(){
        mCno = new MutableLiveData<>();
        mCnt = new MutableLiveData<>();
        mCsigntime = new MutableLiveData<>();
        mCsigntimelast = new MutableLiveData<>();
        mCsignposi = new MutableLiveData<>();
        mCsigned = new MutableLiveData<>();
        mCnotsigned = new MutableLiveData<>();
    }

    public LiveData<String> getCnoText() {
        return mCno;
    }
    public LiveData<String> getCntText() {
        return mCnt;
    }
    public LiveData<String> getCsigntimeText() {
        return mCsigntime;
    }
    public LiveData<String> getCsigntimelastText() {
        return mCsigntimelast;
    }
    public LiveData<String> getCsignposiText() {
        return mCsignposi;
    }
    public LiveData<String> getCsignedText() {
        return mCsigned;
    }
    public LiveData<String> getCnotsignedText() {
        return mCnotsigned;
    }

}
