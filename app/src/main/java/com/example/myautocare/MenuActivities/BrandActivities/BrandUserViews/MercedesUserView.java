package com.example.myautocare.MenuActivities.BrandActivities.BrandUserViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myautocare.Admin.AdminViews.Mercedes.ImageAdapterM;
import com.example.myautocare.Admin.AdminViews.Mercedes.UploadM;
import com.example.myautocare.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MercedesUserView extends AppCompatActivity  implements ImageAdapterM.OnItemClickListener {

private RecyclerView mRecyclerView;
private ImageAdapterM mAdapter;

private ProgressBar mProgressCircle;

private FirebaseStorage mStorage;
private DatabaseReference mDatabaseRef;
private ValueEventListener mDBListener;

private List<UploadM> mUploads;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercedes_user_view);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapterM(MercedesUserView.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(MercedesUserView.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploadsMerc");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {

        mUploads.clear();

        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
        UploadM upload = postSnapshot.getValue(UploadM.class);
        upload.setKey(postSnapshot.getKey());
        mUploads.add(upload);
        }

        mAdapter.notifyDataSetChanged();

        mProgressCircle.setVisibility(View.INVISIBLE);
        }

@Override
public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(MercedesUserView.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        mProgressCircle.setVisibility(View.INVISIBLE);
        }
        });
        }
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {

    }
@Override
protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
        }
        }