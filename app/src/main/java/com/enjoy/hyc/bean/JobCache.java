package com.enjoy.hyc.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by hyc on 2017/4/26 09:52
 */

public class JobCache extends DataSupport {

    private String objectId;
    /**
     * 月结方式
     */
    public static final String MONTHTHLY="月结";
    /**
     * 日结方式
     */
    public static final String DAYLY="日结";
    /**
     * 工作所在城市名
     */
    private String cityName;
    /**
     * 工作发布者
     */
    private String publisher;
    /**
     * 联系电话
     */
    private String contactNumber;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 工作类型
     */
    private String jobType;
    /**
     * 工作全名
     */
    private String jobDetailName;
    /**
     * 工作薪资
     */
    private int jobSalary;
    /**
     * 招聘人数
     */
    private int recruitmentNumber;
    /**
     * 工作区域
     */
    private String jobRegion;
    /**
     * 报名截止时间
     */
    private String deadline;
    /**
     * 工作描述
     */
    private String jobDescribe;
    /**
     * 工作要求
     */
    private String jobDemand;
    /**
     * 结算方式
     */
    private String balanceMode;
    /**
     * 工作时段
     */
    private String workInterval;
    /**
     * 日工作时长
     */
    private int workDayTime;

    private String time;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobDetailName() {
        return jobDetailName;
    }

    public void setJobDetailName(String jobDetailName) {
        this.jobDetailName = jobDetailName;
    }

    public int getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(int jobSalary) {
        this.jobSalary = jobSalary;
    }

    public int getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public void setRecruitmentNumber(int recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public String getJobRegion() {
        return jobRegion;
    }

    public void setJobRegion(String jobRegion) {
        this.jobRegion = jobRegion;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getJobDescribe() {
        return jobDescribe;
    }

    public void setJobDescribe(String jobDescribe) {
        this.jobDescribe = jobDescribe;
    }

    public String getJobDemand() {
        return jobDemand;
    }

    public void setJobDemand(String jobDemand) {
        this.jobDemand = jobDemand;
    }

    public String getBalanceMode() {
        return balanceMode;
    }

    public void setBalanceMode(String balanceMode) {
        this.balanceMode = balanceMode;
    }

    public String getWorkInterval() {
        return workInterval;
    }

    public void setWorkInterval(String workInterval) {
        this.workInterval = workInterval;
    }

    public int getWorkDayTime() {
        return workDayTime;
    }

    public void setWorkDayTime(int workTime) {
        this.workDayTime = workTime;
    }

    public JobCache(Job job){
        this.cityName=job.getCityName();
        this.balanceMode=job.getBalanceMode();
        this.publisher=job.getPublisher();
        this.contactNumber=job.getContactNumber();
        this.contactAddress=job.getContactAddress();
        this.jobType=job.getJobType();
        this.jobDetailName=job.getJobDetailName();
        this.jobSalary=job.getJobSalary();
        this.recruitmentNumber=job.getRecruitmentNumber();
        this.jobRegion=job.getJobRegion();
        this.deadline=job.getDeadline();
        this.jobDescribe=job.getJobDescribe();
        this.jobDemand=job.getJobDemand();
        this.workInterval=job.getWorkInterval();
        this.workDayTime=job.getWorkDayTime();
        this.objectId=job.getObjectId();
    }

    public Job toJob(){
        Job job=new Job();
        job.setCityName(cityName);
        job.setBalanceMode(balanceMode);
        job.setPublisher(publisher);
        job.setContactNumber(contactNumber);
        job.setContactAddress(contactAddress);
        job.setJobType(jobType);
        job.setJobDetailName(jobDetailName);
        job.setJobSalary(jobSalary);
        job.setRecruitmentNumber(recruitmentNumber);
        job.setJobRegion(jobRegion);
        job.setDeadline(deadline);
        job.setJobDescribe(jobDescribe);
        job.setJobDemand(jobDemand);
        job.setWorkInterval(job.getWorkInterval());
        job.setWorkDayTime(job.getWorkDayTime());
        job.setObjectId(objectId);
        return job;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
