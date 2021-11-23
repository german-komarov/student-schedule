package com.german.studentschedule.dto;

public class IncomingLessonDto {
    private String date;
    private Long subject;
    private Long audience;
    private Long group;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getAudience() {
        return audience;
    }

    public void setAudience(Long audience) {
        this.audience = audience;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "IncomingLessonDto{" +
                "date='" + date + '\'' +
                ", subject=" + subject +
                ", audience=" + audience +
                ", group=" + group +
                '}';
    }
}
