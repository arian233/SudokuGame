package com.cmpt276.lota.sudoku;
/*
 * Powered by MaterialFilePicker built by nbsp-team
 * @License GNU General Public License version 2
 * @Link https://github.com/nbsp-team/MaterialFilePicker
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InputWordsActivity extends AppCompatActivity {
    TextView chosenFileName;
    Button chooseFileBtn;
    Button OKBtn;
    EditText enterFileName;
    private final int REQUEST_PERMISSION_CODE = 1001;
    private final int PICK_FILE_CODE = 1000;
    List<Words> words;
    private WordListLab wordListLab = WordListLab.getWordListLab();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_words);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }

        chosenFileName = (TextView) findViewById(R.id.chosen_file_name);
        enterFileName = (EditText) findViewById(R.id.edit_file_name);
        chooseFileBtn = (Button) findViewById(R.id.choose_file);
        OKBtn = (Button) findViewById(R.id.input_word_list);
        chooseFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(InputWordsActivity.this)
                        .withRequestCode(PICK_FILE_CODE)
                        .withFilter(Pattern.compile(".*\\.csv$")) // Filtering files and directories by file name using regexp
                        .withFilterDirectories(false) // Set directories filterable (false by default)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_CODE && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            chosenFileName.setText(filePath);
            try {
                readFileData(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //Toast.makeText(this,""+words.get(5).getLanguageOne()+words.get(5).getLanguageTwo(), Toast.LENGTH_LONG).show();
    }

    /**
     * read csv file data
     * @param path path of file
     */
    void readFileData(String path) throws FileNotFoundException, UnsupportedEncodingException {
        String[] data;
        File file = new File(path);
        if (file.exists())
        {
            words = new ArrayList<>();
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
            BufferedReader reader = new BufferedReader(isr);
            try
            {
                String csvLine;
                while ((csvLine = reader.readLine()) != null)
                {
                    data=csvLine.split(",");
                    try
                    {
                        words.add(new Words(data[0], data[1]));
                        //Toast.makeText(getApplicationContext(),data[0]+" "+data[1],Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {
                        Log.e("Problem",e.toString());
                    }
                }
                Toast.makeText(this, "Read File Successfully: " + file.getName(), Toast.LENGTH_SHORT).show();
                OKBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = enterFileName.getText().toString();
                        if (name.equals("")){
                            Toast.makeText(InputWordsActivity.this, "Please enter the world list name.", Toast.LENGTH_SHORT).show();
                        }else {
                            ListsOfWords list = new ListsOfWords(words, name);
                            wordListLab.addListsOfWords(list);
                            finish();
                        }
                    }
                });
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Error in reading CSV file: "+ex);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"file not exists",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
