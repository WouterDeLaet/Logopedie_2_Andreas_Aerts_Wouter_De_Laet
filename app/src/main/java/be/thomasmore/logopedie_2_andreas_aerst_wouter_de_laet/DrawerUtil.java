package be.thomasmore.logopedie_2_andreas_aerst_wouter_de_laet;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class DrawerUtil {

    public static void getDrawer(final Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemGoToStartScherm = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.naar_start_scherm).withIcon(R.drawable.ic_menu_camera);
        PrimaryDrawerItem drawerItemGoToSpeechToText = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.naar_speech_to_text).withIcon(R.drawable.ic_menu_gallery);
        PrimaryDrawerItem drawerItemGoToWritingTest = new PrimaryDrawerItem()
                .withIdentifier(3).withName(R.string.naar_writing_test).withIcon(R.drawable.ic_menu_slideshow);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        drawerEmptyItem,drawerEmptyItem,drawerEmptyItem,
                        drawerItemGoToStartScherm,
                        drawerItemGoToSpeechToText,
                        drawerItemGoToWritingTest
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 2 && !(activity instanceof MainActivity)) {
                            Intent intent = new Intent(activity, MainActivity.class);
                            view.getContext().startActivity(intent);
                        }
                        else if (drawerItem.getIdentifier() == 1 && !(activity instanceof StartScherm))
                        {
                            Intent intent = new Intent(activity, StartScherm.class);
                            view.getContext().startActivity(intent);
                        }
                        else if (drawerItem.getIdentifier() == 3 && !(activity instanceof writing_test))
                        {
                            Intent intent = new Intent(activity, writing_test.class);
                            view.getContext().startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();
    }
}
