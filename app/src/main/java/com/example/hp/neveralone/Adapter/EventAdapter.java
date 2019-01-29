package com.example.hp.neveralone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.neveralone.Model.Event;
import com.example.hp.neveralone.Model.User;
import com.example.hp.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Queue;

import okhttp3.internal.cache.DiskLruCache;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {


    private DatabaseReference reference;
    private Context mContext;
    private List<Event>mEvents;
    private ViewGroup parent;
    private int viewType;

    public EventAdapter(Context mContext,List<Event> mEvents){

        this.mEvents = mEvents;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_item,viewGroup , false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder Holder, int i) {

        final Event event = mEvents.get(i);

            Holder.event_name.setText(event.getEventName());
            Holder.event_image.setImageResource(R.mipmap.ic_launcher);
            Holder.event_desc.setText(event.getDescription());
            Holder.event_date.setText(event.getDay()+"/"+event.getMonth()+"/"+event.getYear());
            final String uid = event.getOrganiser();
        final String[] u = new String[1];
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                            User user = datasnapshot.getValue(User.class);
                            if (user.getId()!=null && user.getId().equals(uid)) {
                                    u[0] = user.getUsername();
                                    Holder.event_user.setText(u[0]);
                            }
                        }


               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            Holder.event_user.setText(u[0]);

    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public TextView event_name,event_desc,event_date,event_user;
        public ImageView event_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_image = itemView.findViewById(R.id.event_image);
            event_desc= itemView.findViewById(R.id.event_desc);
            event_date = itemView.findViewById(R.id.event_date);
            event_user = itemView.findViewById(R.id.user_event);


        }
    }
}
