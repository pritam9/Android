package pritam.com.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //submitPost();
        Log.d("__________------" , mDatabase.toString());
        //writeNewPost("1", "me", "First", "Body");


        final EditText fullName = (EditText) findViewById(R.id.full_name);
        final EditText email = (EditText) findViewById(R.id.email_id);
        final EditText password = (EditText) findViewById(R.id.password);

        findViewById(R.id.sign_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = fullName.getText().toString().trim();
                String emailId = email.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if(fName.equals("") || emailId.equals("") || pwd.equals("")){
                    Toast.makeText(SignUp.this, "All Fields are required!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(isValidEmail(emailId))
                        submitPost("2", fName, emailId, pwd);
                    else
                        Toast.makeText(SignUp.this, "Invalid Email!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void submitPost(final String id, final String userName, final String email, final String pwd) {
        /*final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }
*/
        // [START single_value_read]
        final String userId = email.substring(0,email.indexOf("."));
        mDatabase.child("Test").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Log.d("xcDemo",dataSnapshot.getValue(Post.class).toString());
                        //writeNewPost(id,userName,email,pwd);
                        // Get user value
                        Post user = dataSnapshot.getValue(Post.class);

                        // [START_EXCLUDE]
                        if (user != null) {
                            // User is null, error out
                            Toast.makeText(SignUp.this, "Already Exists!!", Toast.LENGTH_SHORT).show();

                        } else {
                            // Write new post
                            writeNewPost(userId, userName, email, pwd);
                            Intent intent = new Intent(SignUp.this,MainActivity.class);
                            intent.putExtra("USERNAME",userId);
                            startActivity(intent);
                            finish();

                        }

                        // Finish this Activity, back to the stream
                        //finish();
                        // [END_EXCLUDE]

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("FireBase", "getUser:onCancelled", databaseError.toException());
                    }

                });
        // [END single_value_read]
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String email, String pwd) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
       // Log.d("******", mDatabase.child("Test").getKey());
        String key = email.substring(0,email.indexOf("."));

            //String key = email;//mDatabase.push().getKey();
            Log.d("Test", key);
            Post post = new Post(userId, username, email, pwd);
            Map<String, String> postValues = post.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/Test/" + key, postValues);
            //childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

            mDatabase.updateChildren(childUpdates);
        Toast.makeText(SignUp.this, "Success!!!", Toast.LENGTH_SHORT).show();
        }


    private boolean isValidEmail(String email_id) {


        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email_id);
        return m.matches();


    }

}
