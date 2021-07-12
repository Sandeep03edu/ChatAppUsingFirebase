package com.example.chat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chat.Adapter.UserAdapter;
import com.example.chat.Model.Chat;
import com.example.chat.Model.Chatlist;
import com.example.chat.Model.User;
import com.example.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
//    private List<String> usersList; // Changing to list of chatList
    private List<Chatlist> usersList;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                for(DataSnapshot data: snapshot.getChildren()){
                    Chatlist chatlist = data.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }
                readChatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Removing below code
    /**
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                        Chat chat = data.getValue(Chat.class);
                        assert chat != null;
                        if (chat.getSender().equals(firebaseUser.getUid())) {
                            usersList.add(chat.getReciever());
                        }
                        if (chat.getReciever().equals(firebaseUser.getUid())) {
                            usersList.add(chat.getSender());
                        }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

     **/
        return view;
    }

    private void readChatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    User user = data.getValue(User.class);
                    for(Chatlist chatlist: usersList){
                        if(user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                UserAdapter userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    // Deleting below readChats() function

/**
    private void readChats() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    // Display user 1 from chats list
//                    for (String id : usersList) {
                    for (int i = 0; i < usersList.size(); i++) { //
                        String id = usersList.get(i); //
                        assert user != null;
                        if (user.getId().equals(id)) {
                            if (mUsers.size() != 0) {
//                                    for (User user1 : mUsers) {
                                for (int j = 0; j < mUsers.size(); j++) {//
                                    User user1 = mUsers.get(j); //
                                    if (!user.getId().equals(user1.getId()) &&  checkExist(user)) {
                                        mUsers.add(user);
                                    }
                                }
                            } else {
                                mUsers.add(user);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkExist(User user){
        for(User user1: mUsers){
            if(user1.equals(user)){
                return false;
            }
        }
        return true;
    }
 **/

}