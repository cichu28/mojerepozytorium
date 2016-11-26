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

    private static SeekBar seekBarPWM;
    private static TextView textViewPWM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editTextPWM_value  = (EditText)findViewById(R.id.PWM_value);
//        TextViewresponse = (TextView)findViewById(R.id.response);
        seekBarPWM = (SeekBar)findViewById(R.id.seekBar);
        textViewPWM = (TextView)findViewById(R.id.response2);

        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonClear = (Button)findViewById(R.id.clear);
//        textResponse = (TextView)findViewById(R.id.response);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonClear.setOnClickListener(new OnClickListener()
        {
            // *** USTAWIAM PUSTY TEKST
            @Override
            public void onClick(View v)
            {
                textResponse.setText("");
            }
        });
        SetSeekBarPWM();
    }

//    public void EdytujTekst(View view)
//    {
//        String S = "PWM: " + editTextPWM_value.getText().toString();
//        PWM = Integer.parseInt(editTextPWM_value.getText().toString());     // konwertuje na inta
//        PWM_byte = (byte) PWM;
//        TextViewresponse.setText(S);
//    }

    public void SetSeekBarPWM()
    {
        textViewPWM.setText("PWM: " + seekBarPWM.getProgress() + " / " + seekBarPWM.getMax());
        seekBarPWM.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()
                {
                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textViewPWM.setText("PWM: " + progress + " / " + seekBarPWM.getMax());
                        Toast.makeText(MainActivity.this, "SeekBar in progress", Toast.LENGTH_LONG).show();
                        PWM = progress_value;
                        PWM_byte = (byte) PWM;
                        // tu bedzie wysylanie na server pwm
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(MainActivity.this, "SeekBar in StartTracking", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewPWM.setText("PWM: " + progress_value + " / " + seekBarPWM.getMax());
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
            // You have to verify editTextAddress and editTextPort are input as correct format.
            MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
//           String S = "PWM: " + editTextPWM_value.getText().toString();
//            String S = "PWM: ";
//            PWM = Integer.parseInt(editTextPWM_value.getText().toString());     // konwertuje na inta
           // PWM_byte = (byte) PWM;


//            TextViewresponse.setText(S);
            myClientTask.execute();
        }
    };

    public class MyClientTask extends AsyncTask<Void, Void, Void>
    {
        // *** ZMIENNE
        String dstAddress;
        int dstPort;
        String response;

        // *** POBIERAM ZMIENNE
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
                // *** WYJEBAC POZA ASYNCTASCA...
                Socket socket = new Socket(dstAddress, dstPort);
                //    InputStream inputStream = socket.getInputStream();
                OutputStream byteArrayOutputStream = socket.getOutputStream();
//                ... DO TEGO MIEJSCA//
                byte[] buffer = new byte[1024];

                buffer[0] = (byte)PWM;
                byteArrayOutputStream.write(buffer);
                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1)
//                {
//                    byteArrayOutputStream.write(buffer, 0, );
//                }

                socket.close(); // na koniec appki wypierdolic
//                response = byteArrayOutputStream.toString("UTF-8");
            }
//            // *** POBIERAM, WYPISUJĘ
//            catch (UnknownHostException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        // *** ???
//        @Override
//        protected void onPostExecute(Void result)
//        {
//            textResponse.setText(" HELLO! ");
//            //textResponse.setText(response);
//            super.onPostExecute(result);
//        }
    }
}