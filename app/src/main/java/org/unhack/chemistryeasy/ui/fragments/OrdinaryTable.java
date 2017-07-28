package org.unhack.chemistryeasy.ui.fragments;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
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
    BigViewController big_view;
    SeekBar temp;
    int width,height,x_size,y_size;
    TextView temp_tx;


    private static final int X_CROP  = 18;
    private static final int Y_CROP  = 12;
    private static final int BV_X_SIZE = 10;
    private static final int BV_Y_SIZE = 3;
    /** Elements */
    private static final int ELEMENTS_MARGIN_TOP = 1;
    private static final int ELEMENTS_MARGIN_BUTTOM = 1;
    private static final int ELEMENTS_MARGIN_LEFT = 1;
    private static final int ELEMENTS_MARGIN_RIGHT = 1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ordinary_table_layout, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        x_size = (int) Math.floor((double)width / X_CROP);
        y_size = (int) Math.floor((double)height / Y_CROP);
        //calc of X margin
        int x_margin = (width - x_size*18) /2;
        //final View mView = view.findViewById(android.R.id.content);
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
        /** Test Filter */
        int r[] = {2,3,5,6,7,8,9,11,12};
        HashMap el = this.container.getFilteredElements(r);
        for(int i = 0; i < el.size(); i++) {
            ChemElement s = (ChemElement) el.get(r[i]);
            //s.setBackgroundColor(Color.RED);
        }


        temp = (SeekBar) v.findViewById(R.id.temp);
        temp.setOnSeekBarChangeListener(new TempSeekBarListener(temp));
        temp.setProgress(273);
        temp_tx = (TextView) v.findViewById(R.id.temp_tx);
    }



    @Override
    public void onClick(View v) {
        ChemElement el = (ChemElement) v;
        ElementPopUp popUp = new ElementPopUp((ChemElement) v,getContext(),v);
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


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TemperatureSlideEvent event) {
        this.container.getStateInTemp(event.temperature);
        temp_tx.setText(String.valueOf(event.temperature));
    }


}
