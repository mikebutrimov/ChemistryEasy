package org.unhack.chemistryeasy.elements;


import org.unhack.chemistryeasy.mutators.iMutator;

/**
*   Constructor - number, symbol, english_name, atomic_weight, radioactive;
*   SET / GET - block, family, melting_point, boiling_point, discovery_year with check for null
*/




public class ChemElement implements iChemElement {
    private int number;
    private int family;
    private int discovery_year;
    private float melting_point;
    private float boiling_point;
    private String symbol;
    private String native_name;
    private String block;
    private float atomic_weight;
    private boolean radioactive;
    private iMutator mutator;

    /**
     * Default constructor
     * with only 3 params
     *
     */
    public ChemElement(int number, String symbol, String name, float atomic_weight, boolean radioactive){
        this.number = number;
        this.symbol = symbol;
        this.atomic_weight = atomic_weight;
        this.native_name = name;
        this.radioactive = radioactive;
    }

    /** Number */
    public int getElementNumber(){
        return this.number;
    }
    public void setElementNumber(int number) { this.number = number; }

    /** Symbol */
    public String getElementSymbol(){return this.symbol;}
    public void setElementSymbol(String symbol) {this.symbol = symbol;}

    /** Atomic Weight */
    public float getAtomicWeight(){return this.atomic_weight;}
    public void setAtomic_weight(Float atomic_weight){this.atomic_weight = atomic_weight;}

    /** Element Native Name */
    public String getElementNativeName(){return this.native_name;}
    public void setNativeName(String name) {this.native_name = name;}

    /** Block */
    public String getBlockName(){
        //if (this.block.isEmpty()) return null;
        return this.block;
    }
    public void setBlockName(String block) {this.block = block;}

    /** Radioactive */
    public boolean isRadioactive() {return this.radioactive;}
    public void setRadioactive(boolean radioactive) {this.radioactive = radioactive;}

    /** Melting_point */
    public float getMeltingPoint() {return this.melting_point;}
    public void setMeltingPoint(float point) {this.melting_point = point;}

    /** Boiling_point */
    public float getBoilingPoint() {return this.boiling_point;}
    public void setBoilingPoint(float point) {this.boiling_point = point;}

    /**Year */
    public int getDiscoveryYear() {return this.discovery_year;}
    public void setDiscoveryYear(int year) {this.discovery_year = year;}

    /**Family */
    public int getFamily() {return this.family;}
    public void setFamily(int family) {this.family = family;}

    //region Mutator
    public void setMutator(iMutator mutator){
        this.mutator = mutator;
    }

    public boolean doMutation(){
        //check if mutator is set
        if (this.mutator == null) {
            return true; // no need to perform mutation
        }
        else {
            //Do what ever you want with mutator
        }
        return false;
    }
    //endregion
}
