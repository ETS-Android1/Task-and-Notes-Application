package com.example.newapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.newapplication.Adapter.AdapterNotesRecycler;
import com.example.newapplication.AddNotesActivity;
import com.example.newapplication.DataAccessObject.AddNotesDao;
import com.example.newapplication.DataAccessObject.AddTaskDAO;
import com.example.newapplication.Models.AddNotesModel;
import com.example.newapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Notes Fragment
 */

public class HomeNotesFragment extends Fragment {

    private Button addNotes;
    private RecyclerView notesRecycler;
    private ProgressBar notesLoading;

    AddNotesDao dao;

    AdapterNotesRecycler adapterNotesRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_notes, container, false);
        getActivity().setTitle("Notes");

        addNotes = (Button) rootView.findViewById(R.id.buttonAddNotes);
        notesRecycler = (RecyclerView) rootView.findViewById(R.id.recyclerNotes);
        notesLoading = (ProgressBar) rootView.findViewById(R.id.progressBarNotesLoading);
        notesLoading.setVisibility(View.VISIBLE);

        adapterNotesRecycler = new AdapterNotesRecycler(getContext());
        notesRecycler.setAdapter(adapterNotesRecycler);
        notesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddNotesActivity.class));
            }
        });

        return rootView;
    }

    //To load data from firebase
    private void loadData(){
        dao = new AddNotesDao();
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AddNotesModel> lt = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){
                    AddNotesModel ad = data.getValue(AddNotesModel.class);
                    ad.setKey(data.getKey());
                    lt.add(ad);
                }
                notesLoading.setVisibility(View.GONE);
                adapterNotesRecycler.setItems(lt);
                adapterNotesRecycler.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("onCancelled", String.valueOf(error));
            }
        });
    }


}