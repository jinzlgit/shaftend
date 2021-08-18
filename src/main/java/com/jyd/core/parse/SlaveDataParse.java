package com.jyd.core.parse;

import cn.hutool.core.util.HexUtil;
import com.jyd.entity.SlaveData;
import com.jyd.service.ISlaveDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:28
 */
@Slf4j
@Component("scheduleReport")
public class SlaveDataParse extends AbstractIParsePolicy<SlaveData> {

    private static final String HEART = "心跳";
    private static final String ALARM = "报警";
    private static final String WARNING = "预警";
    private static final String FAULT = "故障";

    @Autowired
    private ISlaveDataService slaveDataService;

    @Override
    public void parse(String msg) {
        SlaveData slaveData = parseHexStrToObject(msg);
        int insert = slaveDataService.insert(slaveData, msg);
        if (insert < 1) {
            log.warn("保存报文:[{}]失败", msg);
        }
    }

    @Override
    protected SlaveData parseHexStrToObject(String content) {
        SlaveData data = new SlaveData();
        data.setHead(content.substring(0, 8));
        data.setLength(HexUtil.toBigInteger(content.substring(8, 12)).toString());
        data.setNumber(content.substring(12, 14));
        data.setVersion(String.valueOf(HexUtil.toBigInteger(content.substring(14, 18)).intValue() / 10));
        data.setMsgType((byte) (HexUtil.toBigInteger(content.substring(18, 20)).intValue()));
        data.setDataType((byte) (HexUtil.toBigInteger(content.substring(20, 22)).intValue()));
        data.setMsgCode(content.substring(22, 24));
        data.setDeviceId(content.substring(24, 28));
        data.setShaftX(content.substring(28, 32));
        data.setShaftY(content.substring(32, 36));
        data.setShaftX(content.substring(36, 40));
        data.setFrequency(HexUtil.toBigInteger(content.substring(40, 44)).toString());
        data.setAccelerationMax(String.valueOf((HexUtil.toBigInteger(content.substring(44, 48)).intValue()) / ONE_HUNDRED));
        data.setSpeedMax(String.valueOf((HexUtil.toBigInteger(content.substring(48, 52)).intValue()) / ONE_THOUSAND));
        data.setDisplacementMax(String.valueOf((HexUtil.toBigInteger(content.substring(52, 56)).intValue()) / ONE_THOUSAND));
        data.setAccelerationValid(String.valueOf((HexUtil.toBigInteger(content.substring(56, 60)).intValue()) / ONE_HUNDRED));
        data.setSpeedValid(String.valueOf((HexUtil.toBigInteger(content.substring(60, 64)).intValue()) / ONE_THOUSAND));
        data.setDisplacementValid(String.valueOf((HexUtil.toBigInteger(content.substring(64, 68)).intValue()) / ONE_THOUSAND));
        data.setTempturePt(String.valueOf((HexUtil.toBigInteger(content.substring(68, 72)).intValue()) * ONE_PERCENT - 20));
        data.setTemptureMlx(String.valueOf((HexUtil.toBigInteger(content.substring(72, 76)).intValue()) * ONE_PERCENT - 20));
        data.setDirection((byte) (HexUtil.toBigInteger(content.substring(76, 78)).intValue()));
        data.setTurnSpeed(String.valueOf(HexUtil.toBigInteger(content.substring(78, 82)).intValue()));
        data.setVoltage(String.valueOf(HexUtil.toBigInteger(content.substring(82, 86)).intValue() / ONE_HUNDRED));
        data.setAlarm(convertAlarm(content.substring(86, 88)));
        data.setRevOne(content.substring(88, 96));
        data.setRevTwo(content.substring(96, 104));
        data.setRevSecond(content.substring(104, 108));
        data.setCrc(content.substring(120, 124));
        return data;
    }

    private String convertAlarm(String alarm) {
        char[] chars = HEX_COMPLEMENT
                .format(Integer.parseInt(Integer.toBinaryString(Integer.parseInt(alarm, 16))))
                .toCharArray();
        StringBuilder sb = new StringBuilder();
        if (chars[0] == '1') {
            sb.append(HEART).append(",");
        }
        if (chars[1] == '1') {
            sb.append(ALARM).append(",");
        }
        if (chars[2] == '1') {
            sb.append(WARNING).append(",");
        }
        if (chars[3] == '1') {
            sb.append(FAULT).append(",");
        }
        return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
    }
}
