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

    /**
     * This methods inits elemets database from sqllite datatbase
     *
     * @param context - Application context to know where to find DataBase
     *
     */
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
                String sWeight = rec[4];
                Pattern mPattern = Pattern.compile("\\d+(.{1}\\d+)");
                Matcher mMatcher = mPattern.matcher(sWeight);
                mMatcher.find();
                try {
                    bufElem = new ChemElement(Integer.parseInt(rec[0]),
                            String.valueOf(rec[1]), Float.parseFloat(mMatcher.group()));
                }
                catch (Exception ee){
                    ee.printStackTrace();
                }
            }
            if (bufElem != null) this.storage.put(bufElem.getElementNumber(), bufElem);
        }
    }

    /**
     *
     * @param number - number of element in periodic table
     * @return ChemElement instance
     */
    public ChemElement getElementByNumber(int number){
        return this.storage.get(number);
    }

    /**
     *
     * @return all elements in container
     */
    public HashMap<Integer, ChemElement> getAll(){
        return this.storage;
    }

    /**
     *
     * @param elem - element to put into container
     */
    public void putElement(ChemElement elem){
        this.storage.put(elem.getElementNumber(), elem);
    }

}
