/* Copyright (c) 2010, Carl Burch. License information is located in the
 * com.cburch.logisim.Main source code and at www.cburch.com/logisim/. */

package com.cburch.logisim.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import static com.cburch.logisim.util.LocaleString.*;

@SuppressWarnings("serial")
public abstract class EditPopup extends JPopupMenu {
    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            for (Map.Entry<LogisimMenuItem, JMenuItem> entry : items.entrySet()) {
                if (entry.getValue() == source) {
                    fire(entry.getKey());
                    return;
                }
            }
        }
    }

    private Listener listener;
    private Map<LogisimMenuItem, JMenuItem> items;

    public EditPopup() {
        this(false);
    }

    public EditPopup(boolean waitForInitialize) {
        listener = new Listener();
        items = new HashMap<LogisimMenuItem, JMenuItem>();
        if (!waitForInitialize) initialize();
    }

    protected void initialize() {
        boolean x = false;
        x |= add(LogisimMenuBar.CUT, getFromLocale("editCutItem"));
        x |= add(LogisimMenuBar.COPY, getFromLocale("editCopyItem"));
        if (x) { addSeparator(); x = false; }
        x |= add(LogisimMenuBar.DELETE, getFromLocale("editClearItem"));
        x |= add(LogisimMenuBar.DUPLICATE, getFromLocale("editDuplicateItem"));
        if (x) { addSeparator(); x = false; }
        x |= add(LogisimMenuBar.RAISE, getFromLocale("editRaiseItem"));
        x |= add(LogisimMenuBar.LOWER, getFromLocale("editLowerItem"));
        x |= add(LogisimMenuBar.RAISE_TOP, getFromLocale("editRaiseTopItem"));
        x |= add(LogisimMenuBar.LOWER_BOTTOM, getFromLocale("editLowerBottomItem"));
        if (x) { addSeparator(); x = false; }
        x |= add(LogisimMenuBar.ADD_CONTROL, getFromLocale("editAddControlItem"));
        x |= add(LogisimMenuBar.REMOVE_CONTROL, getFromLocale("editRemoveControlItem"));
        if (!x && getComponentCount() > 0) { remove(getComponentCount() - 1); }
    }

    private boolean add(LogisimMenuItem item, String display) {
        if (shouldShow(item)) {
            JMenuItem menu = new JMenuItem(display);
            items.put(item, menu);
            menu.setEnabled(isEnabled(item));
            menu.addActionListener(listener);
            add(menu);
            return true;
        } else {
            return false;
        }
    }

    protected abstract boolean shouldShow(LogisimMenuItem item);

    protected abstract boolean isEnabled(LogisimMenuItem item);

    protected abstract void fire(LogisimMenuItem item);
}
