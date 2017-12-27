package com.example.simon.quicknotes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;
import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//Simon Chiu
public class MainActivity extends AppCompatActivity {

    EditText Notes;
    TextView Last_Update_Info;
    SaveData saveData;
    String DateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declarations
        Notes = (EditText)findViewById(R.id.Notes);
        Last_Update_Info = (TextView) findViewById(R.id.Last_Update_Info);

        //Set Time and Date
        DateTime = DateFormat.getDateTimeInstance().format(new Date());
        Last_Update_Info.setText(DateTime);
        //Make Notes scrollable
        Notes.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    protected void onResume() {
        super.onStart();
        saveData=loadFile();
        if (saveData != null) {
            Last_Update_Info.setText(saveData.getLast_Update_Info());
            Notes.setText(saveData.getNotes());
        }
        else
            DateTime = DateFormat.getDateTimeInstance().format(new Date());
        Last_Update_Info.setText(DateTime);
    }

    public SaveData loadFile(){
    saveData=new SaveData();
        try{
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            JsonReader reader = new JsonReader(new InputStreamReader(is, getString(R.string.encoding)));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(Integer.toString(R.string.Last_Update_Info))) {
                    saveData.setLast_Update_Info(reader.nextString());
                } else if (name.equals(Integer.toString(R.string.Notes))) {
                    saveData.setNotes(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveData;

        }

    @Override
    protected void onPause() {
        super.onPause();
        //If notes have not changed, keep the date and time, else set to current date and time
        DateTime = DateFormat.getDateTimeInstance().format(new Date());
        saveData=loadFile();
        if (saveData != null) {
           saveData.setLast_Update_Info(Last_Update_Info.getText().toString());

          /* if (saveData.getNotes().equals(Notes.getText().toString())) {
                saveData.setLast_Update_Info(Last_Update_Info.getText().toString());
            }
            else
                saveData.setLast_Update_Info(DateTime);
    */
       }
       else{
            saveData.setLast_Update_Info(DateTime);
    }

        saveData.setNotes(Notes.getText().toString());
        saveSaveData();
    }


    private void saveSaveData() {

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name(Integer.toString(R.string.Last_Update_Info)).value(saveData.getLast_Update_Info());
            writer.name(Integer.toString(R.string.Notes)).value(saveData.getNotes());
            writer.endObject();
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}


