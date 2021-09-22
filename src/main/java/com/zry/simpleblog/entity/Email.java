package com.zry.simpleblog.entity;


import java.util.Date;

/**
 * @author zry
 * @ClassName Email.java
 * @Description TODO
 * @createTime 2021年09月21日
 */
public class Email {
        private String from;//邮件发送人
        private String to;//邮件接收人
        private String subject;//邮件主题

        private String text;//邮件内容
        private Date sentDate;//发送时间
        private String error; //报错信息
        private String status; //状态

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getFrom() {
                return from;
        }

        public void setFrom(String from) {
                this.from = from;
        }

        public String getTo() {
                return to;
        }

        public void setTo(String to) {
                this.to = to;
        }

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public Date getSentDate() {
                return sentDate;
        }

        public void setSentDate(Date sentDate) {
                this.sentDate = sentDate;
        }

        public String getError() {
                return error;
        }

        public void setError(String error) {
                this.error = error;
        }

        @Override
        public String toString() {
                return "Email{" +
                        "from='" + from + '\'' +
                        ", to='" + to + '\'' +
                        ", subject='" + subject + '\'' +
                        ", text='" + text + '\'' +
                        ", sentDate=" + sentDate +
                        ", error='" + error + '\'' +
                        ", status='" + status + '\'' +
                        '}';
        }
}
