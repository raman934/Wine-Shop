package com.example.android.wineshop;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("requests");

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.list_request);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this, new LinearLayoutManager(MainActivity.this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        progressBar = findViewById(R.id.progressBar_list);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }


    public void getData() {
        Query finalReference;
        finalReference = databaseReference.orderByChild("userId");

        finalReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Item> names = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Requests requests = snapshot.getValue(Requests.class);
                    Item item = new Item();
                    item.setAmount(requests.getAmount());
                    item.setId(snapshot.getKey());
                    item.setUserId(requests.getUserId());
                    names.add(item);
                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new RequestAdapter(MainActivity.this, names));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database Error", databaseError.getMessage());

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        return super.onOptionsItemSelected(item);
    }
}