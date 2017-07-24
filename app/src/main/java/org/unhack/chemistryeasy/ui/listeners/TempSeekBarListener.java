package org.unhack.chemistryeasy.ui.listeners;

import android.widget.SeekBar;

import org.greenrobot.eventbus.EventBus;
import org.unhack.chemistryeasy.events.TemperatureSlideEvent;

/**
 * Created by unhack on 7/24/17.
 */

public class TempSeekBarListener implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar;
    public TempSeekBarListener(SeekBar seekBar){
        this.seekBar = seekBar;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        EventBus.getDefault().post(new TemperatureSlideEvent(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
