package org.example;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.types.Row;

public class CDCConnector {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        String sourceDDL = "CREATE TABLE sales (" +
                "id INT NOT NULL, " +
                "shop_id INT, " +
                "product_name STRING, " +
                "quantity INT, " +
                "price DECIMAL(10, 2), " +
                "sale_time TIMESTAMP(3), " +
                "PRIMARY KEY (id) NOT ENFORCED" +
                ") WITH (" +
                "'connector' = 'postgres-cdc', " +
                "'hostname' = 'localhost', " +
                "'port' = '5432', " +
                "'username' = 'postgres', " +
                "'password' = 'password', " +
                "'database-name' = 'cdc_db', " +
                "'schema-name' = 'public', " +
                "'table-name' = 'sales'" +
                ")";

        tableEnv.executeSql(sourceDDL);

        Table salesTable = tableEnv.from("sales");
        Table resultTable = tableEnv.sqlQuery("SELECT * FROM sales WHERE shop_id = 2");

        TableResult result = resultTable.execute();
        result.print();
    }
}
