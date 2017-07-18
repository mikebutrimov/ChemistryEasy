package org.unhack.chemistryeasy;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;


/**
 * Created by XXX on 18.07.2017.
 */

public class BigViewController extends CardView {
    TextView symbol,number,mass,native_name,second_name;
    CardView big_view_layout;
    ChemElementContainer container;

    public BigViewController(Context context) {
        super(context);
        init();
    }
    private void init()
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.big_view_element, this);
        symbol = (TextView)findViewById(R.id.big_symbol);
        number = (TextView) findViewById(R.id.big_number);
        big_view_layout = (CardView) findViewById(R.id.big_view);
        mass = (TextView) findViewById(R.id.big_mass);
        native_name = (TextView) findViewById(R.id.big_native_name);
        second_name = (TextView) findViewById(R.id.second_name);
        container = new ChemElementContainer(getContext());
        container.initFromDb(getContext());
        start_page();
    }
    private void start_page()
    {
        // Start page here.
    }

    public void setElementToView(int element_number)
    {
        ChemElement el = container.getElementByNumber(element_number);
        symbol.setText(el.getElementSymbol());
        number.setText(String.valueOf(el.getElementNumber()));
        mass.setText(String.valueOf(el.getAtomicWeight()));
        native_name.setText(el.getElementNativeName());
        // second name

    }
    public BigViewController get(){return this;}
}
