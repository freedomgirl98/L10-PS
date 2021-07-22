package sg.edu.rp.c346.id19036308.ps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileWriter;

public class MyMusicService extends Service {

    // declaring object of MediaPlayer
    private MediaPlayer player = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    @Override
    public void onCreate(){
        Log.d("MyService", "Service created");
        super.onCreate();
    }

    @Override
    // execution of service will start on calling this method
    public int onStartCommand(Intent intent, int flags, int startId){

        try{
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder","music.mp3");

            // specify the path of the audio file
            player.setDataSource(file.getPath());
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // providing the boolean value as true to play the audio on Loop
        player.setLooping(true);

        //starting the process
        player.start();

        // returns the status of the program
        return START_STICKY;
    }

    @Override
    // execution of the service will stop on calling this method
    public void onDestroy() {
        super.onDestroy();


        // stopping the process
        player.stop();
    }


    private boolean checkWritePermission() {
        int permissionCheck_Write = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        int permissionCheck_Read = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        );
        if (permissionCheck_Write == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Read == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}