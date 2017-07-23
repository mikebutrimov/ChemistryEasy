package org.unhack.chemistryeasy.elements;

import org.unhack.chemistryeasy.mutators.iMutator;

/**
 * Interface for Chemistry Element
 *
 */

public interface iChemElement {
    /** Standart Getters */
    //region Getters
    int getBoxHeight();
    int getBoxWidth();
    String getElementNativeName();
    int getElementNumber();
    float getAtomicWeight();
    boolean isRadioactive();
    boolean isLantanoid();
    String getElementSymbol();
    int getFamily();
    float getMeltingPoint();
    float getBoilingPoint();
    int getDiscoveryYear();
    String getBlockName(); /** S, P , D , F */
    //endregion


    /**
     *
     * @param mutator - a Chemistry Element Mutator class instance that describes
     *                spesific permutations for an element. Like reaction on temperatures
     *                or pressure changes. This part is an example. Need to do some research
     *                on best architecture
     *
     */


    /** Standart Setters */
    //region Setters
    void setMutator(iMutator mutator);
    void setNativeName(String name);
    void setRadioactive(boolean radioactive);
    void setMeltingPoint(float point);
    void setBoilingPoint(float point);
    void setDiscoveryYear(int year);
    void setFamily(int family);
    void setSize(int width, int height);
    void colorise();
    //endregion

    /**
     * This method is used to invo
     * ke mutation on element
     *
     * @return True if mutation was a success, otherwise false
     *
     * fires mutate() method of mutator if was set.
     *
     */
    boolean doMutation();
}
