package org.unhack.chemistryeasy;

import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.SeekBar;
import android.widget.Space;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.unhack.chemistryeasy.db.DataBaseHelper;
import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;
import org.unhack.chemistryeasy.events.TemperatureSlideEvent;
import org.unhack.chemistryeasy.ui.listeners.TempSeekBarListener;
import org.unhack.chemistryeasy.ui.popups.ElementPopUp;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    BigViewController big_view;
    ChemElementContainer allElementsContainer;
    SeekBar temp;
    int width,height,x_size,y_size;

    private static final int X_CROP  = 18;
    private static final int Y_CROP  = 12;
    private static final int BV_X_SIZE = 10;
    private static final int BV_Y_SIZE = 3;


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Get screen size
         */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        x_size = (int) Math.floor((double)width / X_CROP);
        y_size = (int) Math.floor((double)height / Y_CROP);
        //calc of X margin
        int x_margin = (width - x_size*18) /2;
        View mView = findViewById(android.R.id.content);
        mView.setPadding(x_margin,x_margin,x_margin,x_margin);

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
        big_view = new BigViewController(getApplicationContext());

        Space space = new Space(getApplicationContext());
        Space space2 = new Space(getApplicationContext());
        space2.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0,0), GridLayout.spec(12,5)));
        GridLayout table = (GridLayout) findViewById(R.id.table_layout);
        GridLayout lantan = (GridLayout) findViewById(R.id.lantan);

        GridLayout.LayoutParams bigViewParams = new GridLayout.LayoutParams(GridLayout.spec(0,3), GridLayout.spec(2,10));
        bigViewParams.width = x_size*BV_X_SIZE;
        bigViewParams.height = y_size*BV_Y_SIZE;
        big_view.setLayoutParams(bigViewParams);

        for(int i = 0; i < allElementsContainer.getSize(); i++) {
            ChemElement buf = allElementsContainer.getElementByNumber(i + 1);
            buf.setSize(x_size, y_size);

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
            buf.setOnClickListener(this);
            buf.setOnLongClickListener(this);
        }
        /** Test Filter */
        int r[] = {2,3,5,6,7,8,9,11,12};
        HashMap el = allElementsContainer.getFilteredElements(r);
        for(int i = 0; i < el.size(); i++) {
            ChemElement s = (ChemElement) el.get(r[i]);
            //s.setBackgroundColor(Color.RED);
        }
        temp = (SeekBar) findViewById(R.id.temp);
        temp.setOnSeekBarChangeListener(new TempSeekBarListener(temp));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TemperatureSlideEvent event) {
        allElementsContainer.getStateInTemp(event.temperature);
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


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
