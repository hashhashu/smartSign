package com.example.signclass.ui.person;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.signclass.R;

public class PersonFragment extends Fragment {

    private PersonViewModel mViewModel;
    private Button btn_teacher;//1
    private Button btn_help;//1.1
    private Button btn_exit;//1

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_person, container, false);


        btn_teacher = root.findViewById(R.id.btn_teacher);//2
        btn_help = root.findViewById(R.id.btn_help);//2.1
        btn_exit = root.findViewById(R.id.btn_exit);//2.1


        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        // TODO: Use the ViewModel
    }

}
