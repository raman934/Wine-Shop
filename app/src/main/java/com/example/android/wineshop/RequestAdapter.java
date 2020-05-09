package com.example.android.wineshop;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    public static final String REQUEST_ID = "R Id";
    public static final String REQUEST_OWNER = "R Owner";
    private final Context context;
    private final List<Item> items;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    public RequestAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestAdapter.ViewHolder holder, final int position) {
        databaseReference.child(items.get(position).getUserId()).child("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.location.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.Amount.setText(items.get(position).getAmount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RequestActivity.class);
                intent.putExtra(REQUEST_ID, items.get(position).getId());
                intent.putExtra(REQUEST_OWNER, items.get(position).getUserId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Amount;
        TextView location;

        public ViewHolder(View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.text_item_location);
            Amount = itemView.findViewById(R.id.textView_amount);
        }
    }
}