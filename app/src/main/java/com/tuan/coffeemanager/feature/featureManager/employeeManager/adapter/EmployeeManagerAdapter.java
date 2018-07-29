package com.tuan.coffeemanager.feature.featureManager.employeeManager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeManagerAdapter extends RecyclerView.Adapter<EmployeeManagerAdapter.EmployeeManagerViewHolder> {

    private Context context;
    private List<User> userList;
    private OnItemClickListener onItemClickListener;

    public EmployeeManagerAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EmployeeManagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, viewGroup, false);
        return new EmployeeManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeManagerViewHolder employeeManagerViewHolder, final int i) {
        User user = userList.get(i);
        employeeManagerViewHolder.tvNameUser.setText(context.getString(R.string.text_employee_name_example, user.getName()));
        employeeManagerViewHolder.tvPhone.setText(context.getString(R.string.text_phone_number, user.getPhone_number()));
        employeeManagerViewHolder.clEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    static class EmployeeManagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNameUser)
        TextView tvNameUser;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.clEmployee)
        ConstraintLayout clEmployee;

        EmployeeManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
