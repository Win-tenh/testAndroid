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
import com.example.testdb.model.ItemContact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private List<ItemContact> contactList;

    public ContactAdapter (Context context) {
        this.context = context;
    }
    public void setContactList(List<ItemContact> contactList) {
        this.contactList = contactList;
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
        ItemContact contact = contactList.get(position);
        if (contact == null) return;
        holder.img.setImageResource(contact.getImg());
        holder.name.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        if (contactList != null) {
            return contactList.size();
        }
        return 0;
    }
}
