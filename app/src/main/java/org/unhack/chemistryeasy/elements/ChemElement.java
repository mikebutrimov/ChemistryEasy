package org.unhack.chemistryeasy.elements;


import org.unhack.chemistryeasy.mutators.iMutator;

public class ChemElement implements iChemElement {
    private int number;
    private String symbol;
    private String name;
    private String block;
    private float atomic_weight;
    private iMutator mutator;
    /**
     * Default constructor
     * with only 3 params
     *
     */
    public ChemElement(int number, String symbol, float atomic_weight){
        this.number = number;
        this.symbol = symbol;
        this.atomic_weight = atomic_weight;
    }

    //standart getters

    public int getElementNumber(){
        return this.number;
    }

    public String getElementSymbol(){
        if (this.symbol.isEmpty()) return null;
        return this.symbol;
    }

    public float getAtomicWeight(){
        return this.atomic_weight;
    }

    public String getElementName(){
        if (this.name.isEmpty()) return null;
        return this.name;
    }

    public String getBlockName(){
        if (this.block.isEmpty()) return null;
        return this.block;
    }



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

}
