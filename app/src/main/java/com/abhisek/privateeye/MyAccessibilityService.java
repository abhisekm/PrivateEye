package com.abhisek.privateeye;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {

    static final String TAG = "PrivateEye";

    public MyAccessibilityService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getPackageName().toString().contains("com.google.android.googlequicksearchbox") ||
                event.getPackageName().toString().contains("com.android.systemui"))
            return;


        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
                event.getSource() != null) {
            String msg = getEventDescription(event.getSource());
            if (msg.length() > 0)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

        Log.v(TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [source] %s",
                AccessibilityEvent.eventTypeToString(event.getEventType()), event.getClassName(),
                event.getPackageName(), event.getEventTime(),
                event.getSource() == null ? "" : event.getSource().getClassName()));


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
                node.getContentDescription().toString().trim().length() > 0) {
            sb.append(node.getContentDescription().toString());
            sb.append('\n');
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            sb.append(getEventDescription(node.getChild(i)));
        }

        return sb.toString();
    }


}
