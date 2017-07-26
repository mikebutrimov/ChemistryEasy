package org.unhack.chemistryeasy.ui.fragments;

import org.unhack.chemistryeasy.elements.ChemElementContainer;

/**
 * Created by unhack on 7/26/17.
 */

public interface iFragment {
    void setId(int id);
    int getTabId();
    void setContainer (ChemElementContainer container);
}
