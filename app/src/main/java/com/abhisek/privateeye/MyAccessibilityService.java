package com.abhisek.privateeye;

import android.accessibilityservice.AccessibilityService;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {

    public MyAccessibilityService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getSource()!= null)
        {
            Toast.makeText(this, getEventDescription(event.getSource()),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInterrupt() {

    }

    /**
     * Recursively traverse a node to find content description
     *
     * @param node next child node
     * @return Content Description if the view has one
     */
    private String getEventDescription(AccessibilityNodeInfo node) {
        StringBuilder sb = new StringBuilder();
        if (node.getContentDescription() != null &&
                node.getContentDescription().toString().trim().length() > 0)
            sb.append(node.getContentDescription().toString());

        for(int i=0;i<node.getChildCount();i++){
            sb.append(getEventDescription(node.getChild(i)));
        }

        sb.append("\n\n");

        return sb.toString();
    }


}
