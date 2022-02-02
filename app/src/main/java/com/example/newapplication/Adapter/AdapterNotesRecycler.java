package com.example.newapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapplication.AddNotesActivity;
import com.example.newapplication.DataAccessObject.AddNotesDao;
import com.example.newapplication.Models.AddNotesModel;
import com.example.newapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Displaying Notes in Notes Fragment
 */

public class AdapterNotesRecycler extends RecyclerView.Adapter<AdapterNotesRecycler.MyViewHolder3> {

    List<AddNotesModel> lt = new ArrayList<>();
    Context ct;

    public AdapterNotesRecycler(Context ct){
        this.ct = ct;
    }

    public void setItems(List<AddNotesModel> list){
        lt.clear();
        lt.addAll(list);
    }

    @NonNull
    @Override
    public AdapterNotesRecycler.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notes_row,parent,false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotesRecycler.MyViewHolder3 holder, int position) {
        holder.notesTittle.setText(lt.get(position).getNotesTittle());
        holder.notesStore.setText(lt.get(position).getNotesStore());
        holder.notesUser.setText(lt.get(position).getNotesUser());
        holder.notesDate.setText(lt.get(position).getNotesDate());
        holder.notesOnClickDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(ct)
                        .setTitle("Description")
                        .setMessage(lt.get(holder.getAdapterPosition()).getNotesDescription())
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        holder.notesMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ct,holder.notesMenu);
                popupMenu.inflate(R.menu.notes_options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.notesEdit:
                                ct.startActivity(new Intent(ct, AddNotesActivity.class).putExtra("value",lt.get(holder.getAdapterPosition()).getKey()));
                                return true;

                            case R.id.notesDelete:
                                AddNotesDao dao = new AddNotesDao();
                                dao.remove(lt.get(holder.getAdapterPosition()).getKey());
                                notifyItemRemoved(holder.getAdapterPosition());
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lt.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{
        private TextView notesTittle;
        private TextView notesStore;
        private TextView notesUser;
        private TextView notesDate;
        private TextView notesOnClickDescription;
        private TextView notesMenu;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            notesTittle = (TextView) itemView.findViewById(R.id.textViewNotesTittle);
            notesStore = (TextView) itemView.findViewById(R.id.textViewNotesStore);
            notesUser = (TextView) itemView.findViewById(R.id.textViewNotesUser);
            notesDate = (TextView) itemView.findViewById(R.id.textViewNotesDate);
            notesOnClickDescription = (TextView) itemView.findViewById(R.id.textViewNotesDescription);
            notesMenu = (TextView) itemView.findViewById(R.id.textViewNotesOption);
        }
    }
}
