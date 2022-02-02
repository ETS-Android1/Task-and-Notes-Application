package com.example.newapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapplication.Models.AddTaskModel;
import com.example.newapplication.HomeTaskDetailActivity;
import com.example.newapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Displaying Task In Task Fragment
 */

public class AdapterTaskRecycler extends RecyclerView.Adapter<AdapterTaskRecycler.MyViewHolder2> {

    List<AddTaskModel> lt = new ArrayList<>();
    Context ct;

    public AdapterTaskRecycler(Context ct){
        this.ct = ct;
    }

    public void setItems(List<AddTaskModel> list){
        lt.clear();
        lt.addAll(list);
    }

    @NonNull
    @Override
    public AdapterTaskRecycler.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.task_row,parent,false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTaskRecycler.MyViewHolder2 holder, int position) {
        holder.taskName.setText(lt.get(position).getTaskName());
        holder.taskType.setText(lt.get(position).getTaskType());
        holder.taskUser.setText(lt.get(position).getUserName());
        String tempPriority = lt.get(position).getPriority();
        String temp = (tempPriority.equals("High") ? "H" :(tempPriority.equals("Medium") ? "M" : (tempPriority.equals("Low") ? "L" : "P")));
        holder.taskPriority.setBackgroundResource(temp.equals("H")? R.drawable.circle_high : (temp.equals("M")? R.drawable.circle_medium : (temp.equals("L")? R.drawable.circle_low: R.drawable.circle_high)));
        holder.taskPriority.setText(temp);
        holder.taskCreatedDate.setText(lt.get(position).getCreatedDate());
        holder.taskDueDate.setText(lt.get(position).getDueDate());
        String tempStatus = lt.get(position).getTaskStatus();
        holder.taskStatus.setBackgroundResource(tempStatus.equals("New") ? R.drawable.rect_new : (tempStatus.equals("Read") ? R.drawable.rect_read : (tempStatus.equals("Executed") ? R.drawable.rect_executed : (tempStatus.equals("In Progress") ? R.drawable.rect_in_progress : (tempStatus.equals("Cancelled") ? R.drawable.rect_cancelled : (tempStatus.equals("Closed") ? R.drawable.rect_closed : R.drawable.rect_new))))));
        holder.taskStatus.setText(tempStatus);
        String count = (!lt.get(position).getTaskImage1().equals("null") ? (!lt.get(position).getTaskImage2().equals("null") ? (!lt.get(position).getTaskImage3().equals("null") ? (!lt.get(position).getTaskImage4().equals("null")? "4" : "3" ) : "2" ) : "1") : "0" );
        holder.taskAttachCount.setText(count);
        holder.taskTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ct.startActivity(new Intent(ct, HomeTaskDetailActivity.class).putExtra("value", lt.get(holder.getAdapterPosition()).getKey()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lt.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView taskType;
        private TextView taskUser;
        private TextView taskPriority;
        private TextView taskCreatedDate;
        private TextView taskDueDate;
        private TextView taskStatus;
        private TextView taskAttachCount;
        private ConstraintLayout taskTouch;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.rowTaskName);
            taskType = (TextView) itemView.findViewById(R.id.rowTaskType);
            taskUser = (TextView) itemView.findViewById(R.id.rowTaskUser);
            taskPriority = (TextView) itemView.findViewById(R.id.rowTaskPriority);
            taskCreatedDate = (TextView) itemView.findViewById(R.id.rowTaskPDate);
            taskDueDate = (TextView) itemView.findViewById(R.id.rowTaskDueDate);
            taskStatus = (TextView) itemView.findViewById(R.id.rowTaskStatus);
            taskAttachCount =(TextView) itemView.findViewById(R.id.rowTaskAttachCount);
            taskTouch = (ConstraintLayout) itemView.findViewById(R.id.taskTouch);
        }
    }

}
