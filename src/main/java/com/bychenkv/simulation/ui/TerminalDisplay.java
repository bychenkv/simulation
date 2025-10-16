package com.bychenkv.simulation.ui;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.io.PrintWriter;

public class TerminalDisplay {
    private final Terminal terminal;
    private final PrintWriter writer;

    public TerminalDisplay(Terminal terminal) {
        this.terminal = terminal;
        this.writer = terminal.writer();
    }

    public void clear() {
        writer.print("\033[H\033[2J");
        writer.flush();
    }

    public void flush() {
        writer.flush();
    }

    public void moveCursorHome() {
        writer.print("\033[H");
    }

    public void printWithStyle(String text, AttributedStyle style) {
        writer.print(new AttributedString(text, style).toAnsi(terminal));
    }

    public void println(String text) {
        writer.println(text);
    }

    public void clearToEndOfScreen() {
        writer.print("\033[J");
    }
}
