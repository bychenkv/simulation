package com.bychenkv.simulation.services.logger;

import org.jline.utils.AttributedStyle;

public enum LogLevel {
    DEBUG("DEBUG", AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN)),
    INFO("INFO", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)),
    WARNING("WARN", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)),
    ERROR("ERROR", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));

    private final String prefix;
    private final AttributedStyle style;

    LogLevel(String prefix, AttributedStyle style) {
        this.prefix = prefix;
        this.style = style;
    }

    public String getPrefix() {
        return prefix;
    }

    public AttributedStyle getStyle() {
        return style;
    }
}
