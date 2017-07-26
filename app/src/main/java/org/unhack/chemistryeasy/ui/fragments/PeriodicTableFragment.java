package org.unhack.chemistryeasy.ui.fragments;

import android.support.v4.app.Fragment;

import org.unhack.chemistryeasy.elements.ChemElementContainer;

/**
 * Created by unhack on 7/26/17.
 */

public class PeriodicTableFragment extends Fragment implements iFragment {
    private int id;
    public ChemElementContainer container;


    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getTabId(){
        return this.id;
    }

    public void setContainer(ChemElementContainer container){
        this.container = container;
    }


}
