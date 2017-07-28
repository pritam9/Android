package pritam.com.finalexam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String gift_key ="Gifts",person_key="PERSONS";
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    OnCompleteListener onSignInListener, onSignUpListener;
    public static Person activePerson;
    public static String activePersonName;
    public static long totalExpenses;
    //Person dummyUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.android4848);
        getSupportActionBar().setTitle("Christmas Gift");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        //showProgressBar();
        final ListView personListView = (ListView) findViewById(R.id.personListView);
        if (isConnectedOnline())
        {
            databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.getRoot().child(person_key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final List<Person> persons= new ArrayList<Person>();
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        Person person = new Person();
                        person.setName((String) snapshot.child("name").getValue());
                        person.setExpenses((Long) snapshot.child("expenses").getValue());
                        person.setBudgetLimit((Long) snapshot.child("budgetLimit").getValue());
                        List<Gifts> myGifts=new ArrayList<Gifts>();
                        for(DataSnapshot d1:snapshot.child("myGifts").getChildren())
                        {
                            Gifts gift=d1.getValue(Gifts.class);
                            myGifts.add(gift);
                        }
                        person.setMyGifts(myGifts);
                        persons.add(person);
                    }

                    PersonListAdapter personAdapter=new PersonListAdapter(MainActivity.this,R.layout.person_list_view,persons);
                    personAdapter.setNotifyOnChange(true);
                    personListView.setAdapter(personAdapter);

                    personListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            List<Gifts> myGifts= persons.get(position).getMyGifts();
                            activePersonName=persons.get(position).getName();
                            //totalExpenses=persons.get(position).get();
                            activePerson=persons.get(position);
                            Intent intent = new Intent(MainActivity.this,MyGifts.class);
                            intent.putExtra("MY_GIFTS", (Serializable) myGifts);
                            intent.putExtra("PERSON",persons.get(position).getName());
                            startActivity(intent);
                            finish();
                        }
                    });
                    //hideProgressBar();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else 
        {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void hideProgressBar() {
        progressDialog.hide();
    }

    private void showProgressBar() {
        progressDialog.show();
    }


    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_person:
                Intent intent = new Intent(this,AddPerson.class);
                startActivity(intent);
                finish();

                    break;

                }

                return super.onOptionsItemSelected(item);
        }
    }
