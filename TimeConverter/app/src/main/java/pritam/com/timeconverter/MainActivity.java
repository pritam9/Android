package pritam.com.timeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch toggle= (Switch) findViewById(R.id.switch1);

        //mstButton.setVisibility(View.INVISIBLE);
        setButtonView(true);
        setRadioView(false);


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("Time Converter", "MST Gone!!");
                    setButtonView(true);
                    setRadioView(false);
                }
                else {
                    Log.d("Time Converter", "MST Came");
                    setButtonView(false);
                    setRadioView(true);
                }
            }
        });

        findViewById(R.id.est).setOnClickListener(this);
        findViewById(R.id.cst).setOnClickListener(this);
        findViewById(R.id.mst).setOnClickListener(this);
        findViewById(R.id.PST).setOnClickListener(this);
        findViewById(R.id.clearAll).setOnClickListener(this);
        findViewById(R.id.convert).setOnClickListener(this);

        Button convert = (Button) findViewById(R.id.convert);
        convert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                int selectedId = rg.getCheckedRadioButtonId();
                EditText hours = (EditText) findViewById(R.id.hours);
                EditText minutes = (EditText) findViewById(R.id.minutes);
                TextView result = (TextView) findViewById(R.id.result);
                TextView prevDay = (TextView) findViewById(R.id.prevDay);
                //Log.d("Demo","On Click!!");
                if(selectedId==R.id.clearRadio)
                {
                    hours.setText("");
                    minutes.setText("");
                    result.setText("");
                    prevDay.setVisibility(View.INVISIBLE);
                    rg.check(R.id.estRadio);
                    Log.d("Demo","Clear All");
                }
                else {

                    if (hours.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please provide Hours", Toast.LENGTH_SHORT).show();
                    } else if (minutes.getText().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please provide Minutes", Toast.LENGTH_SHORT).show();
                    } else {
                        int hour = Integer.parseInt(hours.getText().toString());
                        int min = Integer.parseInt(minutes.getText().toString());
                        if (hour > 23) {
                            hours.setText("");
                            minutes.setText("");
                            result.setText("");
                            prevDay.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Hours should be between 0-23", Toast.LENGTH_SHORT).show();
                        } else if (min > 59) {
                            hours.setText("");
                            minutes.setText("");
                            result.setText("");
                            prevDay.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Minutes should be between 0-59", Toast.LENGTH_SHORT).show();

                        } else if (selectedId == R.id.estRadio) {
                            int resultHour = hour - 5;
                            String time = "";
                            boolean isPrevDay = false;
                            if (resultHour < 0) {
                                resultHour = 24 + resultHour;
                                time = time + resultHour + ":";
                                isPrevDay = true;
                                //=  result.setText("EST:\t"+);
                            } else if (resultHour > 9) {
                                time = time + resultHour + ":";
                            } else
                                time = time + 0 + resultHour + ":";
                            if (min > 9)
                                time = time + min;
                            else
                                time = time + 0 + min;


                            result.setText("EST:\t\t" + time);
                            if (isPrevDay)
                                prevDay.setVisibility(View.VISIBLE);
                            else
                                prevDay.setVisibility(View.INVISIBLE);
                        } else if (selectedId == R.id.cstRadio) {
                            int resultHour = hour - 6;
                            String time = "";
                            boolean isPrevDay = false;
                            if (resultHour < 0) {
                                resultHour = 24 + resultHour;
                                time = time + resultHour + ":";
                                isPrevDay = true;
                                //=  result.setText("EST:\t"+);
                            } else if (resultHour > 9) {
                                time = time + resultHour + ":";
                            } else
                                time = time + 0 + resultHour + ":";
                            if (min > 9)
                                time = time + min;
                            else
                                time = time + 0 + min;
                            result.setText("CST:\t\t" + time+"\t");
                            if (isPrevDay)
                                prevDay.setVisibility(View.VISIBLE);
                            else
                                prevDay.setVisibility(View.INVISIBLE);
                        } else if (selectedId == R.id.mstRadio) {
                            int resultHour = hour - 7;
                            String time = "";
                            boolean isPrevDay = false;
                            if (resultHour < 0) {
                                resultHour = 24 + resultHour;
                                time = time + resultHour + ":";
                                isPrevDay = true;
                                //=  result.setText("EST:\t"+);
                            } else if (resultHour > 9) {
                                time = time + resultHour + ":";
                            } else
                                time = time + 0 + resultHour + ":";
                            if (min > 9)
                                time = time + min;
                            else
                                time = time + 0 + min;
                            result.setText("MST:\t" + time);
                            if (isPrevDay)
                                prevDay.setVisibility(View.VISIBLE);
                            else
                                prevDay.setVisibility(View.INVISIBLE);
                        } else if (selectedId == R.id.pstRadio) {
                            int resultHour = hour - 8;
                            String time = "";
                            boolean isPrevDay = false;
                            if (resultHour < 0) {
                                resultHour = 24 + resultHour;
                                time = time + resultHour + ":";
                                isPrevDay = true;
                                //=  result.setText("EST:\t"+);
                            } else if (resultHour > 9) {
                                time = time + resultHour + ":";
                            } else
                                time = time + 0 + resultHour + ":";
                            if (min > 9)
                                time = time + min;
                            else
                                time = time + 0 + min;
                            result.setText("PST:\t" + time);
                            if (isPrevDay)
                                prevDay.setVisibility(View.VISIBLE);
                            else
                                prevDay.setVisibility(View.INVISIBLE);
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Please check one radioButton",Toast.LENGTH_SHORT);
                    }
                }

            }
        });
    }



    @Override
    public void onClick(View v) {
        EditText hours = (EditText) findViewById(R.id.hours);
        EditText minutes = (EditText) findViewById(R.id.minutes);
        TextView result = (TextView) findViewById(R.id.result);
        TextView prevDay = (TextView) findViewById(R.id.prevDay);
        //Log.d("Demo","On Click!!");
        if(v.getId()==R.id.clearAll)
        {
            hours.setText("");
            minutes.setText("");
            result.setText("");
            prevDay.setVisibility(View.INVISIBLE);
            Log.d("Demo","Clear All");
        }
        else
        {

            if(hours.getText().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Please provide Hours",Toast.LENGTH_SHORT).show();
            }
            else if(minutes.getText().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Please provide Minutes",Toast.LENGTH_SHORT).show();
            }
            else
            {
                int hour = Integer.parseInt(hours.getText().toString());
                int min =Integer.parseInt(minutes.getText().toString());
                if(hour>23)
                {
                    hours.setText("");
                    minutes.setText("");
                    result.setText("");
                    prevDay.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Hours should be between 0-23",Toast.LENGTH_SHORT).show();
                }
                else if(min>59)
                {
                    hours.setText("");
                    minutes.setText("");
                    result.setText("");
                    prevDay.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Minutes should be between 0-59",Toast.LENGTH_SHORT).show();

                }
                else if(v.getId()==R.id.est) {
                    int resultHour = hour - 5;
                    String time = "";
                    boolean isPrevDay = false;
                    if (resultHour < 0) {
                        resultHour = 24 + resultHour;
                        time = time + resultHour + ":";
                        isPrevDay = true;
                        //=  result.setText("EST:\t"+);
                    } else if (resultHour > 9) {
                        time = time + resultHour + ":";
                    } else
                        time = time + 0 + resultHour + ":";
                    if (min > 9)
                        time = time + min;
                    else
                        time = time + 0 + min;


                    result.setText("EST:\t" + time);
                    if (isPrevDay)
                        prevDay.setVisibility(View.VISIBLE);
                    else
                        prevDay.setVisibility(View.INVISIBLE);
                }
                    else if(v.getId()==R.id.cst)
                    {
                        int resultHour = hour - 6;
                        String time = "";
                        boolean isPrevDay = false;
                        if (resultHour < 0) {
                            resultHour = 24 + resultHour;
                            time = time + resultHour + ":";
                            isPrevDay = true;
                            //=  result.setText("EST:\t"+);
                        } else if (resultHour > 9) {
                            time = time + resultHour + ":";
                        } else
                            time = time + 0 + resultHour + ":";
                        if (min > 9)
                            time = time + min;
                        else
                            time = time + 0 + min;
                        result.setText("CST:\t"+time);
                        if (isPrevDay)
                            prevDay.setVisibility(View.VISIBLE);
                        else
                            prevDay.setVisibility(View.INVISIBLE);
                    }
                    else if(v.getId()==R.id.mst)
                    {
                        int resultHour = hour - 7;
                        String time = "";
                        boolean isPrevDay = false;
                        if (resultHour < 0) {
                            resultHour = 24 + resultHour;
                            time = time + resultHour + ":";
                            isPrevDay = true;
                            //=  result.setText("EST:\t"+);
                        } else if (resultHour > 9) {
                            time = time + resultHour + ":";
                        } else
                            time = time + 0 + resultHour + ":";
                        if (min > 9)
                            time = time + min;
                        else
                            time = time + 0 + min;
                        result.setText("MST:\t"+time);
                        if (isPrevDay)
                            prevDay.setVisibility(View.VISIBLE);
                        else
                            prevDay.setVisibility(View.INVISIBLE);
                    }
                    else if(v.getId()==R.id.PST)
                    {
                        int resultHour = hour - 8;
                        String time = "";
                        boolean isPrevDay = false;
                        if (resultHour < 0) {
                            resultHour = 24 + resultHour;
                            time = time + resultHour + ":";
                            isPrevDay = true;
                            //=  result.setText("EST:\t"+);
                        } else if (resultHour > 9) {
                            time = time + resultHour + ":";
                        } else
                            time = time + 0 + resultHour + ":";
                        if (min > 9)
                            time = time + min;
                        else
                            time = time + 0 + min;
                        result.setText("PST:\t"+time);
                        if (isPrevDay)
                            prevDay.setVisibility(View.VISIBLE);
                        else
                            prevDay.setVisibility(View.INVISIBLE);
                    }




            }
        }
    }


    private void setButtonView(boolean isButton) {
        if(isButton)
        {
            findViewById(R.id.est).setVisibility(View.VISIBLE);
            findViewById(R.id.cst).setVisibility(View.VISIBLE);
            findViewById(R.id.mst).setVisibility(View.VISIBLE);
            findViewById(R.id.PST).setVisibility(View.VISIBLE);
            findViewById(R.id.clearAll).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.est).setVisibility(View.GONE);
            findViewById(R.id.cst).setVisibility(View.GONE);
            findViewById(R.id.mst).setVisibility(View.GONE);
            findViewById(R.id.PST).setVisibility(View.GONE);
            findViewById(R.id.clearAll).setVisibility(View.GONE);
        }
        EditText hours = (EditText) findViewById(R.id.hours);
        EditText min = (EditText) findViewById(R.id.minutes);
        TextView result = (TextView) findViewById(R.id.result);
        TextView prevDay = (TextView) findViewById(R.id.prevDay);
        hours.setText("");
        min.setText("");
        result.setText("");
        prevDay.setVisibility(View.INVISIBLE);

    }

    private void setRadioView(boolean isRadio) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        Button convert = (Button) findViewById(R.id.convert);
        EditText hours = (EditText) findViewById(R.id.hours);
        EditText min = (EditText) findViewById(R.id.minutes);
        TextView result = (TextView) findViewById(R.id.result);
        TextView prevDay = (TextView) findViewById(R.id.prevDay);
        if(isRadio)
        {
            rg.setVisibility(View.VISIBLE);
            convert.setVisibility(View.VISIBLE);

        }
        else {
            rg.setVisibility(View.GONE);
            convert.setVisibility(View.GONE);
        }
        hours.setText("");
        min.setText("");
        result.setText("");
        prevDay.setVisibility(View.INVISIBLE);
    }
}
