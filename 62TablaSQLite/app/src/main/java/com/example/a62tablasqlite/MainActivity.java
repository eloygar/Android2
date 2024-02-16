package com.example.a62tablasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mEditTextWord;
    EditText mEditTextDefinition;
    DictionaryDatabase mDB;
    ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDB = new DictionaryDatabase(this);
        mEditTextWord = findViewById(R.id.editTextWord);
        mEditTextDefinition = findViewById(R.id.editTextDefinition);
        Button buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        buttonAddUpdate.setOnClickListener(view -> saveRecord());
        mListView = findViewById(R.id.listView);
        mListView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(MainActivity.this, mDB.getDefinition(id), Toast.LENGTH_SHORT).show()
        );
        mListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this,
                    "Records deleted = " + mDB.deleteRecord(id), Toast.LENGTH_SHORT).show();
            updateWordList();
            return true;
        }); 
        updateWordList();
    }

    private void saveRecord() {
        mDB.saveRecord(mEditTextWord.getText().toString(), mEditTextDefinition.getText().toString());
        mEditTextWord.setText("");
        mEditTextDefinition.setText("");
        updateWordList();
    }

    private void updateWordList() {
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                mDB.getWordList(),
                new String[]{"word"},
                new int[]{android.R.id.text1},
                0);
        mListView.setAdapter(simpleCursorAdapter);
    }


}