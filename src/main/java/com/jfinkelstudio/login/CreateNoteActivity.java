/* Software Developer: Denis J Finkel
Description: Allows User TO Create Their Personal Note
*/
package com.jfinkelstudio.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.r0adkll.slidr.Slidr;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notepad);
        Slidr.attach(CreateNoteActivity.this);


    }

    // * Prevents Use From Accidentally Going Back! *
    @Override
    public void onBackPressed() {
    }


    // * Writes The Document To FireStore *
    public void createDocument() {
        EditText title = findViewById(R.id.edTxt_Title);
        EditText description = findViewById(R.id.edTxt_Topic);
        DocumentFireStore setDocument = new DocumentFireStore();
        setDocument.writeDocument(CreateNoteActivity.this, title, description);
        setDocument.successMessage("Document Saved");
        setDocument.failureMessage("Failed To Send Document");
    }


    // onClick Event
    public void btn_SaveNote(View view) {
        createDocument();
    }


}
