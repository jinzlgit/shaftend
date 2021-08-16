package com.jyd.entity;

import java.io.Serializable;
import java.util.Date;

public class SlaveData extends BaseEntity implements Serializable {
    private Integer slaveId;

    private Integer msgId;

    private String head;

    private String length;

    private String number;

    private String version;

    private Byte msgType;

    private Byte dataType;

    private String msgCode;

    private String deviceId;

    private String shaftX;

    private String shaftY;

    private String shaftZ;

    private String frequency;

    private String accelerationMax;

    private String speedMax;

    private String displacementMax;

    private String accelerationValid;

    private String speedValid;

    private String displacementValid;

    private String tempturePt;

    private String temptureMlx;

    private Byte direction;

    private String turnSpeed;

    private String voltage;

    private String alarm;

    private String revOne;

    private String revTwo;

    private String revSecond;

    private String crc;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public SlaveData(Integer slaveId, Integer msgId, String head, String length, String number, String version, Byte msgType, Byte dataType, String msgCode, String deviceId, String shaftX, String shaftY, String shaftZ, String frequency, String accelerationMax, String speedMax, String displacementMax, String accelerationValid, String speedValid, String displacementValid, String tempturePt, String temptureMlx, Byte direction, String turnSpeed, String voltage, String alarm, String revOne, String revTwo, String revSecond, String crc, Date createTime) {
        this.slaveId = slaveId;
        this.msgId = msgId;
        this.head = head;
        this.length = length;
        this.number = number;
        this.version = version;
        this.msgType = msgType;
        this.dataType = dataType;
        this.msgCode = msgCode;
        this.deviceId = deviceId;
        this.shaftX = shaftX;
        this.shaftY = shaftY;
        this.shaftZ = shaftZ;
        this.frequency = frequency;
        this.accelerationMax = accelerationMax;
        this.speedMax = speedMax;
        this.displacementMax = displacementMax;
        this.accelerationValid = accelerationValid;
        this.speedValid = speedValid;
        this.displacementValid = displacementValid;
        this.tempturePt = tempturePt;
        this.temptureMlx = temptureMlx;
        this.direction = direction;
        this.turnSpeed = turnSpeed;
        this.voltage = voltage;
        this.alarm = alarm;
        this.revOne = revOne;
        this.revTwo = revTwo;
        this.revSecond = revSecond;
        this.crc = crc;
        this.createTime = createTime;
    }

    public SlaveData() {
        super();
    }

    public Integer getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(Integer slaveId) {
        this.slaveId = slaveId;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length == null ? null : length.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public Byte getDataType() {
        return dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getShaftX() {
        return shaftX;
    }

    public void setShaftX(String shaftX) {
        this.shaftX = shaftX == null ? null : shaftX.trim();
    }

    public String getShaftY() {
        return shaftY;
    }

    public void setShaftY(String shaftY) {
        this.shaftY = shaftY == null ? null : shaftY.trim();
    }

    public String getShaftZ() {
        return shaftZ;
    }

    public void setShaftZ(String shaftZ) {
        this.shaftZ = shaftZ == null ? null : shaftZ.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getAccelerationMax() {
        return accelerationMax;
    }

    public void setAccelerationMax(String accelerationMax) {
        this.accelerationMax = accelerationMax == null ? null : accelerationMax.trim();
    }

    public String getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(String speedMax) {
        this.speedMax = speedMax == null ? null : speedMax.trim();
    }

    public String getDisplacementMax() {
        return displacementMax;
    }

    public void setDisplacementMax(String displacementMax) {
        this.displacementMax = displacementMax == null ? null : displacementMax.trim();
    }

    public String getAccelerationValid() {
        return accelerationValid;
    }

    public void setAccelerationValid(String accelerationValid) {
        this.accelerationValid = accelerationValid == null ? null : accelerationValid.trim();
    }

    public String getSpeedValid() {
        return speedValid;
    }

    public void setSpeedValid(String speedValid) {
        this.speedValid = speedValid == null ? null : speedValid.trim();
    }

    public String getDisplacementValid() {
        return displacementValid;
    }

    public void setDisplacementValid(String displacementValid) {
        this.displacementValid = displacementValid == null ? null : displacementValid.trim();
    }

    public String getTempturePt() {
        return tempturePt;
    }

    public void setTempturePt(String tempturePt) {
        this.tempturePt = tempturePt == null ? null : tempturePt.trim();
    }

    public String getTemptureMlx() {
        return temptureMlx;
    }

    public void setTemptureMlx(String temptureMlx) {
        this.temptureMlx = temptureMlx == null ? null : temptureMlx.trim();
    }

    public Byte getDirection() {
        return direction;
    }

    public void setDirection(Byte direction) {
        this.direction = direction;
    }

    public String getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(String turnSpeed) {
        this.turnSpeed = turnSpeed == null ? null : turnSpeed.trim();
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage == null ? null : voltage.trim();
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm == null ? null : alarm.trim();
    }

    public String getRevOne() {
        return revOne;
    }

    public void setRevOne(String revOne) {
        this.revOne = revOne == null ? null : revOne.trim();
    }

    public String getRevTwo() {
        return revTwo;
    }

    public void setRevTwo(String revTwo) {
        this.revTwo = revTwo == null ? null : revTwo.trim();
    }

    public String getRevSecond() {
        return revSecond;
    }

    public void setRevSecond(String revSecond) {
        this.revSecond = revSecond == null ? null : revSecond.trim();
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc == null ? null : crc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", slaveId=").append(slaveId);
        sb.append(", msgId=").append(msgId);
        sb.append(", head=").append(head);
        sb.append(", length=").append(length);
        sb.append(", number=").append(number);
        sb.append(", version=").append(version);
        sb.append(", msgType=").append(msgType);
        sb.append(", dataType=").append(dataType);
        sb.append(", msgCode=").append(msgCode);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", shaftX=").append(shaftX);
        sb.append(", shaftY=").append(shaftY);
        sb.append(", shaftZ=").append(shaftZ);
        sb.append(", frequency=").append(frequency);
        sb.append(", accelerationMax=").append(accelerationMax);
        sb.append(", speedMax=").append(speedMax);
        sb.append(", displacementMax=").append(displacementMax);
        sb.append(", accelerationValid=").append(accelerationValid);
        sb.append(", speedValid=").append(speedValid);
        sb.append(", displacementValid=").append(displacementValid);
        sb.append(", tempturePt=").append(tempturePt);
        sb.append(", temptureMlx=").append(temptureMlx);
        sb.append(", direction=").append(direction);
        sb.append(", turnSpeed=").append(turnSpeed);
        sb.append(", voltage=").append(voltage);
        sb.append(", alarm=").append(alarm);
        sb.append(", revOne=").append(revOne);
        sb.append(", revTwo=").append(revTwo);
        sb.append(", revSecond=").append(revSecond);
        sb.append(", crc=").append(crc);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SlaveData other = (SlaveData) that;
        return (this.getSlaveId() == null ? other.getSlaveId() == null : this.getSlaveId().equals(other.getSlaveId()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getHead() == null ? other.getHead() == null : this.getHead().equals(other.getHead()))
            && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()))
            && (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getMsgType() == null ? other.getMsgType() == null : this.getMsgType().equals(other.getMsgType()))
            && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
            && (this.getMsgCode() == null ? other.getMsgCode() == null : this.getMsgCode().equals(other.getMsgCode()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getShaftX() == null ? other.getShaftX() == null : this.getShaftX().equals(other.getShaftX()))
            && (this.getShaftY() == null ? other.getShaftY() == null : this.getShaftY().equals(other.getShaftY()))
            && (this.getShaftZ() == null ? other.getShaftZ() == null : this.getShaftZ().equals(other.getShaftZ()))
            && (this.getFrequency() == null ? other.getFrequency() == null : this.getFrequency().equals(other.getFrequency()))
            && (this.getAccelerationMax() == null ? other.getAccelerationMax() == null : this.getAccelerationMax().equals(other.getAccelerationMax()))
            && (this.getSpeedMax() == null ? other.getSpeedMax() == null : this.getSpeedMax().equals(other.getSpeedMax()))
            && (this.getDisplacementMax() == null ? other.getDisplacementMax() == null : this.getDisplacementMax().equals(other.getDisplacementMax()))
            && (this.getAccelerationValid() == null ? other.getAccelerationValid() == null : this.getAccelerationValid().equals(other.getAccelerationValid()))
            && (this.getSpeedValid() == null ? other.getSpeedValid() == null : this.getSpeedValid().equals(other.getSpeedValid()))
            && (this.getDisplacementValid() == null ? other.getDisplacementValid() == null : this.getDisplacementValid().equals(other.getDisplacementValid()))
            && (this.getTempturePt() == null ? other.getTempturePt() == null : this.getTempturePt().equals(other.getTempturePt()))
            && (this.getTemptureMlx() == null ? other.getTemptureMlx() == null : this.getTemptureMlx().equals(other.getTemptureMlx()))
            && (this.getDirection() == null ? other.getDirection() == null : this.getDirection().equals(other.getDirection()))
            && (this.getTurnSpeed() == null ? other.getTurnSpeed() == null : this.getTurnSpeed().equals(other.getTurnSpeed()))
            && (this.getVoltage() == null ? other.getVoltage() == null : this.getVoltage().equals(other.getVoltage()))
            && (this.getAlarm() == null ? other.getAlarm() == null : this.getAlarm().equals(other.getAlarm()))
            && (this.getRevOne() == null ? other.getRevOne() == null : this.getRevOne().equals(other.getRevOne()))
            && (this.getRevTwo() == null ? other.getRevTwo() == null : this.getRevTwo().equals(other.getRevTwo()))
            && (this.getRevSecond() == null ? other.getRevSecond() == null : this.getRevSecond().equals(other.getRevSecond()))
            && (this.getCrc() == null ? other.getCrc() == null : this.getCrc().equals(other.getCrc()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSlaveId() == null) ? 0 : getSlaveId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getHead() == null) ? 0 : getHead().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getMsgType() == null) ? 0 : getMsgType().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getMsgCode() == null) ? 0 : getMsgCode().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getShaftX() == null) ? 0 : getShaftX().hashCode());
        result = prime * result + ((getShaftY() == null) ? 0 : getShaftY().hashCode());
        result = prime * result + ((getShaftZ() == null) ? 0 : getShaftZ().hashCode());
        result = prime * result + ((getFrequency() == null) ? 0 : getFrequency().hashCode());
        result = prime * result + ((getAccelerationMax() == null) ? 0 : getAccelerationMax().hashCode());
        result = prime * result + ((getSpeedMax() == null) ? 0 : getSpeedMax().hashCode());
        result = prime * result + ((getDisplacementMax() == null) ? 0 : getDisplacementMax().hashCode());
        result = prime * result + ((getAccelerationValid() == null) ? 0 : getAccelerationValid().hashCode());
        result = prime * result + ((getSpeedValid() == null) ? 0 : getSpeedValid().hashCode());
        result = prime * result + ((getDisplacementValid() == null) ? 0 : getDisplacementValid().hashCode());
        result = prime * result + ((getTempturePt() == null) ? 0 : getTempturePt().hashCode());
        result = prime * result + ((getTemptureMlx() == null) ? 0 : getTemptureMlx().hashCode());
        result = prime * result + ((getDirection() == null) ? 0 : getDirection().hashCode());
        result = prime * result + ((getTurnSpeed() == null) ? 0 : getTurnSpeed().hashCode());
        result = prime * result + ((getVoltage() == null) ? 0 : getVoltage().hashCode());
        result = prime * result + ((getAlarm() == null) ? 0 : getAlarm().hashCode());
        result = prime * result + ((getRevOne() == null) ? 0 : getRevOne().hashCode());
        result = prime * result + ((getRevTwo() == null) ? 0 : getRevTwo().hashCode());
        result = prime * result + ((getRevSecond() == null) ? 0 : getRevSecond().hashCode());
        result = prime * result + ((getCrc() == null) ? 0 : getCrc().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}