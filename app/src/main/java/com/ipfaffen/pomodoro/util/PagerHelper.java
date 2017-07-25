package com.ipfaffen.pomodoro.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaias Pfaffenseller
 */
public class PagerHelper {

    private PagerItem itemNull;
    private List<PagerItem> items;
    private PagerItem mainItem;

    public PagerHelper() {
        itemNull = new PagerItem(-1, -1, -1, null);
        items = new ArrayList<>();
        mainItem = itemNull;
    }

    public void addItem(PagerItem item) {
        addItem(item, false);
    }

    public void addItem(PagerItem item, boolean main) {
        items.add(item);
        if(main) {
            mainItem = item;
        }
    }

    public PagerItem getMainItem() {
        return mainItem;
    }

    public PagerItem getItemByPosition(int position) {
        for(PagerItem item: items) {
            if(item.getPosition() == position) {
                return item;
            }
        }
        return itemNull;
    }

    public PagerItem getItemByNavId(int navId) {
        for(PagerItem item: items) {
            if(item.getNavId() == navId) {
                return item;
            }
        }
        return itemNull;
    }

    public int getTotalItems() {
        return items.size();
    }
}