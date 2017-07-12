package org.unhack.chemistryeasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

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

        if (db.isValid) {
            db.openDataBase();
        }
        else {
            Log.d("DB", "problem, db was not inited well");
        }
        ArrayList<String[]> allElements = db.getAllElementsFromDB();
        //debug Log.d output to check correct db reading
        for (String[] rec: allElements){
            Log.d("ELEMENTS:" , TextUtils.join(" ", rec));
        }
        //

        //Example how to use chemelem and chemelemcontainer

        ChemElementContainer allElementsContainer = new ChemElementContainer(getApplicationContext());
        //init container from db
        allElementsContainer.initFromDb(getApplicationContext());

        //get elems by filter
        int[] filter = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        HashMap<Integer, ChemElement> filterdElems = allElementsContainer.getFilteredElements(filter);
        Log.d("Elems from filter ", filterdElems.toString());


        /** ----------UI-----------*/
        GridLayout grid = (GridLayout) findViewById(R.id.la);
        HashMap all = allElementsContainer.getAll();
        for (int i = 1; i < all.size(); i++)
        {
            ChemElement elem = allElementsContainer.getAll().get(i);
            grid.addView(elem);
        }
        /** --------------------------*/

        //get an element
        ChemElement mElement = allElementsContainer.getElementByNumber(2);
        Log.d("Element", "Number " + mElement.getElementNumber());
        Log.d("Element", "Symbol " + mElement.getElementSymbol());
        Log.d("Element", "Atomic weight " + mElement.getAtomicWeight());
        Log.d("Element", "Name " + mElement.getElementNativeName());
        Log.d("Element", "Block " + mElement.getBlockName());
        Log.d("Element", "Family " + mElement.getFamily());
        Log.d("Element", "Radioactive " + mElement.isRadioactive());
        Log.d("Element", "Melting Point " + mElement.getMeltingPoint());
        Log.d("Element", "Boiling Point " + mElement.getBoilingPoint());
        Log.d("Element", "Year " + mElement.getDiscoveryYear());

        //custom container
        ChemElementContainer mCustomContainer = new ChemElementContainer(getApplicationContext());
        mCustomContainer.putElement(mElement);
        mCustomContainer.putElement(allElementsContainer.getElementByNumber(2));

        HashMap<Integer, ChemElement> elemsFromCustomContainer = mCustomContainer.getAll();

        Log.d("Elems from custom ", elemsFromCustomContainer.toString());

    }
}
