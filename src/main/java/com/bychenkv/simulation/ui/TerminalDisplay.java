package com.bychenkv.simulation.ui;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp;

import java.io.PrintWriter;

public class TerminalDisplay {
    private final Terminal terminal;
    private final PrintWriter writer;

    public TerminalDisplay(Terminal terminal) {
        this.terminal = terminal;
        this.writer = terminal.writer();
    }

    public void moveCursorTo(int row, int col) {
        terminal.puts(InfoCmp.Capability.cursor_address, row, col);
        terminal.flush();
    }

    public void clearLine(int lineNum) {
        moveCursorTo(lineNum, 0);

        terminal.puts(InfoCmp.Capability.carriage_return);
        terminal.puts(InfoCmp.Capability.clr_eol);
        terminal.flush();
    }

    public void clear() {
        writer.print("\033[H\033[2J");
        writer.flush();
    }

    public void printWithStyle(String text, AttributedStyle style) {
        writer.print(new AttributedString(text, style).toAnsi(terminal));
    }

    public void println(String text) {
        writer.println(text);
    }

    public void flush() {
        writer.flush();
    }

    public void hideCursor() {
        writer.print("\033[?25l");
        writer.flush();
    }

    public void showCursor() {
        writer.print("\033[?25h");
        writer.flush();
    }
}
