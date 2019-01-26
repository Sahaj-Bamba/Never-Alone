package com.example.hp.neveralone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hp.neveralone.Adapter.EventAdapter;
import com.example.hp.neveralone.Model.Event;
import com.example.hp.neveralone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class EventFragment extends Fragment {

    private RecyclerView recyclerView,recyclerUser;

    private EventAdapter eventAdapter;
    private List<Event> mEvents;

    EditText search_events;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);


        recyclerView = view.findViewById(R.id.recycler_view_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerUser = view.findViewById(R.id.recycler_view_events);
        mEvents = new ArrayList<>();

        readEvents();

        search_events = view.findViewById(R.id.search_events);
        search_events.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchUsers(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void searchUsers(String s) {

        if(s!="") {
            final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
            Query query = FirebaseDatabase.getInstance().getReference("Events").orderByChild("search")
                    .startAt(s)
                    .endAt(s + "\uf8ff");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (search_events.getText().toString().equals("")) {
                        mEvents.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Event event = snapshot.getValue(Event.class);

                            assert event != null;
                            if (!event.getOrganiser().equals(fuser.getUid())){
                                mEvents.add(event);
                            }

                        }
                        eventAdapter = new EventAdapter(getContext(), mEvents);
                        recyclerView.setAdapter(eventAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            readEvents();
        }
    }

    private void readEvents(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mEvents.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Event event = snapshot.getValue(Event.class);

                    assert event!=null;
                    assert firebaseUser!= null;
                    if(event !=null && event.getOrganiser()==firebaseUser.getUid())
                        mEvents.add(event);
                    }
                eventAdapter = new EventAdapter(getContext(),mEvents);
                recyclerView.setAdapter(eventAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
