package com.apnatutorials.optionmenuwithicons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static String POPUP_CONSTANT = "mPopup";
    private static String POPUP_FORCE_SHOW_ICON = "setForceShowIcon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }


    public boolean onMenuItemSelect(MenuItem item) {
        showPopup(findViewById(item.getItemId()));
        return true;
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(MainActivity.this, view);
        try {
            // Reflection apis to enforce show icon
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(POPUP_CONSTANT)) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(POPUP_FORCE_SHOW_ICON, boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pmnuDelete:
                Toast.makeText(MainActivity.this, "You clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pmnuEdit:
                Toast.makeText(MainActivity.this, "You clicked edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pmnuShare:
                Toast.makeText(MainActivity.this, "You clicked share", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
