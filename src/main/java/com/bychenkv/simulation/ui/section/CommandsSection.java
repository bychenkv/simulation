package com.bychenkv.simulation.ui.section;

import com.bychenkv.simulation.ui.TerminalDisplay;
import com.bychenkv.simulation.ui.UiCommand;
import org.jline.utils.AttributedStyle;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandsSection extends UiSection {
    private static final String COMMANDS_TEXT_DELIMITER = " | ";
    private static final String COMMANDS_TEXT_PREFIX = "Commands: ";

    private final UiCommand[] commands = UiCommand.values();

    public CommandsSection(TerminalDisplay display) {
        super(display);
    }

    public void render() {
        String commandsText = Arrays.stream(commands)
                .map(cmd -> String.format("[%s] %s", cmd.getKey(), cmd.getDescription()))
                .collect(Collectors.joining(COMMANDS_TEXT_DELIMITER, COMMANDS_TEXT_PREFIX, "\n"));

        display.printWithStyle(
                commandsText,
                AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN)
        );
    }
}
