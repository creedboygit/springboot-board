package com.creedboy.springbootboard.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import java.util.Locale;
import java.util.Stack;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6SpySqlFormatter {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfiguration.class.getName());
    }

    static public class P6spySqlFormatConfiguration implements MessageFormattingStrategy {

        // 표기에 허용되는 filter
        private static final String ALLOW_FILTER = "com.creed";

        @Override
        public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {

            sql = formatSql(category, sql);
            if (sql.trim().isEmpty()) { // sql 이 없다면 출력하지 않아도 됨
                return "";
            }

            // stack 을 구성하는 Format을 만든다
            return sql + createStack(connectionId, elapsed);
//            return sql + "\n\n";
        }

        private String formatSql(String category, String sql) {

            if (sql == null || sql.trim().isEmpty()) {
                return sql;
            }

            // Only format Statement, distinguish DDL And DML
            if (Category.STATEMENT.getName().equals(category)) {
                String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
                if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                    sql = FormatStyle.BASIC.getFormatter().format(sql);
                } else {
                    sql = FormatStyle.BASIC.getFormatter().format(sql);
                }
//                sql = "|\nFormatSql(P6Spy sql, Hibernate format):" + sql;
                sql = "|\n--------------------------------------" + sql;
            }

            return sql;
        }

        // stack 콘솔 표기
        private String createStack(int connectionId, long elapsed) {
            Stack<String> callStack = new Stack<>();
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();

            for (StackTraceElement stackTraceElement : stackTrace) {
                String trace = stackTraceElement.toString();

                // trace 항목을 보고 내게 맞는 것만 필터
                if (trace.startsWith(ALLOW_FILTER) && !trace.contains("config.P6SpySqlFormatter")) {
                    callStack.push(trace);
                }
            }

            StringBuilder sb = new StringBuilder();
            int order = 1;
            while (!callStack.isEmpty()) {
                sb.append("\n\t\t").append(order++).append(".").append(callStack.pop());
            }

//            return new StringBuffer().append("\n\n\tConnection ID: ").append(connectionId)
//                .append("\n\tExcution Time: ").append(elapsed).append("ms")
//                .append("\n\tCall Stack:").append(sb)
//                .append("\n--------------------------------------")
//                .toString();

            return new StringBuffer().append("\n\n\tCall Stack:").append(sb)
                .append("\n--------------------------------------\n")
                .toString();
        }
    }
}
