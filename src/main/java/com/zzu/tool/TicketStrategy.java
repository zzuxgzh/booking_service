package com.zzu.tool;

import java.math.BigDecimal;

public class TicketStrategy {

    //根据买票人和给谁买票的身份判断每人的价格
    public static BigDecimal getTicketPriceBySttus(BigDecimal basic,int a,int b){
        BigDecimal zhe = basic.multiply(new BigDecimal(0.1));
        if (a>=8){//购买者是导游，只优惠一次，九折
            basic = basic.subtract(zhe);
            return basic;
        }
        if (a>=4){//会员
            basic = basic.subtract(zhe);
            a = a-4;
        }
        if (a>=2){//小孩
            basic = basic.subtract(zhe);
            a=a-2;
        }
        if (a>=1){//学生
            basic = basic.subtract(zhe);
            a=a-1;
        }
        return basic;
    }

}
