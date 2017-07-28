package pritam.com.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class AddGift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.android4848);
        //getSupportActionBar().setTitle("Christmas Gift");
        getSupportActionBar().setTitle("ADD GIFTS");
        Log.d("ADD GIFT","ADD GIFT");
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.getRoot().child(MainActivity.gift_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Gifts> gifts= new ArrayList<Gifts>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Gifts gift=snapshot.getValue(Gifts.class);
                    //Log.d("ADD GIFT",gift.getGift());
                    gifts.add(gift);
                }

                Comparator<Gifts> cmp = new Comparator<Gifts>() {
                    public int compare(Gifts m1, Gifts m2) {
                        return ((Long)m1.getPrice()).compareTo(m2.getPrice());
                    }
                };

                Collections.sort(gifts,cmp);

                ListView allGifts= (ListView) findViewById(R.id.addGiftListView);
                GiftListAdapter giftAdapter = new GiftListAdapter(AddGift.this,R.layout.my_gifts,gifts);
                allGifts.setAdapter(giftAdapter);



                allGifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long totalExpenses=MainActivity.activePerson.getExpenses();
                        long limit = MainActivity.activePerson.getBudgetLimit();
                        Gifts gift = gifts.get(position);
                        if(totalExpenses+gift.getPrice()<=limit) {
                            databaseReference.getRoot().child(MainActivity.person_key).child(MainActivity.activePersonName).child("myGifts").push().setValue(gifts.get(position));
                            databaseReference.getRoot().child(MainActivity.person_key).child(MainActivity.activePersonName).child("expenses").setValue(totalExpenses+gift.getPrice());
                            MainActivity.activePerson.setExpenses(totalExpenses+gift.getPrice());
                        }
                        else
                            Toast.makeText(AddGift.this, "Price Exceeding Budget", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(AddGift.this,MyGifts.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddGift.this, "Error fetching Data from Firebase!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
