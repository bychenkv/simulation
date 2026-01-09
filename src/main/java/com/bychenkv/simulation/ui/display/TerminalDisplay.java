package com.bychenkv.simulation.ui.display;

import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp;

import java.io.PrintWriter;

public class TerminalDisplay implements Display {
    private final Terminal terminal;

    public TerminalDisplay(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public void moveCursorTo(UiLayoutPosition position) {
        terminal.puts(
                InfoCmp.Capability.cursor_address,
                position.row(),
                position.column()
        );
        terminal.flush();
    }

    @Override
    public void clearLine(int lineNum) {
        moveCursorTo(new UiLayoutPosition(lineNum));
        terminal.puts(InfoCmp.Capability.carriage_return);
        terminal.puts(InfoCmp.Capability.clr_eol);
        terminal.flush();
    }

    @Override
    public void clear() {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.flush();
    }

    @Override
    public void printWithStyle(String text, AttributedStyle style) {
        terminal.writer()
                .print(new AttributedString(text, style).toAnsi(terminal));
        terminal.flush();
    }

    @Override
    public void print(String text) {
        terminal.writer()
                .print(text);
        terminal.flush();
    }

    @Override
    public void println(String text) {
        terminal.writer()
                .println(text);
        terminal.flush();
    }

    @Override
    public void showCursor() {
        terminal.puts(InfoCmp.Capability.cursor_normal);
        terminal.flush();
    }

    @Override
    public void hideCursor() {
        terminal.puts(InfoCmp.Capability.cursor_invisible);
        terminal.flush();
    }
}
