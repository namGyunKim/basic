package org.example.basic.global.config.p6spy;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        sql = formatSql(category, sql);
        Date currentDate = new Date();

        SimpleDateFormat format1 = new SimpleDateFormat("yy.MM.dd HH:mm:ss");

        // 수정된 부분: 로그 메시지 포맷을 개선했습니다. 날짜와 실행 시간을 포함하고, SQL을 보기 좋게 포맷팅합니다.
        return String.format("%s | OperationTime: %dms | Category: %s | Connection %d\nFormatted SQL: %s",
                format1.format(currentDate), elapsed, category, connectionId, sql);
    }

    private String formatSql(String category, String sql) {
        if (sql == null || sql.trim().isEmpty()) return sql;

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            String tmpSql = sql.trim().toLowerCase(Locale.ROOT);
            if (tmpSql.startsWith("create") || tmpSql.startsWith("alter") || tmpSql.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
            // 수정된 부분: 포맷팅된 SQL 앞에 추가적인 정보를 제거하고 SQL만을 깔끔하게 표시합니다.
            sql = "\n" + sql;
        }

        return sql;
    }
}
