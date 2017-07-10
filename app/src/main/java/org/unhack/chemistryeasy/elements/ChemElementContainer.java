package org.unhack.chemistryeasy.elements;

import android.content.Context;
import android.util.Log;

import org.unhack.chemistryeasy.db.DataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class to manipulate chem elements
 */

public class ChemElementContainer {
    private HashMap<Integer, ChemElement> storage = new HashMap<>();


    public void initFromDb(Context context) {
        this.storage.clear();
        DataBaseHelper db = new DataBaseHelper(context);
        ArrayList<String[]> elems = db.getAllElementsFromDB();
        for (String[] rec : elems) {
            ChemElement bufElem = null;
            try {
                bufElem = new ChemElement(Integer.parseInt(rec[0]),
                        String.valueOf(rec[1]), Float.parseFloat(rec[4]));
                }
            catch (NumberFormatException e){
                float weight;
                String sWeight = rec[4];
                Pattern mPattern = Pattern.compile("\\d+(.{1}\\d+)");
                Matcher mMatcher = mPattern.matcher(sWeight);
                mMatcher.find();
                try {
                    bufElem = new ChemElement(Integer.parseInt(rec[0]),
                            String.valueOf(rec[1]), Float.parseFloat(mMatcher.group()));
                }
                catch (Exception ee){
                }
            }
            if (bufElem != null) this.storage.put(bufElem.getElementNumber(), bufElem);
        }
    }

    public ChemElement getElementByNumber(int number){
        return this.storage.get(number);
    }

    public HashMap<Integer, ChemElement> getAll(){
        return this.storage;
    }

    public void putElement(ChemElement elem){
        this.storage.put(elem.getElementNumber(), elem);
    }

}
