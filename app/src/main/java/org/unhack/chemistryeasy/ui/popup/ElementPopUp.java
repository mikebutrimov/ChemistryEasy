package org.unhack.chemistryeasy.ui.popup;

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
import android.widget.TableLayout;
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
    private TextView symbol,number,mass,native_name,second_name;
    private CardView big_view_layout;
    private RelativeLayout left_element;

    public ElementPopUp(ChemElement element, Context context, View parentView){
        this.element = element;
        this.context = context;
        this.parentView = parentView;
    }

    public void show(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.big_view_element, null);

        symbol = (TextView) popupView.findViewById(R.id.big_symbol);
        number = (TextView) popupView.findViewById(R.id.big_number);
        big_view_layout = (CardView) popupView.findViewById(R.id.big_view);
        mass = (TextView) popupView.findViewById(R.id.big_mass);
        native_name = (TextView) popupView.findViewById(R.id.big_native_name);
        second_name = (TextView) popupView.findViewById(R.id.second_name);
        left_element = (RelativeLayout) popupView.findViewById(R.id.left_element);

        symbol.setText(element.getElementSymbol());
        number.setText(String.valueOf(element.getElementNumber()));
        mass.setText(String.valueOf(element.getAtomicWeight()));
        native_name.setText(element.getElementNativeName());
        switch (element.getBlockName())
        {
            case "s":
                left_element.setBackgroundColor(Color.parseColor("#E87891"));
                break;
            case "p":
                left_element.setBackgroundColor(Color.parseColor("#F5DA67"));
                break;
            case "d":
                left_element.setBackgroundColor(Color.parseColor("#4EBDD1"));
                break;
            case "f":
                left_element.setBackgroundColor(Color.parseColor("#ABD3AE"));
                break;
            default:
                left_element.setBackgroundColor(Color.parseColor("#6b6b6b"));
                return;
        }
        if(element.isRadioactive()) {//show logo of radioactive
        }

        boolean focusable = true;
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int x = (int) parentView.getX();
        int y = (int) parentView.getY();
        Log.d("Coords", "X: " + String.valueOf(x) + "   Y: " + String.valueOf(y));
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        if(element.getElementNumber() >= 58 && element.getElementNumber() <= 71 || element.getElementNumber() >= 90 && element.getElementNumber() <= 103)
        {
            Log.d("Coords2", "X: " + String.valueOf(x) + "   Y: " + String.valueOf(y));
            int[] location = new int[2];
            element.getLocationInWindow(location);
            int x2 = location[0];
            int y2 = location[1];
            popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, x2,y2 - element.getHeight() * 3);

        }else {
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, x, y);}

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
