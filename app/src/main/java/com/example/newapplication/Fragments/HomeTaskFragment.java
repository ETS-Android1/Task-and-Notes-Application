package com.example.newapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.newapplication.Adapter.AdapterTaskRecycler;
import com.example.newapplication.AddTaskActivity;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.example.newapplication.Models.AddTaskModel;
import com.example.newapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Task Fragment
 */

public class HomeTaskFragment extends Fragment {

    private Button addTask;
    private RecyclerView taskRecycler;
    private ProgressBar taskLoading;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    AddTaskDAO dao;

    AdapterTaskRecycler adapterTaskRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_task, container, false);
        getActivity().setTitle("Tasks");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        addTask = (Button) rootView.findViewById(R.id.buttonAddTask);
        taskRecycler = (RecyclerView) rootView.findViewById(R.id.recyclerTask);
        taskLoading = (ProgressBar) rootView.findViewById(R.id.progressBarTaskLoading);
        taskLoading.setVisibility(View.VISIBLE);

        adapterTaskRecycler = new AdapterTaskRecycler(getContext());
        taskRecycler.setAdapter(adapterTaskRecycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
            }
        });


        return rootView;
    }

    //To load data from firebase
    private void loadData() {
        dao = new AddTaskDAO();

        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AddTaskModel> lt = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()) {
                    AddTaskModel ad = data.getValue(AddTaskModel.class);
                    ad.setKey(data.getKey());
                    lt.add(ad);
                }
                taskLoading.setVisibility(View.GONE);
                Log.d("size", "onCreateView: "+ lt.size());
                adapterTaskRecycler.setItems(lt);
                adapterTaskRecycler.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("onCancelled", String.valueOf(error));
            }
        });

    }
}