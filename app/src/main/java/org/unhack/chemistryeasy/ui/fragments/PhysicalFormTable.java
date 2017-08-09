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

import android.view.View;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.unhack.chemistryeasy.R;
import org.unhack.chemistryeasy.events.TemperatureSlideEvent;
import org.unhack.chemistryeasy.ui.listeners.TempSeekBarListener;


/**
 * Created by unhack on 7/26/17.
 */

public class PhysicalFormTable extends OrdinaryTable  {
    private int last_temp = 0;

    @Override
    public void init_specific_ui(View v){
        super.init_specific_ui(v);
        temp.setOnSeekBarChangeListener(new TempSeekBarListener(temp));
        container.getStateInTemp(NORMAL_TEMPERATURE_K);
        temp.setVisibility(View.VISIBLE);
        temp_tx.setVisibility(View.VISIBLE);
        temp_tx.setText(String.valueOf(NORMAL_TEMPERATURE_K) + getString(R.string.Kelvin));
        temp.setProgress(NORMAL_TEMPERATURE_K);
        last_temp = NORMAL_TEMPERATURE_K;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TemperatureSlideEvent event) {
        container.getStateInTemp(event.temperature);
        temp_tx.setText(String.valueOf(event.temperature) + getString(R.string.Kelvin));
        last_temp = event.temperature;
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
    @Override
    public void onResume(){
        super.onResume();
        temp.setProgress(last_temp);
        temp_tx.setText(String.valueOf(last_temp) + getString(R.string.Kelvin));

    }

}
