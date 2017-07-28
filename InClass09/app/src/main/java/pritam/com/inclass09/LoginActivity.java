package pritam.com.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pritam.com.inclass09.R;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    boolean auth=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username=(EditText)findViewById(R.id.usernamefield);
        final EditText password=(EditText)findViewById(R.id.passwordfield);


        Button b=(Button)findViewById(R.id.login);
        Button reg=(Button)findViewById(R.id.register);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreg = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intentreg);
                finish();

            }
        });



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String ID=username.getText().toString().trim();
                final String pass=password.getText().toString().trim();

                String test="INVALID";
                if(isValidEmail(ID))
                    test=ID.substring(0,ID.indexOf("."));
                final String key=test;
                mDatabase = FirebaseDatabase.getInstance().getReference();




               /* ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        Post post = dataSnapshot.getValue(Post.class);
                        Log.d("POST",post.toString());
                        if(post.getEmail().equals(ID)&& post.getPwd().equals(pass)){
                            auth=true;

                        }

                        // ...
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };*/
                mDatabase.child("Test").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post post = dataSnapshot.getValue(Post.class);
                        //Log.d("POST", post.toString());
                        if(post!=null) {
                            if(isValidEmail(ID)) {
                                if (post.getEmail().equals(ID) && post.getPwd().equals(pass)) {
                                    //auth = true;
                                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("USERNAME", key);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else
                                Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                /*if(auth){
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USERNAME",key);
                    startActivity(intent);
                    finish();
                }*/



                }


        });


    }

    private boolean isValidEmail(String email_id) {


        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email_id);
        return m.matches();


    }
}
