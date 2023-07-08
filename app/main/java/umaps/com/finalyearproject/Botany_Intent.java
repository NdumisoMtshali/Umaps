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

public class Botany_Intent extends AppCompatActivity {
    private ImageView botImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botany_intent);

        botImage = findViewById(R.id.botImage);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference();


        Bundle value = getIntent().getExtras();


        DatabaseReference getImage = databaseReference.child("departmentofbotany");

        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/umapsdatabase.appspot.com/o/pictures%2F1667435149070.jpg?alt=media&token=def605db-bece-4052-9ca0-bef6a23d7bf8").into(botImage);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    }
