package com.ecust.ecusthelper.util.ui;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

/**
 * Created on 2016/5/9
 *
 * @author chenjj2048
 */
public class DrawerUtil {
    public static boolean isDrawerOpen(DrawerLayout drawerLayout) {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        if (!isDrawerOpen(drawerLayout))
            drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (isDrawerOpen(drawerLayout))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public static void toggleDrawer(DrawerLayout drawerLayout) {
        if (isDrawerOpen(drawerLayout))
            closeDrawer(drawerLayout);
        else
            openDrawer(drawerLayout);
    }
}
