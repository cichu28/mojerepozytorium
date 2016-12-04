package com.example.marcin.clientservertutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//package com.example.androidclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView textResponse, TextViewresponse;
    EditText editTextAddress, editTextPort, editTextPWM_value;
    Button buttonConnect, buttonClear;
    int PWM  = 1;
    byte PWM_byte;
    String dstAddress;
    int dstPort;
    String response;
    MyClientTask myClientTask;
    private static SeekBar seekBarPWMfrontback;
    private static SeekBar seekBarPWMrightleft;
    private static TextView textViewPWM_FB;
    private static TextView textViewPWM_RL;
    OutputStream byteArrayOutputStream;
    Socket socket;
    byte[] buffer = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editTextPWM_value  = (EditText)findViewById(R.id.PWM_value);
//        TextViewresponse = (TextView)findViewById(R.id.response);
        seekBarPWMfrontback = (SeekBar)findViewById(R.id.seekBar);
        seekBarPWMrightleft = (SeekBar)findViewById(R.id.seekBar2);
        textViewPWM_FB = (TextView)findViewById(R.id.response);
        textViewPWM_RL = (TextView)findViewById(R.id.response2);

        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonClear = (Button)findViewById(R.id.clear);
//        textResponse = (TextView)findViewById(R.id.response);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener() {
            // *** USTAWIAM PUSTY TEKST
            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });
        SetSeekBarPWMrightleft();
        SetSeekBarPWM();

    }

    public void EdytujTekst(View view)
    {
        String S = "PWM: " + editTextPWM_value.getText().toString();
        PWM = Integer.parseInt(editTextPWM_value.getText().toString());     // konwertuje na inta
        PWM_byte = (byte) PWM;
        TextViewresponse.setText(S);
    }

    public void SetSeekBarPWM()
    {
        textViewPWM_FB.setText("PWM: " + seekBarPWMfrontback.getProgress() + " / " + seekBarPWMfrontback.getMax());
        seekBarPWMfrontback.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textViewPWM_FB.setText("PWM: " + progress + " / " + seekBarPWMfrontback.getMax());
                        Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
                        PWM = progress_value;
                        buffer[0] = (byte)PWM;
                        // tu bedzie wysylanie na server pwm
                        try
                        {
                            byteArrayOutputStream.write(buffer);
                            //socket.close(); // na koniec appki wypierdolic
                        }

                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewPWM_FB.setText("PWM: " + progress_value + " / " + seekBarPWMfrontback.getMax());
                        Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void SetSeekBarPWMrightleft()
    {
        textViewPWM_RL.setText("PWM2: " + seekBarPWMrightleft.getProgress() + " / " + seekBarPWMrightleft.getMax());
        seekBarPWMrightleft.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBarPWMrightleft, int progress, boolean fromUser) {
                        progress_value = progress;
                        textViewPWM_RL.setText("PWM: " + progress + " / " + seekBarPWMrightleft.getMax());
                        Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
                        PWM = progress_value;
                        buffer[1] = (byte)PWM;
                        // tu bedzie wysylanie na server pwm
                        try
                        {
                            byteArrayOutputStream.write(buffer);
                            //socket.close(); // na koniec appki wypierdolic
                        }

                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBarPWMrightleft) {
                        Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBarPWMrightleft) {
                        textViewPWM_RL.setText("PWM: " + progress_value + " / " + seekBarPWMrightleft.getMax());
                        Toast.makeText(MainActivity.this, "SeekBar in StopTracking", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    // *** PRZYCISK "NASŁUCHUJE"
    OnClickListener buttonConnectOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
            // Co sie stanie po wciśnięciuCONNETCT?

            myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
            myClientTask.execute();

        }
    };

    public class MyClientTask extends AsyncTask<Void, Void, Void>
    {
        MyClientTask(String addr, int port)
        {
            dstAddress = addr;
            dstPort = port;
        }

        // *** KLIENT
        @Override
        protected Void doInBackground(Void... arg0)
        {
            // *** WYSYŁAM
            try
            {
                socket = new Socket("172.24.1.1", 8080);
                byteArrayOutputStream = socket.getOutputStream();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }
}