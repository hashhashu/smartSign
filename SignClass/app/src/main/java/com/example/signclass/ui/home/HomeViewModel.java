package com.example.signclass.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mCno;
    private MutableLiveData<String> mCname;
    private MutableLiveData<String> mCteacher;
    private MutableLiveData<Integer> mCtime;
    private MutableLiveData<Integer> mCstart;
    private MutableLiveData<Integer> mCend;
    //private MutableLiveData<String> mCsigntime;
    //private MutableLiveData<Integer> mCnth;


    public HomeViewModel() {
        mCno = new MutableLiveData<>();
        mCname= new MutableLiveData<>();
        mCteacher = new MutableLiveData<>();
        mCtime = new MutableLiveData<>();
        mCstart = new MutableLiveData<>();
        mCend = new MutableLiveData<>();
        //mCsigntime = new MutableLiveData<>();
        //mCnth = new MutableLiveData<>();

        //undo:设置数据取数
        //mText.setValue("This is home fragment");
    }

    public LiveData<String> getCnoText() {
        return mCno;
    }

    public LiveData<String> getCnameText() {
        return mCname;
    }

    public LiveData<String> getCteacherText() {
        return mCteacher;
    }

    public LiveData<Integer> getCtimeText() {
        return mCtime;
    }

    public LiveData<Integer> getCstartText() {
        return mCstart;
    }

    public LiveData<Integer> getCendText() {
        return mCend;
    }

//    public LiveData<String> getText() {
//        return mCsigntime;
//    }
//
//    public LiveData<Integer> getText() {
//        return mCnth;
//    }
}