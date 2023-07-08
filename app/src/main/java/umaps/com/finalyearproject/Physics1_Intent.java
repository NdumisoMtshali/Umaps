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

public class Physics1_Intent extends AppCompatActivity {

    private ImageView phyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physics1_intent);

        phyImage = findViewById(R.id.phyImage);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference();


        Bundle value = getIntent().getExtras();


        DatabaseReference getImage = databaseReference.child("physics1");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/umapsdatabase.appspot.com/o/pictures%2F1667435853574.jpg?alt=media&token=4546d09b-b04c-4ce2-8467-bf471230dbbc").into(phyImage);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}