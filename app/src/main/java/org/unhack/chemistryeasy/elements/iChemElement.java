package org.unhack.chemistryeasy.elements;

import org.unhack.chemistryeasy.mutators.iMutator;

/**
 * Interface for Chemistry Element
 *
 */

public interface iChemElement {
    //standart getters

    /**
     *
     * @return element name as String
     */
    String getElementName();

    /**
     *
     * @return element number as int in periodic table
     */
    int getElementNumber();

    /**
     *
     * @return atomic mass as float
     */
    float getAtomicMass();

    /**
     *
     * @return element's block as String (S,P,D,F)
     */
    String getBlockName();

    //Some othe getters shoul be there, these ones ar as part of an example.
    //


    //standart setters
    //base params are setted by class constructor or fabric

    /**
     *
     * @param mutator - a Chemistry Element Mutator class instance that describes
     *                spesific permutations for an element. Like reaction on temperatures
     *                or pressure changes. This part is an example. Need to do some research
     *                on best architecture
     *
     */
    void setMutator(iMutator mutator);

    //statefull methods

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
