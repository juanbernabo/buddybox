package buddybox;

import android.app.Application;

import buddybox.io.Library;
import buddybox.io.Player;
import buddybox.io.Sampler;
import buddybox.model.Model;
import buddybox.ui.ModelProxy;

public class BuddyBoxApp extends Application {

    //TODO Check for storage permission.

    private static boolean USE_SIMULATOR = false;

    @Override
    public void onCreate() {
        super.onCreate();
        ModelProxy.init(USE_SIMULATOR ? new ModelSim() : new Model(this));
        Player.init(this);
        Library.init();
        Sampler.init(this);
    }
}
