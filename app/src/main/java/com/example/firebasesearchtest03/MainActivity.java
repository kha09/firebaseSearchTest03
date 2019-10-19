package com.example.firebasesearchtest03;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;


public class MainActivity extends AppCompatActivity {
   private DatabaseReference ref;
   private ArrayList<Users> list;
   private RecyclerView recyclerView;
   private SearchView searchView;
   private AdapterClass adapterClass;
   private FirebaseFirestore mFirestore;
   private static final String TAG = "FireLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = findViewById(R.id.rvSearch);
        searchView = findViewById(R.id.searchView);

        list = new ArrayList<>();
        adapterClass = new AdapterClass(list);
        recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterClass);
        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error :" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Users users = doc.getDocument().toObject(Users.class);
                        list.add(users);

                        adapterClass.notifyDataSetChanged();
                    }
                }
            }
        });



//    @Override
//    protected void onStart(){
//        super.onStart();
//        if(ref != null){
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists()){
//                        list = new ArrayList<>();
//                        for(DataSnapshot ds : dataSnapshot.getChildren()){
//                            list.add(ds.getValue(Users.class));
//                        }
//                        AdapterClass adapterClass = new AdapterClass(list);
//                        recyclerView.setAdapter(adapterClass);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }
//
    private void search(String str) {
        ArrayList<Users> myList = new ArrayList<>();
        for(Users object : list){
            if(object.getName().toLowerCase().contains(str.toLowerCase())){
                myList.add(object);
            }
        }
        AdapterClass adapterClass = new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);
    }
}
