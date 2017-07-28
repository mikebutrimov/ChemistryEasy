package org.unhack.chemistryeasy;

import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.unhack.chemistryeasy.db.DataBaseHelper;
import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;
import org.unhack.chemistryeasy.events.TemperatureSlideEvent;
import org.unhack.chemistryeasy.ui.adaptors.MixedPagerAdapter;
import org.unhack.chemistryeasy.ui.fragments.OrdinaryTable;
import org.unhack.chemistryeasy.ui.listeners.TempSeekBarListener;
import org.unhack.chemistryeasy.ui.popups.ElementPopUp;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    BigViewController big_view;
    ChemElementContainer allElementsContainer;
    SeekBar temp;
    int width,height,x_size,y_size;
    public static MixedPagerAdapter pagerAdapter;
    private ViewPager viewPager;



    private String[] mMenuOptions;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private static final int X_CROP  = 18;
    private static final int Y_CROP  = 12;
    private static final int BV_X_SIZE = 10;
    private static final int BV_Y_SIZE = 3;
    /** Elements */
    private static final int ELEMENTS_MARGIN_TOP = 1;
    private static final int ELEMENTS_MARGIN_BUTTOM = 1;
    private static final int ELEMENTS_MARGIN_LEFT = 1;
    private static final int ELEMENTS_MARGIN_RIGHT = 1;


    TextView temp_tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Get screen size
         */
        /*Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        x_size = (int) Math.floor((double)width / X_CROP);
        y_size = (int) Math.floor((double)height / Y_CROP);
        //calc of X margin
        int x_margin = (width - x_size*18) /2;
        final View mView = findViewById(android.R.id.content);
        mView.setPadding(x_margin,x_margin,x_margin,x_margin);

        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        if (db.isValid) {
            db.openDataBase();
        }
        else {
            Log.d("DB", "problem, db was not inited well");
        }
        */
        /*
        Prepare burger menu
         */
        mMenuOptions = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMenuOptions));
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Toast toast;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(toast != null) {toast.cancel();}
                toast = Toast.makeText(getApplicationContext(),"You click item with position: " + String.valueOf(position + 1), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        allElementsContainer = new ChemElementContainer(getApplicationContext());
        allElementsContainer.initFromDb(getApplicationContext());

        //OrdinaryTable mOrdinaryTableFragment = new OrdinaryTable();
        //mOrdinaryTableFragment.setContainer(allElementsContainer);



        //Ui_init();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
                fab.animate().translationXBy(-500).withLayer();
                fab.animate().rotationBy(-500).withLayer();
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                    fab.setAlpha(1 - slideOffset);
            }
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {
                fab.animate().rotationBy(500).withLayer();
                fab.animate().translationXBy(+500).withLayer();}
        @Override
            public void onDrawerStateChanged(int newState) {}
        });
        initPaging();
    }


    private void initPaging() {
        OrdinaryTable mOrdinaryTableFragment = new OrdinaryTable();
        mOrdinaryTableFragment.setContainer(allElementsContainer);
        pagerAdapter = new MixedPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(mOrdinaryTableFragment);
        viewPager = (ViewPager) findViewById(R.id.container);
        if (viewPager != null) {
            viewPager.setAdapter(pagerAdapter);
        }
    }




    /** --------------- UI ----------- */
    /*public void Ui_init(){
        allElementsContainer = new ChemElementContainer(getApplicationContext());
        allElementsContainer.initFromDb(getApplicationContext());
        big_view = new BigViewController(getApplicationContext());

        Space space = new Space(getApplicationContext());
        Space space2 = new Space(getApplicationContext());
        space2.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(0,0), GridLayout.spec(12,5)));
        final GridLayout table = (GridLayout) findViewById(R.id.table_layout);
        GridLayout lantan = (GridLayout) findViewById(R.id.lantan);
        GridLayout.LayoutParams bigViewParams = new GridLayout.LayoutParams(GridLayout.spec(0,3), GridLayout.spec(2,10));
        bigViewParams.width = x_size*BV_X_SIZE;
        bigViewParams.height = y_size*BV_Y_SIZE;
        big_view.setLayoutParams(bigViewParams);

        for(int i = 0; i < allElementsContainer.getSize(); i++) {
            ChemElement buf = allElementsContainer.getElementByNumber(i + 1);
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
    /*
        int r[] = {2,3,5,6,7,8,9,11,12};
        HashMap el = allElementsContainer.getFilteredElements(r);
        for(int i = 0; i < el.size(); i++) {
            ChemElement s = (ChemElement) el.get(r[i]);
            //s.setBackgroundColor(Color.RED);
        }
        temp = (SeekBar) findViewById(R.id.temp);
        temp.setProgress(273);
        temp.setOnSeekBarChangeListener(new TempSeekBarListener(temp));
        temp_tx = (TextView) findViewById(R.id.temp_tx);
    }*/


    @Override
    public void onClick(View v) {
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
