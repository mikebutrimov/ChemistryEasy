package org.unhack.chemistryeasy;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        try {
            db.createDataBase();
        }
        catch (IOException ex)
        {
            String ds = ex.toString();
            Log.d("Tab", ds);
        }
        db.openDataBase();

    }
}
