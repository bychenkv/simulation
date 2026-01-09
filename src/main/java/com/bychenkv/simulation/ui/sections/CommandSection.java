package com.bychenkv.simulation.ui.sections;

import com.bychenkv.simulation.ui.display.Display;
import com.bychenkv.simulation.ui.command.UiCommand;
import com.bychenkv.simulation.ui.layout.UiLayoutPosition;
import org.jline.utils.AttributedStyle;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandSection implements UiSection {
    private static final String COMMANDS_TEXT_DELIMITER = " | ";
    private static final String COMMANDS_TEXT_PREFIX = "Commands: ";
    private static final int HEIGHT = 1;
    private static final UiCommand[] COMMANDS = UiCommand.values();

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void renderAt(UiLayoutPosition position, Display display) {
        display.clearLine(position.row());

        String commandsText = Arrays.stream(COMMANDS)
                .map(cmd -> String.format("[%s] %s", cmd.getKey(), cmd.getDescription()))
                .collect(Collectors.joining(COMMANDS_TEXT_DELIMITER, COMMANDS_TEXT_PREFIX, "\n"));

        display.printWithStyle(
                commandsText,
                AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN)
        );
    }
}
