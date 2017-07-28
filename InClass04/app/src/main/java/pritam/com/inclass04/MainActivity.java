package pritam.com.inclass04;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(getResources().getStringArray(R.array.spinner_array));

        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); //display hint
       /* // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.
        // Apply the adapter to the spinner*/
        //spinner.setAdapter(adapter);
        final ProgressDialog pd=new ProgressDialog(MainActivity.this);
        pd.setMessage("Thread");
        pd.setCancelable(false);
        handler=new Handler(new Handler.Callback() {


            //pd.set();
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what==1)
                {
                    pd.show();
                }
                else if (msg.what==2)
                {
                    pd.dismiss();
                    TextView textView = (TextView) findViewById(R.id.password);
                    textView.setText((String) msg.obj);
                }
                return false;
            }
        });
        //spinner.setPrompt("Select Password Length");
        final CheckBox numberCheckBox= (CheckBox) findViewById(R.id.numberBox);
        final CheckBox upperCaseBox = (CheckBox) findViewById(R.id.upperBox);
        final CheckBox lowerCheckBox = (CheckBox) findViewById(R.id.lowerBox);
        final CheckBox specialCharacters = (CheckBox) findViewById(R.id.specialBox);
        final TextView textView = (TextView) findViewById(R.id.password);
        findViewById(R.id.asyncButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                int selectedItemPosition = spinner.getSelectedItemPosition();
                int numbers = 0;
                int upper = 0;
                int lower = 0;
                int special =0;
                if(numberCheckBox.isChecked())
                {
                    numbers=1;
                }
                if(upperCaseBox.isChecked())
                    upper=1;
                if(lowerCheckBox.isChecked())
                    lower=1;
                if (specialCharacters.isChecked())
                    special=1;
                if(spinner.getSelectedItemPosition()==3)
                {

                    Toast.makeText(MainActivity.this,"Select Password Length",Toast.LENGTH_SHORT).show();
                }
                else if(numbers==0 && upper==0 && lower==0 && special==0)
                    Toast.makeText(MainActivity.this,"Select atleast one checkbox!!",Toast.LENGTH_SHORT).show();
                else
                    new GeneratePassword().execute(selectedItemPosition,numbers,upper,lower,special);


            }
        });

        findViewById(R.id.threadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                Log.d("Demo",""+spinner.getSelectedItemPosition());


                if(spinner.getSelectedItemPosition()==3)
                {

                    Toast.makeText(MainActivity.this,"Select Password Length",Toast.LENGTH_SHORT).show();
                }
                else if(numberCheckBox.isChecked() || upperCaseBox.isChecked() || lowerCheckBox.isChecked() || specialCharacters.isChecked())
                    new Thread(new GeneratePasswordThread(spinner.getSelectedItemPosition(),numberCheckBox.isChecked(),upperCaseBox.isChecked(),lowerCheckBox.isChecked() , specialCharacters.isChecked())).start();
                else
                    Toast.makeText(MainActivity.this,"Select atleast one checkbox!!",Toast.LENGTH_SHORT).show();


            }
        });


        //new GeneratePassword().execute();
    }

    private class GeneratePassword extends AsyncTask<Integer,Integer,String>{
        ProgressDialog pd;
        String str=null;


        @Override
        protected String doInBackground(Integer... params) {
            boolean isNumberChecked=false;
            boolean isUpperChecked = false;
            boolean isLowerChecked = false;
            boolean isSpecialChecked = false;
            if(params[1]==1)
                isNumberChecked=true;
            if(params[2]==1)
                isUpperChecked =true;
            if(params[3]==1)
                isLowerChecked=true;
            if(params[4]==1)
                isSpecialChecked=true;




            publishProgress(100);
            str = Util.getPassword(params[0],isNumberChecked,isUpperChecked,isLowerChecked,isSpecialChecked);

            //return null;
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Async Task");
            pd.setCancelable(false);
            //pd.setMax(100);
            //pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.show();
            //str=Util.getPassword(2,true,true,true,true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) findViewById(R.id.password);
            textView.setText(str);
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pd.setProgress(values[0]);
        }



        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Hi");
            pd.setMax(100);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.show();
            str=Util.getPassword(2,true,true,true,true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(str);
            pd.dismiss();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pd.setProgress(values[0]);
        }*/
    }

    class GeneratePasswordThread implements Runnable{
        int len;
        boolean isNumberChecked,isUpperCheckd,isLowerChecked,isSpecialChecked;

        public GeneratePasswordThread(int len, boolean isNumberChecked, boolean isUpperCheckd, boolean isLowerChecked, boolean isSpecialChecked) {
            this.len = len;
            this.isNumberChecked = isNumberChecked;
            this.isUpperCheckd = isUpperCheckd;
            this.isLowerChecked = isLowerChecked;
            this.isSpecialChecked = isSpecialChecked;
        }

        @Override
        public void run() {

            Message msg = new Message();
            msg.what=1;
            handler.sendMessage(msg);
            String pwd = Util.getPassword(len,isNumberChecked,isUpperCheckd,isLowerChecked,isSpecialChecked);
            msg=new Message();
            msg.what=2;
            msg.obj=pwd;
            handler.sendMessage(msg);
        }
    }
}
