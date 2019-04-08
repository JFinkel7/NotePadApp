/* Software Developer: Denis J Finkel
Description: Allows  See Their Saved Note
*/
package com.jfinkelstudio.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notepad);
        Slidr.attach(ViewNoteActivity.this);
        //
        writeDocument();

    }

    // * Prevents Use From Accidentally Going Back! *
    public void onBackPressed() {
    }

    //* Gets The User Saved Document And Displays It *
    public void writeDocument() {
        Button btn = findViewById(R.id.btnViewNote);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentFireStore setDocument = new DocumentFireStore();
                TextView edTxt_Topic = findViewById(R.id.edTxt_Topic);
                TextView edTxt_Title = findViewById(R.id.edTxt_Title);
                setDocument.readDocument(ViewNoteActivity.this, edTxt_Title, edTxt_Topic);
            }
        });

    }

}
