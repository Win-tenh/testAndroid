package com.example.testdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdb.R;
import com.example.testdb.model.Employee;
import com.example.testdb.model.Unit;
import com.example.testdb.my_interface.IClickItemListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private boolean checkList;
    private List<Unit> unitList;
    private List<Employee> employeeList;
    private IClickItemListener clickUnitListener;

    public ContactAdapter (IClickItemListener clickUnitListener) {
        this.clickUnitListener = clickUnitListener;
        this.checkList = true;
    }
    public void setContactList(List<Unit> unitList, List<Employee> employeeList) {
        this.unitList = unitList;
        this.employeeList = employeeList;
        this.checkList = this.unitList != null ? true : false;
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_item;
        private CircleImageView img;
        private TextView name;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
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
        String getId = "";
        if (checkList) {
            Unit unit = unitList.get(position);
            holder.name.setText(unit.getName());
            getId = unit.getId();
            if (!unit.getLogo().isEmpty())
                Picasso.get().load(unit.getLogo()).into(holder.img);
            else
                holder.img.setImageResource(R.drawable.avatar1);
        }
        else {
            Employee employee = employeeList.get(position);
            holder.name.setText(employee.getName());
            getId = employee.getId();
            if (!employee.getAvatar().isEmpty())
                Picasso.get().load(employee.getAvatar()).into(holder.img);
            else
                holder.img.setImageResource(R.drawable.avatar1);
        }
        final String id = getId;
        holder.layout_item.setOnClickListener( v -> {
            if (checkList) {
                clickUnitListener.onClickUnit(id);
            } else {
                clickUnitListener.onClickEmployee(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (checkList) { return unitList.size(); }
        else { return employeeList.size(); }
    }
}