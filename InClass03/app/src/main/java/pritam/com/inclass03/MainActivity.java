package pritam.com.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static final  String REQ_KEY="Student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.name)).getText().toString();
                String email_id = ((EditText)findViewById(R.id.email_id)).getText().toString();
                RadioGroup rg=(RadioGroup)findViewById(R.id.radio_group);
                String fav_language="";
                int checkedId = rg.getCheckedRadioButtonId();
                if(rg.getCheckedRadioButtonId()==R.id.c ||rg.getCheckedRadioButtonId()==R.id.c_sharp || rg.getCheckedRadioButtonId()==R.id.java) {
                    fav_language = ((RadioButton) findViewById(checkedId)).getText().toString();
                }
                //String
                boolean isValidInput = validateInput(name,email_id,fav_language);
                if(isValidInput)
                {
                    Student student = new Student(name,email_id,fav_language);
                    Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
                    intent.putExtra(REQ_KEY,student);
                    startActivity(intent);
                }


            }
        });

    }

    private boolean validateInput(String name, String email_id, String fav_language) {
        if(name.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Name cannot be blank",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!isValidEmail(email_id))
        {
            Toast.makeText(getApplicationContext(),"Invalid Email Id",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(fav_language.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Invalid Language Selected",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email_id) {
        /*Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


            pattern = Pattern.compile(EMAIL_PATTERN);



            matcher = pattern.matcher(email_id);
        Log.d("Demo",matcher.matches())
            return matcher.matches();*/

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email_id);
        return m.matches();


    }
}
