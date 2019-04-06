package org.debatetool.debatetoolandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import org.debatetool.debatetoolandroid.tree.DebateTreeNode;
import org.debatetool.debatetoolandroid.tree.Directory;
import org.debatetool.debatetoolandroid.tree.DirectoryNodeBinder;
import org.debatetool.debatetoolandroid.tree.File;
import org.debatetool.debatetoolandroid.tree.FileNodeBinder;
import org.debatetool.io.iocontrollers.IOController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        RecyclerView recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);
    }

    private class InitMongoTask extends AsyncTask<String, Integer, List<TreeNode>>{
        @Override
        protected List<TreeNode> doInBackground(String ... strings) {
            return startMongoGetRoot();
        }
    }

    private  List<TreeNode> startMongoGetRoot(){
        IOController.getIoController().attemptAuthenticate("ec2-3-18-215-223.us-east-2.compute.amazonaws.com", 27017, "user", "password");
        List<TreeNode> root = new ArrayList<>();
        List<String> rootNames = IOController.getIoController().getStructureIOManager().getRoot();
        // TODO add nondirectories
        for (String name:rootNames){
            TreeNode dir = new DebateTreeNode(new Directory(name));
            root.add(dir);
        }
        return root;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        InitMongoTask backgroundMongo = new InitMongoTask();
        backgroundMongo.execute();
        List<TreeNode> root = null;
        try {
            root = backgroundMongo.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TreeViewAdapter adapter = new TreeViewAdapter(root, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
        // whether collapse child nodes when their parent node was close.
//        adapter.ifCollapseChildWhileCollapseParent(true);
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
                }else{
                    Snackbar.make(recyclerView, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
