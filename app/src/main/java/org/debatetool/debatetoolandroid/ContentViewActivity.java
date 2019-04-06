package org.debatetool.debatetoolandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.debatetool.core.HashIdentifiedSpeechComponent;
import org.debatetool.debatetoolandroid.tree.File;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tellh.com.recyclertreeview_lib.TreeNode;

public class ContentViewActivity extends AppCompatActivity {

    final private static String STYLE = "<style id=\"style\">\n" +
            "h {\n" +
            "  font-size : 13pt;\n" +
            "  background-color: #00e310;\n" +
            "}\n" +
            "div {\n" +
            "  font-size : 8pt;\n" +
            "  font-family : Arial;\n" +
            "  margin-top: 0px;\n" +
            "  margin-bottom: 0px;\n" +
            "  margin-right: 0px;\n" +
            "  margin-left: 0px;\n" +
            "}\n" +
            "u {\n" +
            "  font-size : 13pt;\n" +
            "  text-decoration : underline;\n" +
            "}\n" +
            "c {\n" +
            "  font-size : 13pt;\n" +
            "  font-weight : bold;\n" +
            "}\n" +
            "n {\n" +
            "  font-size : 13pt;\n" +
            "  font-weight : bold;\n" +
            "}\n" +
            "t {\n" +
            "  font-size : 13pt;\n" +
            "  font-weight : bold;\n" +
            "}\n" +
            "</style>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getIntent().getStringExtra("label"));

        WebView webView = findViewById(R.id.webView);
        try {
            webView.getSettings().setJavaScriptEnabled(true);
            String contents = STYLE + new ContentFetchTask().execute(((File)getIntent().getExtras().get("fileContent")).getContent()).get();
            webView.loadDataWithBaseURL("", contents, "text/html", "UTF-8", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class ContentFetchTask extends AsyncTask<HashIdentifiedSpeechComponent, Integer, String> {

        @Override
        protected String doInBackground(HashIdentifiedSpeechComponent... hashIdentifiedSpeechComponents) {
            if (!hashIdentifiedSpeechComponents[0].isLoaded()){
                try {
                    hashIdentifiedSpeechComponents[0].load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return hashIdentifiedSpeechComponents[0].getDisplayContent();
        }
    }

}
