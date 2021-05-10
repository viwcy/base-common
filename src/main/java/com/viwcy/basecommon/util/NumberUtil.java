package com.viwcy.basecommon.util;

import com.viwcy.basecommon.constant.NumberEnum;
import com.viwcy.basecommon.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Random;

/**
 * TODO //、、订单编码生成器
 *
 * <p> Title: NumberUtil </p>
 * <p> Description: NumberUtil </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
@Component
public class NumberUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public NumberUtil() {
    }

    /**
     * 时间模板(年月日)，线程安全
     */
    private static final FastDateFormat ORDER_FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

    /**
     * 时间模板(年月日时分秒毫秒)，线程安全
     */
    private static final FastDateFormat TRADING_FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");

    /**
     * 随机数组
     */
    private static final String[] temp = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * 品牌编码前缀
     */
    private static final String BRAND_PREFIX = "B";

    /**
     * 品类编码前缀
     */
    private static final String CLASSIFY_PREFIX = "C";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * TODO 生成订单编号(线程安全)，如:ES2020082700000001856236。规则：prefix + 当前日期(年月日) + 顺势编号(00000001) + 随机macLength位的MAC地址的ASCII编码
     *
     * @param prefix    前缀
     * @param value     1:千万，2:百万，3:十万，4:万，5:千
     * @param macLength mac编码的长度，一般取值4或6
     */
    public synchronized String orderNumber(String prefix, int value, int macLength) {
        String result;
        Object obj = redisTemplate.opsForValue().get(NumberEnum.getName(value) + ":");

        String formatDate = ORDER_FAST_DATE_FORMAT.format(new Date());
        String macNo = "";
        try {
            macNo = macNo(macLength);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != obj) {
            String number;
            if (StringUtils.isBlank(prefix)) {
                number = (String) obj;
            } else {
                number = ((String) obj).substring(prefix.length());
            }

            //日期
            String date = number.substring(0, 8);

            if (ORDER_FAST_DATE_FORMAT.format(new Date()).equals(date)) {
                if (StringUtils.isBlank(prefix)) {
                    result = formatDate + serialNo(number, value) + macNo;
                } else {
                    result = prefix + formatDate + serialNo(number, value) + macNo;
                }
            } else {
                if (StringUtils.isBlank(prefix)) {
                    result = formatDate + NumberEnum.getNo(value) + macNo;
                } else {
                    result = prefix + formatDate + NumberEnum.getNo(value) + macNo;
                }
            }
        } else {
            if (StringUtils.isBlank(prefix)) {
                result = formatDate + NumberEnum.getNo(value) + macNo;
            } else {
                result = prefix + formatDate + NumberEnum.getNo(value) + macNo;
            }
        }
        redisTemplate.opsForValue().set(NumberEnum.getName(value) + ":", result);
        return result;
    }

    /**
     * TODO 生成交易流水号
     *
     * @param length 总长度，>=18，因为日期长度已经是17了，建议位数在25左右
     */
    public String tradingNumber(int length) {
        if (length < 18) {
            throw new BaseException("The length value cannot be less than 18");
        }
        return TRADING_FAST_DATE_FORMAT.format(new Date()) + randomNumber(length - 17);
    }

    /**
     * TODO 生成length位品牌编码
     *
     * @param prefix 前缀
     * @param length 长度
     */
    public String brandNumber(String prefix, int length) {
        if (StringUtils.isBlank(prefix)) {
            return BRAND_PREFIX + randomNumber(length - 1);
        } else if (prefix.length() >= length) {
            //prefix长度不能大于等于length值
            throw new BaseException("The prefix length must not be greater than or equal to the length value.");
        } else {
            return prefix + randomNumber(length - prefix.length());
        }
    }

    /**
     * TODO 生成length位品类编码
     *
     * @param prefix 前缀
     * @param length 长度
     */
    public String classifyNumber(String prefix, int length) {
        if (StringUtils.isBlank(prefix)) {
            return CLASSIFY_PREFIX + randomNumber(length - 1);
        } else if (prefix.length() >= length) {
            //prefix长度不能大于等于length值
            throw new BaseException("The prefix length must not be greater than or equal to the length value.");
        } else {
            return prefix + randomNumber(length - prefix.length());
        }
    }

    /**
     * TODO 生成商品编号
     *
     * @param prefix 前缀
     * @param length 长度
     */
    public String goodsNumber(String prefix, int length) {
        if (StringUtils.isBlank(prefix)) {
            return randomNumber(length);
        } else if (prefix.length() >= length) {
            //prefix长度不能大于等于length值
            throw new BaseException("The prefix length must not be greater than or equal to the length value.");
        } else {
            return prefix + randomNumber(length);
        }
    }

    /**
     * TODO 生成length位随机数(纯数字)
     *
     * @param length 长度
     */
    public String randomNumber(int length) {
        if (length <= 0) {
            //length的值不能小于等于0
            throw new BaseException("The length value cannot be less than or equal to 0");
        } else {
            Random random = new Random();
            StringBuffer sb = new StringBuffer();

            for (int i = 1; i <= length; i++) {
                //random.nextInt()->[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n
                String s = temp[random.nextInt(10)];
                sb.append(s);
            }

            return sb.toString();
        }
    }

    /**
     * TODO 根据当前number和type，生成序列号
     *
     * @param number 传入的序列号，如：202009180000018285（纯数字，不能带前缀）
     * @param value  1:千万，2:百万，3:十万，4:万，5:千
     */
    private static String serialNo(String number, int value) {
        if (value >= NumberEnum.TEN_MILLION.getValue() && value <= NumberEnum.ONE_THOUSAND.getValue()) {
            String substring = number.substring(8, 17 - value);
            return convertValue(9 - value, Long.parseLong(substring));
        } else {
            throw new BaseException("The value is not supported，value = [1,2,3,4,5]");
        }
    }

    /**
     * TODO 补零操作。根据传入的值进行加1操作，然后补0返回
     *
     * @param length 长度
     * @param value  序列号
     */
    private static String convertValue(int length, long value) {
        String valueStr = String.valueOf(value + 1);//3
        int valueStrLength = valueStr.length();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length - valueStrLength; i++) {
            builder.append("0");
        }

        //000003
        return builder.append(valueStr).toString();
    }


    /**
     * TODO MAC地址转换ASCII编码，并随即取出n个。MAC: 5CC5D4B0EDC0，对应ASCII编码: 536767536852664869686748
     *
     * @param length 生成Mac编码长度
     */
    private static String macNo(int length) throws Exception {
        //获取本机MAC地址，如：5CC5D4B0EDC0
        InetAddress inetAddress = InetAddress.getLocalHost();
        byte[] macByte = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        String macAddress = DatatypeConverter.printHexBinary(macByte);

        //MAC地址转换成ASCII编码，如：536767536852664869686748
        StringBuffer temp = new StringBuffer();
        char[] chars1 = macAddress.toCharArray();
        for (int i = 0; i < chars1.length; i++) {
            temp.append((int) chars1[i]);
        }

        if (chars1.length < length) {
            throw new BaseException("The value length must not be greater than the MAC address length = " + chars1.length);
        } else {
            //随机从MAC地址的ASCII编码中取值length位
            char[] chars2 = temp.toString().toCharArray();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int index = (int) (Math.random() * chars2.length);
                stringBuffer.append(chars2[index]);
            }
            return stringBuffer.toString();
        }
    }

}
