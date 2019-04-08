package com.jfinkelstudio.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("Registered")
public class DocumentFireStore extends AppCompatActivity {
    private static String _KEY_TITLE = "Title";
    private static String _KEY_DESCRIPTION = "Description";
    private String _successMessage, _failureMessage;
    private final String _collectionPath = "Notebook";
    private final String _documentPath = "Notes";
    private FirebaseFirestore db_FireStore = FirebaseFirestore.getInstance();

    // * Default Constructor *
    public DocumentFireStore() {}

    // * Writes The Document *
    public void writeDocument(final Context context, EditText edTxtID_TITLE, EditText edTxtID_DESCRIPTION) {
        String title = edTxtID_TITLE.getText().toString();
        String description = edTxtID_DESCRIPTION.getText().toString();
        Map<String, Object> document = new HashMap<>();
        document.put(_KEY_TITLE, title);
        document.put(_KEY_DESCRIPTION, description);
        db_FireStore.collection(_collectionPath).document(_documentPath).set(document)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, get_SuccessMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, get_FailureMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //* Outputs The Saved Document *
    public void readDocument(final Context context, final TextView txtView_Title, final TextView txtView_Topic) {
        DocumentReference documentReference = db_FireStore.collection(_collectionPath).document(_documentPath);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String title = documentSnapshot.getString(_KEY_TITLE);
                            String description = documentSnapshot.getString(_KEY_DESCRIPTION);
                            txtView_Title.setText(String.format("Title: %s", title));
                            txtView_Topic.setText(String.format("Description: %s", description));
                            Toast.makeText(context, "Document Exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Please Create A Document To Retrieve", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Optional Message Here!
                    }
                });

    }


    // <----------------- ** SET METHODS ** ----------------->
    public void successMessage(String message) {
        this._successMessage = message;
    }

    public void failureMessage(String message) {
        this._failureMessage = message;
    }

    // <----------------- ** GET METHODS ** ----------------->
    private String get_SuccessMessage() {
        return (_successMessage);
    }

    private String get_FailureMessage() {
        return (_failureMessage);
    }


}
