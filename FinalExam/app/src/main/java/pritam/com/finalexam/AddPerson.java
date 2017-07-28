package pritam.com.finalexam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPerson extends AppCompatActivity {
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.android4848);
        getSupportActionBar().setTitle("Add Person");
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_person:
                EditText pName= (EditText) findViewById(R.id.txtPersonName);
                EditText budget= (EditText) findViewById(R.id.txtBudget);
                String personName = pName.getText().toString();
                String budgetLimit = budget.getText().toString();
                if(personName.equals("")||budgetLimit.equals(""))
                    Toast.makeText(AddPerson.this, "All Fields are mandatory!!", Toast.LENGTH_SHORT).show();
                else
                {
                    try {
                        long budgetAmount = Long.parseLong(budgetLimit);
                        Person person=new Person();
                        person.setBudgetLimit(budgetAmount);
                        person.setExpenses(0);
                        person.setName(personName);
                        databaseReference.getRoot().child(MainActivity.person_key).child(personName).setValue(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(AddPerson.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Log.d("ERROR","NOT ADDED = " +task.getException().getMessage());
                            }
                        });


                    }catch (NumberFormatException e){
                        Toast.makeText(AddPerson.this, "Not a valid amount..", Toast.LENGTH_SHORT).show();
                    }

                }


                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
