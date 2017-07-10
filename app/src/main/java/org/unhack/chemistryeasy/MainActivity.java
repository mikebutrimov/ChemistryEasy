package org.unhack.chemistryeasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.unhack.chemistryeasy.db.DataBaseHelper;
import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

        //Example how to use chemelem and chemelemcontainer

        ChemElementContainer allElemenets = new ChemElementContainer();
        //init container from db
        allElemenets.initFromDb(getApplicationContext());
        //get an elemnt
        ChemElement mElement = allElemenets.getElementByNumber(1);
        Log.d("mElement: ", mElement.getElementName() + " " + mElement.getElementSymbol());

        //custom container
        ChemElementContainer mCustomContainer = new ChemElementContainer();
        mCustomContainer.putElement(mElement);
        mCustomContainer.putElement(allElemenets.getElementByNumber(2));

        HashMap<Integer, ChemElement> elemsFromCustomContainer = mCustomContainer.getAll();

        Log.d("Elems from custom ", elemsFromCustomContainer.toString());



    }
}
