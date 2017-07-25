package com.example.adilos.takenotes;

/**
 * Created by ADILOS on 26-07-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        //-1 if noteId/position is null
        noteId = intent.getIntExtra("noteId", -1);

        //obtain already present text at clicked item's position
        // and set it on the text edit activity
        if (noteId != -1) {
            if(MainActivity.notes.get(noteId)=="Example note"){
                editText.setHint("Example note");
            }else {
                editText.setText(MainActivity.notes.get(noteId));
            }
        } else {
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        //add a listener to edit text which will update the data on text change
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //set text on list view and notify array adapter
                MainActivity.notes.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                //store data in shared preferences
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.adilos.takenotes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
