package org.unhack.chemistryeasy.elements;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.unhack.chemistryeasy.BigViewController;
import org.unhack.chemistryeasy.R;
import org.unhack.chemistryeasy.mutators.iMutator;

/**
*   Constructor - number, symbol, english_name, atomic_weight, radioactive;
*   SET / GET - block, family, melting_point, boiling_point, discovery_year with check for null
*/




public class ChemElement extends RelativeLayout implements iChemElement {
    /** UI */
    TextView symbol_view;
    TextView number_view;
    TextView mass_view;
    TextView first_name_view;
    TextView second_name_view;
    RelativeLayout element_box;
    /***/

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
     *
     */
    public ChemElement(Context context,int number, String symbol, String name, float atomic_weight, boolean radioactive){
        super(context);
        init();
        this.number = number;number_view.setText(String.valueOf(number));
        this.symbol = symbol;symbol_view.setText(symbol);
        this.atomic_weight = atomic_weight;mass_view.setText(String.valueOf(atomic_weight));
        this.native_name = name;first_name_view.setText(name);
        this.radioactive = radioactive;
    }
    public ChemElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChemElement(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.element_layout, this);
        symbol_view = (TextView) findViewById(R.id.symbol);
        number_view = (TextView) findViewById(R.id.number);
        mass_view = (TextView) findViewById(R.id.mass);
        first_name_view = (TextView) findViewById(R.id.first_name);
        second_name_view = (TextView) findViewById(R.id.second_name);
        element_box = (RelativeLayout) findViewById(R.id.element_box);
        /** Radioactive and S,P,D,F */
        if (this.radioactive)
        {
            // radioactive
        }
    }

    /** Number */
    public int getElementNumber(){
        return this.number;
    }
    public void setElementNumber(int number) { this.number = number; number_view.setText(String.valueOf(number));}

    /** Symbol */
    public String getElementSymbol(){return this.symbol;}
    public void setElementSymbol(String symbol) {this.symbol = symbol;symbol_view.setText(symbol);}

    /** Atomic Weight */
    public float getAtomicWeight(){return this.atomic_weight;}
    public void setAtomic_weight(Float atomic_weight){this.atomic_weight = atomic_weight;mass_view.setText(String.valueOf(atomic_weight));}

    /** Element Native Name */
    public String getElementNativeName(){return this.native_name;}
    public void setNativeName(String name) {this.native_name = name; first_name_view.setText(name);}

    /** Block */
    public String getBlockName(){return this.block;}
    public void setBlockName(String block) {this.block = block;}
    public void colorise()
    {
        switch (block) {
            case "s":
                element_box.setBackgroundColor(Color.parseColor("#E87891"));
                break;
            case "p":
                element_box.setBackgroundColor(Color.parseColor("#F5DA67"));
                break;
            case "d":
                element_box.setBackgroundColor(Color.parseColor("#4EBDD1"));
                break;
            case "f":
                element_box.setBackgroundColor(Color.parseColor("#ABD3AE"));
                break;
            default:
                element_box.setBackgroundColor(Color.parseColor("#6b6b6b"));
                return;
        }
    }

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

    /** Size */
    public int getBoxWidth() {return element_box.getWidth();}
    public int getBoxHeight() {return element_box.getHeight();}
    public void setSize(int width, int height) {element_box.setLayoutParams(new LayoutParams(width, height));}

    /** Color */
    public void setColor(String color){element_box.setBackgroundColor(Color.parseColor(color));}


    /** Lantanoid */
    public boolean isLantanoid()
    {
        if(this.getElementNumber() >= 58 && this.getElementNumber() <= 71 || this.getElementNumber() >= 90 && this.getElementNumber() <= 103)
        {return true;}
        else{return false;}
    }

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
