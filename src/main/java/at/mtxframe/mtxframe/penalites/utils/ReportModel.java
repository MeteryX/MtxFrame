package at.mtxframe.mtxframe.penalites.utils;

import org.bukkit.entity.Player;

import java.util.Date;

public class ReportModel {

    private String reportTitle;
    private String reportComment;
    private ReportReason reportReason;
    private Date reportDate;
    private Boolean isWorkedOn;


    private Player reportedPlayer;


    public ReportModel(String reportTitle, String reportComment, ReportReason reportReason, Date reportDate, Boolean isWorkedOn, Player reportedPlayer) {
        this.reportTitle = reportTitle;
        this.reportComment = reportComment;
        this.reportReason = reportReason;
        this.reportDate = reportDate;
        this.isWorkedOn = isWorkedOn;


        this.reportedPlayer = reportedPlayer;
    }








    public String getReportTitle() {
        return reportTitle;
    }
    public String getReportComment() {
        return reportComment;}
    public ReportReason getReportReason() {
        return reportReason;
    }
    public Date getReportDate() {
        return reportDate;
    }
    public Boolean getIsWorkedOn() {
        return isWorkedOn;
    }

}
