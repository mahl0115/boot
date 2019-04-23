package com.hl.boot.controller;

import com.hl.boot.util.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mahl
 * @date 2019-04-23
 */
@Slf4j
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        LoggerUtils.ERROR_LOGGER.info("TestController.test ---------------info log");
        return "success";
    }

    /**
     * 修改logger打印级别(log4j2)
     *
     * @param loggerName 目标logger名称
     * @param level      目标级别
     */
    @RequestMapping("/updateLoggerLevel4Log4j2")
    public Object updateLoggerLevel4Log4j2(String loggerName, String level) {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Map<String, LoggerConfig> map = loggerContext.getConfiguration().getLoggers();

        LoggerConfig loggerConfig = null;
        if (map.containsKey(loggerName)) {
            loggerConfig = map.get(loggerName);
        } else if ("root".equals(loggerName)) {
            loggerConfig = map.get("");
        } else {
            throw new RuntimeException("LoggerName not exist!");
        }
        Level targetLevel = Level.toLevel(level);
        loggerConfig.setLevel(targetLevel);
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        // This causes all Loggers to refetch information from their LoggerConfig.
        ctx.updateLoggers();

        return map.entrySet().stream()
                  .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().getLevel().name()));
    }

    @RequestMapping("/getLoggers")
    public Object getLoggers() {
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Map<String, LoggerConfig> map = loggerContext.getConfiguration().getLoggers();
        return map.entrySet().stream()
                  .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().getLevel().name()));
    }
}
