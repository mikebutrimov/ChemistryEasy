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

package org.unhack.chemistryeasy.ui.popups;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.unhack.chemistryeasy.R;
import org.unhack.chemistryeasy.elements.ChemElement;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by unhack on 7/21/17.
 */

public class ElementPopUp {

    private ChemElement element;
    private Context context;
    private View parentView;
    PopupWindow popupWindow;
    RelativeLayout element_lay;

    public ElementPopUp(ChemElement element, Context context, View parentView){
        this.element = element;
        this.context = context;
        this.parentView = parentView;
    }
    public void show(){
        /** Object */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.element_popup, null);
        CardView pop_up_card = (CardView) popupView.findViewById(R.id.pop_up_card);
        element_lay = (RelativeLayout) popupView.findViewById(R.id.element);

        /** Text */
        TextView symbol = (TextView) popupView.findViewById(R.id.pop_symbol);
        TextView number = (TextView) popupView.findViewById(R.id.pop_number);
        TextView mass = (TextView) popupView.findViewById(R.id.pop_mass);
        TextView native_name = (TextView) popupView.findViewById(R.id.pop_native_name);
        TextView second_name = (TextView) popupView.findViewById(R.id.pop_second_name);
        symbol.setText(element.getElementSymbol());
        number.setText(String.valueOf(element.getElementNumber()));
        mass.setText(String.valueOf(element.getAtomicWeight()));
        native_name.setText(element.getElementNativeName());
        this.colorise(element.getBlockName());
        if(element.isRadioactive()) {//show logo of radioactive
        }
        boolean focusable = true;
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int[] location = new int[2];
        element.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        Log.d("Coords", "X: " + String.valueOf(x) + "   Y: " + String.valueOf(y));
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, x, y - element.getHeight() * 3);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void colorise(String block_name) // Colorise PopUp to color of element
    {
        switch (block_name){
            case "s":
                element_lay.setBackgroundColor(Color.parseColor("#E87891"));
                break;
            case "p":
                element_lay.setBackgroundColor(Color.parseColor("#F5DA67"));
                break;
            case "d":
                element_lay.setBackgroundColor(Color.parseColor("#4EBDD1"));
                break;
            case "f":
                element_lay.setBackgroundColor(Color.parseColor("#ABD3AE"));
                break;
            default:
                element_lay.setBackgroundColor(Color.parseColor("#6b6b6b"));
                return;
        }
    }
}
