package com.example.signclass.ui.sign;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.signclass.MainActivity;
import com.example.signclass.R;
import com.example.signclass.SignActivity;
import com.example.signclass.adapter.SignedRecordAdapter;
import com.example.signclass.bean.SignedRecord;

import java.util.ArrayList;
import java.util.List;

public class SignFragment extends Fragment {

    private SignViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private Button btn_sign;

    public static SignFragment newInstance() {
        return new SignFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView_sign);
        btn_sign = root.findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignActivity.class);
                startActivity(intent);
            }
        });

        List<SignedRecord> recordList = new ArrayList<>();
        SignedRecord record1 = new SignedRecord("2020/05/02 08:30","C教学楼");
        SignedRecord record2 = new SignedRecord("2020/05/09 08:30","C教学楼");
        recordList.add(record1);
        recordList.add(record2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        SignedRecordAdapter myAdapter = new SignedRecordAdapter(recordList);
        mRecyclerView.setAdapter(myAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignViewModel.class);
        // TODO: Use the ViewModel
    }



}
