package pritam.com.inclass03;

import android.app.Activity;

/**
 * Created by Pritam on 5/31/16.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayActivity extends AppCompatActivity {
    public static final String EDT_NAME="Name";
    public static final String EDT_EMAIL="Email";
    public static final String EDT_LANG="Language";
    public static final int REQ_CODE_FOR_NAME = 100;
    public static final int REQ_CODE_FOR_EMAIL = 200;
    public static final int REQ_CODE_FOR_LANG = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        final TextView nameText = (TextView) findViewById(R.id.nameValue);
        Student student = (Student) getIntent().getExtras().getSerializable(MainActivity.REQ_KEY);
        nameText.setText(student.getName());
        final TextView emailText = (TextView) findViewById(R.id.emailValue);
        emailText.setText(student.getEmail_id());
        final TextView langText = (TextView) findViewById(R.id.langValue);
        langText.setText(student.getFavourite_language());

        findViewById(R.id.editName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this,EditActivity.class);
                intent.putExtra(EDT_NAME,nameText.getText());
                startActivityForResult(intent,REQ_CODE_FOR_NAME);

            }
        });

        findViewById(R.id.editEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this,EditActivity.class);
                intent.putExtra(EDT_EMAIL,emailText.getText());
                startActivityForResult(intent,REQ_CODE_FOR_EMAIL);

            }
        });

        findViewById(R.id.editLang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this,EditActivity.class);
                intent.putExtra(EDT_LANG,langText.getText());
                startActivityForResult(intent,REQ_CODE_FOR_LANG);

            }
        });

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub

        if(requestCode == REQ_CODE_FOR_NAME){

            if (resultCode == RESULT_OK){
                TextView nameText = (TextView) findViewById(R.id.nameValue);
                nameText.setText(data.getExtras().getString(EDT_NAME));
                //R.id.name.setText(data.getExtras().getSerializable());

            }else if(resultCode == RESULT_CANCELED){

                Log.d("Demo"," Error in parsing");

            }

        }
        if(requestCode == REQ_CODE_FOR_EMAIL){

            if (resultCode == RESULT_OK){
                TextView emailText = (TextView) findViewById(R.id.emailValue);
                emailText.setText(data.getExtras().getString(EDT_EMAIL));
                //R.id.name.setText(data.getExtras().getSerializable());

            }else if(resultCode == RESULT_CANCELED){

                Log.d("Demo"," Error in parsing");

            }

        }

        if(requestCode == REQ_CODE_FOR_LANG){

            if (resultCode == RESULT_OK){
                TextView langText = (TextView) findViewById(R.id.langValue);
                langText.setText(data.getExtras().getString(EDT_LANG));
                //R.id.name.setText(data.getExtras().getSerializable());

            }else if(resultCode == RESULT_CANCELED){

                Log.d("Demo"," Error in parsing");

            }

        }

    }
}
