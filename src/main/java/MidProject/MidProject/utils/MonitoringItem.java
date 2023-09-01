package MidProject.MidProject.utils;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MonitoringItem {
    private String component;
    private String action;
    private String exception;
    private long duration;
    private String parameters;
}