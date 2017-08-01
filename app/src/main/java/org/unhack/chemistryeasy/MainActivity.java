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

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import org.unhack.chemistryeasy.elements.ChemElement;
import org.unhack.chemistryeasy.elements.ChemElementContainer;
import org.unhack.chemistryeasy.ui.adaptors.MixedPagerAdapter;
import org.unhack.chemistryeasy.ui.fragments.OrdinaryTable;
import org.unhack.chemistryeasy.ui.fragments.OrdinaryTablePhysicalForm;
import org.unhack.chemistryeasy.ui.popups.ElementPopUp;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, NavigationView.OnNavigationItemSelectedListener{
    BigViewController big_view;
    public static MixedPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.requestLayout();

        NavigationView navigationView = (NavigationView) findViewById(R.id.left_drawer);
        navigationView.setNavigationItemSelectedListener(this);
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
        OrdinaryTablePhysicalForm mOrdinaryTablePhysicalForm = new OrdinaryTablePhysicalForm();
        mOrdinaryTableFragment.setContainer(ChemElementContainerFabric(getApplicationContext()));
        mOrdinaryTablePhysicalForm.setContainer(ChemElementContainerFabric(getApplicationContext()));
        pagerAdapter = new MixedPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(mOrdinaryTableFragment);
        pagerAdapter.addFragment(mOrdinaryTablePhysicalForm);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.elements) {
            // display all elements for example :)
            Toast.makeText(getApplicationContext(),"Elements", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(0);

        } else if (id == R.id.physical_form) {
            Toast.makeText(getApplicationContext(),"Physical Form", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(1);

        } else if (id == R.id.element_p) {
            Toast.makeText(getApplicationContext(),"P-elems", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.element_s) {
            Toast.makeText(getApplicationContext(),"S-elems", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    ChemElementContainer ChemElementContainerFabric (Context cntx){
        ChemElementContainer buf = new ChemElementContainer(cntx);
        buf.initFromDb(cntx);
        return buf;
    }


}
