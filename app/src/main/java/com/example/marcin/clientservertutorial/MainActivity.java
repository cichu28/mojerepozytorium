package com.example.marcin.clientservertutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//package com.example.androidclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView textResponse, TextViewresponse;
    EditText editTextAddress, editTextPort, editTextPWM_value;
    Button buttonConnect, buttonClear;
    //Button radioButtonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPWM_value  = (EditText)findViewById(R.id.PWM_value);
        TextViewresponse = (TextView)findViewById(R.id.response);

        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonClear = (Button)findViewById(R.id.clear);
        textResponse = (TextView)findViewById(R.id.response);
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
    }

    public void EdytujTekst(View view)
    {
        String S = "PWM: " + editTextPWM_value.getText().toString();
        TextViewresponse.setText(S);
    }

    // *** PRZYCISK "NASŁUCHUJE"
    OnClickListener buttonConnectOnClickListener = new OnClickListener()
    {
        @Override
        public void onClick(View arg0)
        {
                // You have to verify editTextAddress and editTextPort are input as correct format.
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
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
                Socket socket = new Socket(dstAddress, dstPort);
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1)
//                {
//                    byteArrayOutputStream.write(buffer, 0, bytesRead);
//                }

                socket.close();
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
