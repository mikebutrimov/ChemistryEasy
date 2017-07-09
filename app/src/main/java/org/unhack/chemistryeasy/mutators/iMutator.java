package org.unhack.chemistryeasy.mutators;

import android.os.Bundle;

import org.unhack.chemistryeasy.elements.iChemElement;

/**
 *  Interface for mutator class
 *
 */

public interface iMutator {
    /**
     *
     * @param elem - ChemElement to get info from
     * @return true if mutation was a success
     */
    boolean mutate(iChemElement elem);

}
