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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                    fab.setAlpha((float)1.2 - slideOffset);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                fab.animate().translationXBy(-500).withLayer();
                fab.animate().rotationBy(-500).withLayer();}
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
