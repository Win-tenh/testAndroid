package com.example.testdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.R;
import com.example.testdb.model.Employee;
import com.example.testdb.model.ItemContact;
import com.example.testdb.model.Unit;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private List<Unit> unitList;
    private List<Employee> employeeList;

    public ContactAdapter (Context context) {
        this.context = context;
    }
    public void setContactList(List<Unit> unitList, List<Employee> employeeList) {
        this.unitList = unitList;
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        if (unitList != null) {
            Unit unit = unitList.get(position);
            holder.img.setImageResource(R.drawable.avatar1);
            holder.name.setText(unit.getName());
        }
        else if (employeeList != null) {
            Employee employee = employeeList.get(position);
            holder.img.setImageResource(R.drawable.avatar1);
            holder.name.setText(employee.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (unitList != null) { return unitList.size(); }
        if (employeeList != null) { return employeeList.size(); }
        return 0;
    }
}