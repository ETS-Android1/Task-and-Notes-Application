package com.example.newapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.newapplication.HomeActivity;
import com.example.newapplication.R;

/**
 * Home Fragment which has Buttons for navigating either to taskFragment or notesFragment
 */

public class HomeFragment extends Fragment {

    private Button taskButton;
    private Button notesButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle("Home");

        taskButton = (Button) rootView.findViewById(R.id.buttonTasksHome);
        notesButton = (Button) rootView.findViewById(R.id.buttonNotesHome);

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.homefFrameLayout,new HomeTaskFragment()).addToBackStack(null).commit();
            }
        });

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.homefFrameLayout,new HomeNotesFragment()).addToBackStack(null).commit();
            }
        });

        return rootView;
    }
}