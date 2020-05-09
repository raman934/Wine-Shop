package com.example.android.wineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TestActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;
    String currentuser;
    static Boolean valid;
    Query query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = (ImageView) findViewById(android.R.id.icon);
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        query= FirebaseDatabase.getInstance().getReference("users").orderByChild("userId").equalTo(currentuser);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = getPath(data.getData());
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        // cursor.close();
        // Convert file path into bitmap image using below line.
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        return bitmap;
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.child(currentuser).child("valid").getValue(Boolean.class) == null)
                valid = false;
            else valid = true;

            if(valid)
                startActivity(new Intent(TestActivity.this, MainActivity.class));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}