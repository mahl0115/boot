package com.hl.boot.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.hl.boot.util.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
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
    /*@RequestMapping("/updateLoggerLevel4Log4j2")
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
    }*/

    /**
     * 修改logger打印级别(logback)
     *
     * @param loggerName 目标logger名称
     * @param level      目标级别
     */
    @RequestMapping("/updateLoggerLevel4Logback")
    public Object updateLoggerLevel4Logback(String loggerName, String level) {
        ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();

        loggerList.stream()
                  .filter(logger -> logger.getName().equals(loggerName))
                  .filter(logger -> Objects.nonNull(logger.getLevel()))
                  .forEach(logger -> {
                      Level targetLevel = Level.toLevel(level);
                      logger.setLevel(targetLevel);
                  });
        return loggerList.stream()
                         .filter(logger -> Objects.nonNull(logger.getLevel()))
                         .collect(Collectors.toMap(x -> x.getName(), x -> x.getLevel().levelStr));
    }

    @RequestMapping("/getLoggers")
    public Object getLoggers() {
        ch.qos.logback.classic.LoggerContext loggerContext = (ch.qos.logback.classic.LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();
        return loggerList.stream()
                         .filter(logger -> Objects.nonNull(logger.getLevel()))
                         .collect(Collectors.toMap(x -> x.getName(), x -> x.getLevel().levelStr));
    }
}
