package org.unhack.chemistryeasy;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TableRow;
import android.widget.TextView;

import org.unhack.chemistryeasy.db.DataBaseHelper;
import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;
import org.unhack.chemistryeasy.ui.popup.ElementPopUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    BigViewController big_view;
    ChemElementContainer allElementsContainer;
    SeekBar temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        if (db.isValid) {
            db.openDataBase();
        }
        else {
            Log.d("DB", "problem, db was not inited well");
        }
        Ui_init();
    }

    /** --------------- UI ----------- */
    public void Ui_init(){
        allElementsContainer = new ChemElementContainer(getApplicationContext());
        allElementsContainer.initFromDb(getApplicationContext());
        //allElementsContainer.getStateInTemp(51);
        big_view = new BigViewController(getApplicationContext());
        //big_view.setElementToView(10);
        big_view.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0,3), GridLayout.spec(2,10)));

        Space space = new Space(getApplicationContext());
        Space space2 = new Space(getApplicationContext());
        space2.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0,0), GridLayout.spec(12,5)));
        GridLayout table = (GridLayout) findViewById(R.id.table_layout);
        GridLayout lantan = (GridLayout) findViewById(R.id.lantan);

        for(int i = 0; i < allElementsContainer.getSize(); i++) {
            ChemElement buf = allElementsContainer.getElementByNumber(i + 1);
            buf.setOnClickListener(this);
            buf.setOnLongClickListener(this);
            switch (buf.getElementNumber()){
                case 2:
                    table.addView(space);
                    table.addView(big_view);
                    table.addView(space2);
                    break;
            }

            if(buf.getElementNumber() >= 58 && buf.getElementNumber() <= 71)
            {
                lantan.addView(buf);
            } else if(buf.getElementNumber() >= 90 && buf.getElementNumber() <= 103){
                lantan.addView(buf);
            }
            else {
                table.addView(buf);
            }
        }
        /** Test Filter */
        int r[] = {2,3,5,6,7,8,9,11,12};
        HashMap el = allElementsContainer.getFilteredElements(r);
        for(int i = 0; i < el.size(); i++) {
            ChemElement s = (ChemElement) el.get(r[i]);
            //s.setBackgroundColor(Color.RED);
        }
        temp = (SeekBar) findViewById(R.id.temp);



    }
    @Override
    public void onClick(View v) {
        allElementsContainer.getStateInTemp(temp.getProgress());
        ChemElement el = (ChemElement) v;
        ElementPopUp popUp = new ElementPopUp((ChemElement) v,getApplicationContext(),v);
        popUp.show();
        Log.d("ELEMENT", ((ChemElement) v).getElementNativeName());
    }

    @Override
    public boolean onLongClick(View v) {

        ChemElement el = (ChemElement) v;
        int num = el.getElementNumber();
        big_view.setElementToView(num);
        return true;
    }
}
