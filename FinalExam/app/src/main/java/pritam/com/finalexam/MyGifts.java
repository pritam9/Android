package pritam.com.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyGifts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gifts);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.android4848);
        getSupportActionBar().setTitle(MainActivity.activePerson.getName());
        //List<Gifts> myGifts = (List<Gifts>) getIntent().getExtras().getSerializable("MY_GIFTS");
        final List<Gifts> myGifts = new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.getRoot().child(MainActivity.person_key).child(MainActivity.activePerson.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d1:dataSnapshot.child("myGifts").getChildren())
                {
                    Gifts gift=d1.getValue(Gifts.class);
                    myGifts.add(gift);

                }
                Comparator<Gifts> cmp = new Comparator<Gifts>() {
                    public int compare(Gifts m1, Gifts m2) {
                        return ((Long)m1.getPrice()).compareTo(m2.getPrice());
                    }
                };

                Collections.sort(myGifts,cmp);

                ListView giftListView = (ListView) findViewById(R.id.myGiftsListView);
                GiftListAdapter giftListAdapter = new GiftListAdapter(MyGifts.this,R.layout.my_gifts,myGifts);
                giftListView.setAdapter(giftListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyGifts.this, "Error Connecting to Firebase", Toast.LENGTH_SHORT).show();
            }
        });
        if (myGifts!=null)
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_gift_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_gift:
                Intent intent = new Intent(this,AddGift.class);
                startActivity(intent);
                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}

