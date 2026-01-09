package com.bychenkv.simulation.ui.display;

import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.utils.AttributedStyle;

public interface Display {
    void moveCursorTo(UiLayoutPosition position);
    void clearLine(int lineNum);
    void clear();
    void printWithStyle(String text, AttributedStyle style);
    void println(String text);
    void print(String text);
    void hideCursor();
    void showCursor();
}
