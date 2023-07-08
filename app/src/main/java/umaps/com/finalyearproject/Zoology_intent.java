package umaps.com.finalyearproject;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Zoology_intent extends AppCompatActivity {

    private ImageView zooImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoology_intent);

        zooImage = findViewById(R.id.zooImage);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference();


        Bundle value = getIntent().getExtras();


        DatabaseReference getImage = databaseReference.child("departmentofzoology");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/umapsdatabase.appspot.com/o/pictures%2F1667871420097.jpg?alt=media&token=3c972701-0352-478b-bea1-8c84e713c0ab").into(zooImage);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    }
