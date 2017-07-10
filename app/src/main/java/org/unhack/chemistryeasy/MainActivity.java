package org.unhack.chemistryeasy;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

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
        ArrayList<String[]> allElements = db.getAllElementsFromDB();
        //debug Log.d output to check correct db reading
        for (String[] rec: allElements){
            Log.d("ELEMENTS:" , TextUtils.join(" ", rec));
        }
        //

    }
}
