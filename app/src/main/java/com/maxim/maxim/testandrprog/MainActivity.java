package com.maxim.maxim.testandrprog;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private int type = 0;

    private String f = "";

    private String a = "";
    private String b = "";
    private String c = "";

    private String z = "";

    public void alertMessage(String messageContent) {
        Toast toast = Toast.makeText(getApplicationContext(), messageContent, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("StaticFieldLeak")
    private class JTask extends AsyncTask <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            if(type == 1) {
                try {
                    URL url = new URL("https://api-news-nin1.herokuapp.com/getOneObj/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) buffer.append(line);
                    JSONObject obj = new JSONObject(buffer.toString());
                    a = obj.getString("a");
                    b = obj.getString("b");
                    c = obj.getString("c");
                } catch (Exception e) {
                    // error
                }
            }

            if(type == 2) {
                try {
                    URL url = new URL("https://api-news-nin1.herokuapp.com/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) buffer.append(line);
                    JSONArray arr = new JSONArray(buffer.toString());
                    StringBuilder bigAnswer = new StringBuilder();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject element = arr.getJSONObject(i);
                        String x = element.getString("tema") + "\n" + element.getString("article") + "\n" + element.getString("content") + "\n\n";
                        bigAnswer.append(x);
                    }
                    z = bigAnswer.toString();

                } catch (Exception e) {
                    // error
                }
            }

            if(type == 3) {
                try {
                    URL url = new URL("https://api-news-nin1.herokuapp.com/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    MaximJsonMaker ppp = new MaximJsonMaker();
                    ppp.addElement("tema", f);
                    ppp.addElement("article", f + f);
                    ppp.addElement("content", f + f + f);
                    connection.getOutputStream().write(ppp.getMaximJsonObj().getBytes());
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) buffer.append(line);
                    JSONObject obj = new JSONObject(buffer.toString());
                    z = obj.getString("message");
                } catch (Exception e) {
                    // error
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(type == 1) {
                String message = a + " " + b + " " + c;
                alertMessage(message);
            }

            if(type == 2) {
                String message = z;
                alertMessage(message);
            }

            if(type == 3) {
                String message = z;
                alertMessage(message);
            }
        }
    }

    public void clickToBtn(View view) {
        type = 1;
        new JTask().execute();
    }

    public void pushToBtn(View view) {
        type = 2;
        new JTask().execute();
    }

    public void doToBtn(View view) {
        EditText eee = (EditText) findViewById(R.id.edittext_1);
        String fieldContent = eee.getText().toString();
        String emptyString = "";
        if(fieldContent.equals(emptyString)) {
            alertMessage("Поле ввода пусто.");
        } else {
            f = fieldContent;
            type = 3;
            new JTask().execute();
        }
    }
}
