package com.example.cr.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button button_start, button_stop;
    TextView textView;
    Boolean mStop;
    int count=0;
    MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_start= (Button) findViewById(R.id.start_button);
        button_stop = (Button) findViewById(R.id.stop_button);
        textView = (TextView) findViewById(R.id.text_view);
        button_start.setOnClickListener(this);
        button_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.start_button: mStop = true;
                                    myAsyncTask = new MyAsyncTask();
                                    myAsyncTask.execute(count);
                                    break;

            case R.id.stop_button:  mStop = false;
                                    break;
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer>
    {
        int customCount = 0;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            customCount = 0;
        }

        @Override
        protected Integer doInBackground(Integer... params)
        {
            customCount = params[0];
            while(mStop)
            {
                try
                {
                    Thread.sleep(1000);
                    customCount++;
                    publishProgress(customCount);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if(isCancelled())
                {
                    break;
                }
            }
            return customCount;
        }


        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            textView.setText(""+values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);
            textView.setText(""+integer);
            count=integer;
        }
    }
}
