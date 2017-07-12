package org.unhack.chemistryeasy.elements;

import android.content.Context;
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
     * This methods inits elemets database from sqllite database
     *
     * @param context - Application context to know where to find DataBase
     *
     */
    public void initFromDb(Context context) {
        this.storage.clear();
        DataBaseHelper db = new DataBaseHelper(context);
        ArrayList<String[]> elems = db.getAllElementsFromDB();
        /**
         *  0 - Atomic number   (int)
         *  1 - Symbol          (string)
         *  2 - English_name    (string)
         *  3 - Russian_name    (string)
         *  4 - Atomic_weight   (float)
         *  5 - Block_name      (string)
         *  6 - Family          (int)
         *  7 - Radioactive     (int)
         *  8 - Melting_point   (float)
         *  9 - Boiling_point   (float)
         *  10 - discovery_year (int)
         */
        for (String[] rec : elems) {
            ChemElement bufElem = null;
            try {
                bufElem = new ChemElement(Integer.parseInt(rec[0]),String.valueOf(rec[1]),
                        String.valueOf(rec[2]), Float.parseFloat(rec[4]), Boolean.valueOf(rec[7]));
                }
            catch (NumberFormatException e){
                String sWeight = rec[4];
                Pattern mPattern = Pattern.compile("\\d+(.{1}\\d+)");
                Matcher mMatcher = mPattern.matcher(sWeight);
                mMatcher.find();
                try {
                    bufElem = new ChemElement(Integer.parseInt(rec[0]),String.valueOf(rec[1]),
                            String.valueOf(rec[2]), Float.parseFloat(mMatcher.group()), Boolean.valueOf(rec[7]));
                }
                catch (Exception ee){
                    ee.printStackTrace();
                }
            }
            if (bufElem != null) {
                if (rec[8] != null) bufElem.setMeltingPoint(Float.valueOf(rec[8]));
                if (rec[9] != null) bufElem.setBoilingPoint(Float.valueOf(rec[9]));
                if (rec[5] != null) bufElem.setBlockName(String.valueOf(rec[5]));
                if (rec[7] != null) bufElem.setRadioactive(Boolean.valueOf(rec[7]));
                if (rec[6] != null) bufElem.setFamily(Integer.parseInt(rec[6]));
                if (rec[10] != null) bufElem.setDiscoveryYear(Integer.parseInt(rec[10]));
                this.storage.put(bufElem.getElementNumber(), bufElem);
            }
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


    public HashMap<Integer, ChemElement> getFilteredElements(int[] filter){
        HashMap<Integer, ChemElement> result = new HashMap<>();
        for (int elemNumber: filter){
            try {
                result.put(elemNumber, this.storage.get(elemNumber));
            }
            catch (NullPointerException npe){
                //filter contains elem number but container storage does not
                //need to think out this problem
                npe.printStackTrace();
            }
        }
        return result;
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
