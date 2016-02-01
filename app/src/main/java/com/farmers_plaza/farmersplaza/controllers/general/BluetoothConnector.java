package com.farmers_plaza.farmersplaza.controllers.general;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BluetoothConnector extends AsyncTask<BluetoothDevice, Void, Void> {
    private InputStream inputStream;
    private BluetoothSocket socket;
    private ProgressDialogPrompt progressDialogPrompt;
    private byte[] buffer = new byte[0];
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder = new StringBuilder();
    private String line;
    private UUID uuid;
    private int counter = 0;
    private Context context;
    private List<String> listValues = new ArrayList<>();
    private int intMoisture;
    private int intSunlight;
    private int intSoilPh;
    private double convertedPh;
    private int intTemperature;
    private boolean isError;

    public BluetoothConnector(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialogPrompt = new ProgressDialogPrompt(this.context);
        progressDialogPrompt.showProgress("Collecting Results...");
    }

    @Override
    protected Void doInBackground(BluetoothDevice... devices) {
        uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Standard UUID
        try {
            socket = devices[0].createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            if (socket.isConnected()) {
                Log.e("SOCKET CONNECTIVITY", "Connected");
                inputStream = socket.getInputStream();
                inputStream.read(buffer);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    if(counter < 1) {
                        counter++;
                    } else {
                        break;
                    }
                }
                bufferedReader.close();
                socket.close();
                recordTest();
            } else {
                Log.e("SOCKET CONNECTIVITY", "Not Connected");
            }
        } catch (IOException e) {
            isError = true;
            Log.e("SOCKET", e.getMessage());
        }

        return null;
    }

    private void recordTest() {
            listValues = Arrays.asList(stringBuilder.toString().split(",[ ]*"));
            for (int i = 0; i < listValues.size(); i++) {
                switch (i) {
                    case 0:
                        intMoisture = Integer.parseInt(listValues.get(i).replaceAll("\\D", ""));
                        break;
                    case 1:
                        intSunlight = Integer.parseInt(listValues.get(i).replaceAll("\\D", ""));
                        break;
                    case 2:
                        intSoilPh = Integer.parseInt(listValues.get(i).replaceAll("\\D", ""));
                        break;
                    case 3:
                        intTemperature = Integer.parseInt(listValues.get(i).replaceAll("\\D", ""));
                        break;
                }
            }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialogPrompt.stopProgress();
        if(isError) {
            Toast.makeText(context,
                    "Request timed out. Please check your device if connected and try again",
                    Toast.LENGTH_LONG).show();
        } else {
            showResult();
        }
    }

    private void showResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Soil Analysis Result");
        builder.setMessage(String.format("Moisture: %d\nSunlight: %d\nSoil pH: %d\n" +
                "Temperature: %d", intMoisture, intSunlight, intSoilPh, intTemperature));
        builder.show();
        convertPh();
    }

    private void convertPh() {
        if(intSoilPh <= 43) {
            convertedPh = 0;
        } else if(intSoilPh <= 62) {
            convertedPh = 3.5;
        } else if(intSoilPh <= 141) {
            convertedPh = 4;
        } else if(intSoilPh <= 361) {
            convertedPh = 5;
        } else if(intSoilPh <= 405) {
            convertedPh = 5.5;
        } else if(intSoilPh <= 444) {
            convertedPh = 6.0;
        } else if(intSoilPh <= 479) {
            convertedPh = 6.5;
        } else if(intSoilPh <= 515) {
            convertedPh = 7;
        } else if(intSoilPh <= 543) {
            convertedPh = 7.5;
        } else if(intSoilPh <= 581) {
            convertedPh = 8;
        } else if(intSoilPh <= 625) {
            convertedPh = 8.5;
        } else if(intSoilPh <= 699) {
            convertedPh = 9;
        } else {
            convertedPh = 10;
        }
    }

    public String getConvertedPH() {
        return Double.toString(convertedPh);
    }


}
