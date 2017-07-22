package com.ipfaffen.pomodoro.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author Isaias Pfaffenseller
 */
@Entity
public class Pomodoro {

    @Id
    @Property(nameInDb = "id")
    private Long id;

    @NotNull
    @Property(nameInDb = "type")
    private String type;

    @NotNull
    @Property(nameInDb = "state")
    private String state;

    @NotNull
    @Property(nameInDb = "start_date")
    private Date startDate;

    @Property(nameInDb = "end_date")
    private Date endDate;

    @Property(nameInDb = "used_time_millis")
    private Long usedTimeMillis;

    @Generated(hash = 754847468)
    public Pomodoro(Long id, @NotNull String type, @NotNull String state,
            @NotNull Date startDate, Date endDate, Long usedTimeMillis) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usedTimeMillis = usedTimeMillis;
    }

    @Generated(hash = 1864521524)
    public Pomodoro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getUsedTimeMillis() {
        return usedTimeMillis;
    }

    public void setUsedTimeMillis(Long usedTimeMillis) {
        this.usedTimeMillis = usedTimeMillis;
    }
}