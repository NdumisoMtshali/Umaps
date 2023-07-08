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

public class SC_Intent extends AppCompatActivity {

    private ImageView scImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc_intent);

        scImage = findViewById(R.id.scImage);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference();


        Bundle value = getIntent().getExtras();


        DatabaseReference getImage = databaseReference.child("sc");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/umapsdatabase.appspot.com/o/pictures%2F1667436667239.jpg?alt=media&token=3a90bc0f-4257-4b8c-925e-e2cc47d13464").into(scImage);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    }
