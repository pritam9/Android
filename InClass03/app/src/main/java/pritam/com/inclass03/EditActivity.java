package pritam.com.inclass03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Pritam on 5/31/16.
 */
public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        final String name = getIntent().getExtras().getString(DisplayActivity.EDT_NAME);
        final String email = getIntent().getExtras().getString(DisplayActivity.EDT_EMAIL);
        final String lang = getIntent().getExtras().getString(DisplayActivity.EDT_LANG);
        final EditText nameText = (EditText) findViewById(R.id.editName);
        final TextView nameForText = (TextView) findViewById(R.id.editNameText);

        if(name!= null)
        {

            nameForText.setText(R.string.name);
            nameText.setText(name);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.editNameLayout);
            linearLayout.setVisibility(View.VISIBLE);
        }
        if(email!=null)
        {
            //EditText nameText = (EditText) findViewById(R.id.editName);
            nameForText.setText(R.string.email);
            nameText.setText(email);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.editNameLayout);
            linearLayout.setVisibility(View.VISIBLE);
        }
        if(lang!=null)
        {
            RadioGroup rg = (RadioGroup) findViewById(R.id.edit_radio_group);
            TextView textView = (TextView) findViewById(R.id.lang_textView);
            Log.d("Demo",lang+" - "+R.string.java_radio+"-");
            if(lang.equals("Java"))
            {

                rg.check(R.id.editJava_radioButton);
            }
            else if(lang.equals("C"))
            {
                rg.check(R.id.editC_radioButton);
            }
            else if(lang.equals("C#"))
            {
                rg.check(R.id.editC_Sharp_radioButton2);
            }
            rg.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                if(name!=null)
                {
                    if(nameText.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Name not updated as it is blank",Toast.LENGTH_SHORT).show();
                        //return false;
                    }
                    else
                    {
                        Log.d("Demo",nameText.getText().toString());

                        intent.putExtra(DisplayActivity.EDT_NAME,nameText.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
                if(email!=null)
                {
                    if(!isValidEmail(nameText.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Email Id",Toast.LENGTH_SHORT).show();
                        //return false;
                    }
                    else
                    {
                        Log.d("Demo",nameText.getText().toString());
                        intent.putExtra(DisplayActivity.EDT_EMAIL,nameText.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    //EditText nameText = (EditText) findViewById(R.id.editName);

                }
                if(lang!=null)
                {
                    RadioGroup rg = (RadioGroup) findViewById(R.id.edit_radio_group);
                    RadioButton radioButton= (RadioButton) findViewById(rg.getCheckedRadioButtonId());

                    intent.putExtra(DisplayActivity.EDT_LANG,radioButton.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

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
