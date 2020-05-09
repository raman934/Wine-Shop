package com.example.android.wineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RequestActivity extends AppCompatActivity {
    private EditText editTextAmount;
    private Spinner spinnerType;
    private Spinner spinnerType1;
    private Spinner spinnerType2;
    private Spinner spinnerType3;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("requests");
    private Requests requests;
    private String requestId;
    private String requestOwner;
    private FirebaseAuth firebaseAuth;
    private Button btnSubmit, btnSub;
    String t[], t1[], t2[], t3[];
    Query query;
    TextView txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        firebaseAuth = FirebaseAuth.getInstance();
        requestId = getIntent().getStringExtra(RequestAdapter.REQUEST_ID);
        requestOwner = getIntent().getStringExtra(RequestAdapter.REQUEST_OWNER);
        t = getResources().getStringArray(R.array.type);
        t1 = getResources().getStringArray(R.array.type);
        t2 = getResources().getStringArray(R.array.type);
        t3 = getResources().getStringArray(R.array.type);
        editTextAmount = findViewById(R.id.editText_donation_amount);
        spinnerType = findViewById(R.id.spinner_type);
        spinnerType1 = findViewById(R.id.spinner_type1);
        spinnerType2 = findViewById(R.id.spinner_type2);
        spinnerType3 = findViewById(R.id.spinner_type3);
        btnSubmit = findViewById(R.id.button_submit);
        btnSub= findViewById(R.id.button_submit1);
        txt = (TextView) findViewById(R.id.textView5);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = spinnerType.getSelectedItem().toString();
                String type1 = spinnerType1.getSelectedItem().toString();
                String type2 = spinnerType2.getSelectedItem().toString();
                String type3 = spinnerType3.getSelectedItem().toString();
                String amount = editTextAmount.getText().toString().trim();
                String display, userId;
                String drink= "hey";

                if (TextUtils.isEmpty(amount)) {
                    display = "Please fill in the Amount";
                } else {
                    if (requestId != null) {
                        userId = requests.getUserId();
                        display = "Successfully updated ";
                    } else {
                        userId = firebaseAuth.getUid();
                        display = "Successfully created ";
                        requestId = databaseReference.push().getKey();
                    }
                    Requests request = new Requests(userId, drink, type, type1, type2, type3, amount);
                    databaseReference.child(requestId).setValue(request);
                }

                new CountDownTimer(86400000 , 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        databaseReference.child(requestId).removeValue();
                    }
                }.start();

                Toast.makeText(RequestActivity.this, display, Toast.LENGTH_SHORT).show();
                }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSub.setVisibility(View.INVISIBLE);
                txt.setVisibility(View.VISIBLE);
                if(spinnerType.getSelectedItem().toString().equals("wine"))
                    spinnerType1.setVisibility(View.VISIBLE);
                else if(spinnerType.getSelectedItem().toString().equals("beer"))
                    spinnerType2.setVisibility(View.VISIBLE);
                else spinnerType3.setVisibility(View.VISIBLE);

                btnSubmit.setVisibility(View.VISIBLE);


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (requestId != null) {
            databaseReference.child(requestId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    requests = dataSnapshot.getValue(Requests.class);
                    editTextAmount.setText(requests.getAmount());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

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
