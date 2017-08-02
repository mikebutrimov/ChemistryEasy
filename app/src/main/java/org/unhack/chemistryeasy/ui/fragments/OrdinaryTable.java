/*
 * Copyright ChemistryEasy Project (https://vk.com/chemistryeasyru)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.unhack.chemistryeasy.ui.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.unhack.chemistryeasy.BigViewController;
import org.unhack.chemistryeasy.R;
import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;
import org.unhack.chemistryeasy.events.TemperatureSlideEvent;
import org.unhack.chemistryeasy.ui.listeners.TempSeekBarListener;
import org.unhack.chemistryeasy.ui.popups.ElementPopUp;

import java.util.HashMap;

/**
 * Created by unhack on 7/26/17.
 */

public class OrdinaryTable extends PeriodicTableFragment implements iFragment, View.OnClickListener, View.OnLongClickListener {
    private View view;
    BigViewController big_view;
    SeekBar temp;
    int width,height,x_size,y_size;
    TextView temp_tx;

    static final int NORMAL_TEMPERATURE_K = 273;
    static final int X_CROP  = 18;
    static final int Y_CROP  = 12;
    static final int BV_X_SIZE = 10;
    static final int BV_Y_SIZE = 3;
    /** Elements */
    static final int ELEMENTS_MARGIN_TOP = 1;
    static final int ELEMENTS_MARGIN_BUTTOM = 1;
    static final int ELEMENTS_MARGIN_LEFT = 1;
    static final int ELEMENTS_MARGIN_RIGHT = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Container", "In onCreateView. Set for class: "+ this.toString() +" "+
                "Container id: " +this.container.toString());

        view = inflater.inflate(R.layout.ordinary_table_layout, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        x_size = (int) Math.floor((double)width / X_CROP);
        y_size = (int) Math.floor((double)height / Y_CROP);
        //calc of X margin
        int x_margin = (width - x_size*18) /2;
        view.setPadding(x_margin,x_margin,x_margin,x_margin);
        Ui_init(view);
        return view;
    }


    public void Ui_init(View v){
        big_view = new BigViewController(v.getContext());
        Space space = new Space(v.getContext());
        Space space2 = new Space(v.getContext());
        space2.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0,0), GridLayout.spec(12,5)));
        final GridLayout table = (GridLayout) v.findViewById(R.id.table_layout);
        GridLayout lantan = (GridLayout) v.findViewById(R.id.lantan);
        GridLayout.LayoutParams bigViewParams = new GridLayout.LayoutParams(GridLayout.spec(0,3), GridLayout.spec(2,10));
        bigViewParams.width = x_size*BV_X_SIZE;
        bigViewParams.height = y_size*BV_Y_SIZE;
        big_view.setLayoutParams(bigViewParams);
        big_view.setElementToView(1);

        for(int i = 0; i < this.container.getSize(); i++) {
            ChemElement buf = this.container.getElementByNumber(i + 1);

            buf.setSize(x_size - (ELEMENTS_MARGIN_LEFT + ELEMENTS_MARGIN_RIGHT), y_size - (ELEMENTS_MARGIN_TOP + ELEMENTS_MARGIN_BUTTOM));
            switch (buf.getElementNumber()){
                case 2:
                    table.addView(space);
                    table.addView(big_view);
                    table.addView(space2);
                    break;
            }
            if(buf.isLantanoid())
            {
                lantan.addView(buf);
            }
            else {
                table.addView(buf);
            }
            buf.setOnClickListener(this);
            buf.setOnLongClickListener(this);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) buf.getLayoutParams();
            params.setMargins(ELEMENTS_MARGIN_LEFT,ELEMENTS_MARGIN_TOP,ELEMENTS_MARGIN_RIGHT,ELEMENTS_MARGIN_BUTTOM);
            buf.setLayoutParams(params);
        }
        temp = (SeekBar) v.findViewById(R.id.temp);
        temp_tx = (TextView) v.findViewById(R.id.temp_tx);
        temp.setVisibility(View.INVISIBLE);
        temp_tx.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        ChemElement el = (ChemElement) v;
        ElementPopUp popUp = new ElementPopUp((ChemElement) v,getContext(),v);
        popUp.show();
    }

    @Override
    public boolean onLongClick(View v) {
        ChemElement el = (ChemElement) v;
        int num = el.getElementNumber();
        big_view.setElementToView(num);
        return true;
    }





}
