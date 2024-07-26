package pe.edu.utp.blackdog.model;

import java.time.LocalDateTime;

public class Report_log {
    private long worker_id;
    private long report_id;
    private LocalDateTime date;
    private String state;

    public Report_log(Builder builder) {
        this.worker_id = builder.worker_id;
        this.report_id = builder.report_id;
        this.date = builder.date;
        this.state = builder.state;
    }

    public static class Builder {
        private long worker_id;
        private long report_id;
        private LocalDateTime date;
        private String state;

        public Builder(long worker_id, long report_id, LocalDateTime date, String state) {
            this.worker_id = worker_id;
            this.report_id = report_id;
            this.date = date;
            this.state = state;
        }

        public Report_log build() {
            return new Report_log(this);
        }
    }

    public long getWorker_id() {
        return worker_id;
    }

    public long getReport_id() {
        return report_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    // CREATE REPORT_LOG INSTANCE
    public static Report_log createReport_log(long worker_id, long report_id, LocalDateTime date, String state) {
        return new Report_log.Builder(worker_id, report_id, date, state).build();
    }
}
