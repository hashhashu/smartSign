package com.example.signclass.ui.course;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.signclass.R;

public class CourseFragment extends Fragment {

    private CourseViewModel mViewModel;
    private RecyclerView mRecyclerView;

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_course, container, false);

//        mRecyclerView = root.findViewById(R.id.recyclerView_course);
//
//        List<SignedRecord> recordList = new ArrayList<>();
//        SignedRecord record1 = new SignedRecord("2020/05/02 08:30","C教学楼");
//        SignedRecord record2 = new SignedRecord("2020/05/09 08:30","C教学楼");
//        recordList.add(record1);
//        recordList.add(record2);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
//        mRecyclerView.setLayoutManager(layoutManager);
//        SignedRecordAdapter myAdapter = new SignedRecordAdapter(recordList);
//        mRecyclerView.setAdapter(myAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        // TODO: Use the ViewModel
    }

}
