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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.FileWriter;

public class MyService extends Service {

    private FusedLocationProviderClient client;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private double lat;
    private double lng;

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

        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        if (checkLocationPermission()) {

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setSmallestDisplacement(100);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult){
                    if (locationResult != null){
                        Location data = locationResult.getLastLocation();
                        lat = data.getLatitude();
                        lng = data.getLongitude();

                        //File Creation and writing internal
                        try {
                            String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                            File targetFile_I = new File(folderLocation_I, "data.txt");
                            FileWriter writer_I = new FileWriter(targetFile_I, true);
                            writer_I.write("Lat: " + lat + " Lng: " + lng + "\n");
                            writer_I.flush();
                            writer_I.close();
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Failed to write!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "New Location Detected\n" + "Lat: " + lat + "lng: " + lng, Toast.LENGTH_SHORT).show();
                    }
                }

            };
            client.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }



        // returns the status of the program
        return START_STICKY;
    }

    @Override
    // execution of the service will stop on calling this method
    public void onDestroy() {
        super.onDestroy();

        // Stopping tracking of location
        client.removeLocationUpdates(mLocationCallback);

    }

    private boolean checkLocationPermission() {
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        );
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        );
        if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
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