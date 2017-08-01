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
import android.widget.ExpandableListView;
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
import org.unhack.chemistryeasy.ui.adaptors.DrawerAdapter;
import org.unhack.chemistryeasy.ui.adaptors.MixedPagerAdapter;
import org.unhack.chemistryeasy.ui.fragments.OrdinaryTable;
import org.unhack.chemistryeasy.ui.listeners.TempSeekBarListener;
import org.unhack.chemistryeasy.ui.popups.ElementPopUp;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    BigViewController big_view;
    ChemElementContainer allElementsContainer;
    public static MixedPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] groupsTitles = getResources().getStringArray(R.array.group_names);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), initDrawerOptions(), groupsTitles));
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();

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

    private ArrayList<ArrayList<String>> initDrawerOptions()
    {
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();

        ArrayList<String> elements = new ArrayList<String>();
        ArrayList<String> calculator = new ArrayList<String>();
        ArrayList<String> family_elements = new ArrayList<String>();
        ArrayList<String> agregat = new ArrayList<String>();

        for (int i = 0; i < getResources().getStringArray(R.array.elements).length; i++) {elements.add(i,getResources().getStringArray(R.array.elements)[i]);} // elements
        for (int i = 0; i < getResources().getStringArray(R.array.calculator).length; i++) {calculator.add(i,getResources().getStringArray(R.array.calculator)[i]);} // calculator
        for (int i = 0; i < getResources().getStringArray(R.array.family).length; i++) {family_elements.add(i,getResources().getStringArray(R.array.family)[i]);} // family
        for (int i = 0; i < getResources().getStringArray(R.array.agregat).length; i++) {agregat.add(i,getResources().getStringArray(R.array.agregat)[i]);} // agregat

        groups.add(0,elements);
        groups.add(1,calculator);
        groups.add(2,family_elements);
        groups.add(3,agregat);

        return groups;
    }
}
