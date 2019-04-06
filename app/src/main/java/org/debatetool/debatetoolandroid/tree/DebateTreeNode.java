package org.debatetool.debatetoolandroid.tree;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.debatetool.core.HashIdentifiedSpeechComponent;
import org.debatetool.io.iocontrollers.IOController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tellh.com.recyclertreeview_lib.LayoutItemType;
import tellh.com.recyclertreeview_lib.TreeNode;

public class DebateTreeNode extends TreeNode {
    boolean isFetched = false;
    public DebateTreeNode(@NonNull LayoutItemType content) {
        super(content);
    }
    public List<String> getPath(){
        if (getParent()==null){
            List<String> path = new ArrayList<>();
            path.add(getContent().toString());
            return path;
        }else{
            List<String> parentPath = ((DebateTreeNode) getParent()).getPath();
            parentPath.add(getContent().toString());
            return parentPath;
        }
    }

    @Override
    public List<TreeNode> getChildList(){
        if (isFetched){
            return super.getChildList();
        }
        try {
            List<TreeNode> nodes = new TreeGetChilrenPath().execute(getPath()).get();
            for (TreeNode node:nodes){
                addChild(node);
            }
            isFetched = true;
            return super.getChildList();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isFetched = false;
        return null;
    }

    @Override
    public boolean isLeaf(){
        return getContent() instanceof File;
    }

    private class TreeGetChilrenPath extends AsyncTask<List<String>, Integer, List<TreeNode>> {
        @Override
        protected List<TreeNode> doInBackground(List<String>... lists) {
            List<TreeNode> children = new ArrayList<>();
            List<String> childNames = IOController.getIoController().getStructureIOManager().getChildren(lists[0]);
            for (String name:childNames){
                children.add(new DebateTreeNode(new Directory(name)));
            }
            try {
                List<HashIdentifiedSpeechComponent> contents = IOController.getIoController().getStructureIOManager().getContent(getPath());
                for (HashIdentifiedSpeechComponent content:contents){
                    children.add(new DebateTreeNode(new File(content)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return children;
        }
    }
}
