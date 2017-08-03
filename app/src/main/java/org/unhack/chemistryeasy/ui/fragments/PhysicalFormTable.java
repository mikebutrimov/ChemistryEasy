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
import java.util.StringTokenizer;

/**
 * Created by unhack on 7/26/17.
 */

public class PhysicalFormTable extends OrdinaryTable  {

    @Override
    public void Ui_init(View v){
        super.Ui_init(v);
        temp.setOnSeekBarChangeListener(new TempSeekBarListener(temp));
        temp.setProgress(NORMAL_TEMPERATURE_K);
        this.container.getStateInTemp(NORMAL_TEMPERATURE_K);
        temp.setVisibility(View.VISIBLE);
        temp_tx.setVisibility(View.VISIBLE);
        temp_tx.setText(String.valueOf(NORMAL_TEMPERATURE_K));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TemperatureSlideEvent event) {
        this.container.getStateInTemp(event.temperature);
        temp_tx.setText(String.valueOf(event.temperature));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void  onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
