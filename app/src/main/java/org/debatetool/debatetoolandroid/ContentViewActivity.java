package org.debatetool.debatetoolandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.debatetool.core.HashIdentifiedSpeechComponent;
import org.debatetool.debatetoolandroid.tree.File;

import java.util.List;
import java.util.concurrent.ExecutionException;

import tellh.com.recyclertreeview_lib.TreeNode;

public class ContentViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView textView = findViewById(R.id.textView);
        try {
            textView.setText(new ContentFetchTask().execute(((File)getIntent().getExtras().get("fileContent")).getContent()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class ContentFetchTask extends AsyncTask<HashIdentifiedSpeechComponent, Integer, String> {

        @Override
        protected String doInBackground(HashIdentifiedSpeechComponent... hashIdentifiedSpeechComponents) {
            return hashIdentifiedSpeechComponents[0].getDisplayContent();
        }
    }

}
