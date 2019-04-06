package org.debatetool.debatetoolandroid.tree;

import org.debatetool.core.HashIdentifiedSpeechComponent;
import org.debatetool.debatetoolandroid.R;

import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class File implements LayoutItemType {
    private HashIdentifiedSpeechComponent content;

    public File(HashIdentifiedSpeechComponent content) {
        this.content = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public HashIdentifiedSpeechComponent getContent() {
        return content;
    }
}