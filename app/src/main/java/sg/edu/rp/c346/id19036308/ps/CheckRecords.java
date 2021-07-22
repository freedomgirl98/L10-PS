package sg.edu.rp.c346.id19036308.ps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class CheckRecords extends AppCompatActivity {

    Button btnRefresh, btnFav;
    ListView lvRecords;
    TextView tvRecords;
    ArrayList<String> alRecords;
    ArrayAdapter<String> aaRecord;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_records);
        setTitle("P10-Getting My Locations (Enhanced)");
        btnRefresh = findViewById(R.id.btnRefresh);
        btnFav = findViewById(R.id.btnFav);
        lvRecords = findViewById(R.id.lvRecords);
        tvRecords = findViewById(R.id.tvRecords);
        alRecords = new ArrayList<>();
        aaRecord = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alRecords);
        lvRecords.setAdapter(aaRecord);

        // Reading internal Storage
        String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
        File targetFile_I = new File(folderLocation_I,"data.txt");
        if (targetFile_I.exists() == true){
            String data = "";
            try{
                FileReader reader = new FileReader(targetFile_I);
                BufferedReader br = new BufferedReader(reader);
                String line = br.readLine();
                while (line != null){
//                    tvRecords.setText(line);
                    data += line + "\n";
                    line = br.readLine();
                    alRecords.add(line + "\n");
                    aaRecord.notifyDataSetChanged();
//                    tvRecords.setText("Number of records: "+alRecords.size());


//                    tv.setText("Data: " + data );
                }
//                tvRecords.setText(alRecords.size());
                tvRecords.setText("Number of records: "+alRecords.size());

                br.close();
                reader.close();
            } catch (Exception e){
                Toast.makeText(CheckRecords.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("Content", data);
        }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alRecords.clear();
                // Reading internal Storage
                String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                File targetFile_I = new File(folderLocation_I,"data.txt");
                if (targetFile_I.exists() == true){
                    String data = "";
                    try{
                        FileReader reader = new FileReader(targetFile_I);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();
                            alRecords.add(line + "\n");
                            aaRecord.notifyDataSetChanged();

                        }
                        tvRecords.setText("Number of records: "+alRecords.size());
                        br.close();
                        reader.close();
                    } catch (Exception e){
                        Toast.makeText(CheckRecords.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("Content", data);
                }
            }
        });

        lvRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String coordinates = lvRecords.getItemAtPosition(i).toString();
                builder = new AlertDialog.Builder(CheckRecords.this);
//                builder.setMessage("Add this location in your favourite list?") .setTitle("Add this location in your favourite list?");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to close this application ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //File Creation and writing internal
                                try {
                                    String folderLocation_2= getFilesDir().getAbsolutePath() + "/Folder";
                                    File targetFile_2 = new File(folderLocation_2, "favorites.txt");
                                    FileWriter writer_I = new FileWriter(targetFile_2, true);
                                    writer_I.write(coordinates );
                                    writer_I.flush();
                                    writer_I.close();
                                } catch (Exception e){
                                    Toast.makeText(CheckRecords.this, "Failed to write!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }

//                                finish();
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Add this location in your favourite list?");
                alert.show();
            }

        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alRecords.clear();
                // Reading internal Storage
                String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                File targetFile_I = new File(folderLocation_I,"favorites.txt");
                if (targetFile_I.exists() == true){
                    String data = "";
                    try{
                        FileReader reader = new FileReader(targetFile_I);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();
                            alRecords.add(line + "\n");
                            aaRecord.notifyDataSetChanged();

                        }
                        tvRecords.setText("Number of records: "+alRecords.size());
                        br.close();
                        reader.close();
                    } catch (Exception e){
                        Toast.makeText(CheckRecords.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    Log.d("Content", data);
                }

            }
        });

    }
}