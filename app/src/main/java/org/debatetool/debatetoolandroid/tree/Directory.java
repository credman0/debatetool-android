package org.debatetool.debatetoolandroid.tree;

import org.debatetool.debatetoolandroid.R;

import tellh.com.recyclertreeview_lib.LayoutItemType;


public class Directory implements LayoutItemType {
    public String dirName;

    public Directory(String dirName) {
        this.dirName = dirName;
    }


    public String toString(){
        return dirName;
    }
    @Override
    public int getLayoutId() {
        return R.layout.item_detail;
    }
}
