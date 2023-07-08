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

public class PSD_Intent extends AppCompatActivity {

    private ImageView psdImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psd_intent);

        psdImage = findViewById(R.id.psdImage);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference();


        Bundle value = getIntent().getExtras();


        DatabaseReference getImage = databaseReference.child("psdoffices");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/umapsdatabase.appspot.com/o/pictures%2F1667437376657.jpg?alt=media&token=e3ce871a-7154-46ca-9afa-93455761efda").into(psdImage);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}