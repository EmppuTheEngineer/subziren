package com.example.subziren.subziren;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my);
           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            Thread thread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                           final String temp = GetTemperature();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String text = temp;
                                    if(temp == null)
                                    {
                                        text = "Nope";
                                    }
                                    TextView textv = (TextView)findViewById(R.id.textView);
                                    textv.setText(text);
                                }
                            });
                        }
                    }
            );
            thread.start();

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static String GetTemperature()
    {
        String result = null;
        try {
            // Create a URL for the desired page
           /* URL url = new URL("http://canihazip.com/s");

            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)
            }

            in.close();
            return str;*/
            HttpURLConnection urlConnection = null; //Luo objekti, jolla haetaan informaatio webistä(sisäänrakennettu)

                URL url = new URL("https://subziren.blob.core.windows.net/temperature/temperature"); //Luo URL objekti johon tallennetaan nettisivun osoite
                urlConnection = (HttpURLConnection) url.openConnection(); //Avaa yhteys
                InputStream in = new BufferedInputStream(urlConnection.getInputStream()); //Hae webbi sivun palauttamat tiedot yhteyden striimistä
                InputStreamReader is = new InputStreamReader(in); //Luo uudet striimi tyypit sen lukemisen helpottamiseksi. Haluamme muuttaa tiedot Stringiksi
                final StringBuilder sb=new StringBuilder();
                BufferedReader br = new BufferedReader(is);
                String read = br.readLine();
                while(read != null) { //Luetaan striimiä tavu tavulta ja lisätään ne StringBuilder objektiin.
                    System.out.println(read);
                    sb.append(read);
                    read =br.readLine();
                }
                result = sb.toString();
        } catch (MalformedURLException e) {
            Log.d("ErrorURL:",e.getMessage(),e);
        } catch (IOException e) {
            Log.d("ErrorIO:",e.getMessage(),e);
        }
        catch(Exception e)
        {
            Log.d("Error Common: ",e.getMessage(),e);
        }
        return result;
    }
}
