package pe.edu.utp.blackdog.model;

import java.time.LocalDateTime;

public class Report {
    private long report_id;
    private long customer_order_id;
    private LocalDateTime lastUpdate;
    private String lastState;

    public Report(Builder builder) {
        this.report_id = builder.report_id;
        this.customer_order_id = builder.customer_order_id;
        this.lastUpdate = builder.lastUpdate;
        this.lastState = builder.lastState;
    }

    public static class Builder {
        private long report_id;
        private long customer_order_id;
        private LocalDateTime lastUpdate;
        private String lastState;

        public Builder(long report_id, long customer_order_id, LocalDateTime lastUpdate, String lastState) {
            this.report_id = report_id;
            this.customer_order_id = customer_order_id;
            this.lastUpdate = lastUpdate;
            this.lastState = lastState;
        }

        public Report build() {
            return new Report(this);
        }
    }

    public long getReport_id() {
        return report_id;
    }

    public long getCustomer_order_id() {
        return customer_order_id;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getLastState() {
        return lastState;
    }

    public static Report createReport(long report_id, long customer_order_id, LocalDateTime lastUpdate, String lastState) {
        return new Report.Builder(report_id, customer_order_id, lastUpdate, lastState).build();
    }
}
