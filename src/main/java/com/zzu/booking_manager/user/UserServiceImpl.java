package com.zzu.booking_manager.user;

import com.zzu.booking_manager.user.userservice.IUserService;
import com.zzu.dao.IStaffDao;
import com.zzu.dao.IUserDao;
import com.zzu.entity.Staff;
import com.zzu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service

public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao iud;
    @Override
    public List<User> getAllUsers() {
        return iud.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return iud.getUserById(id);
    }

    @Override
    public String insertSelective(User user) {
        if(!isMobileNO(user.getTel())){
            return "手机号格式错误";
        }else if(!isIDNumber(user.getIDCard())){
            return "身份证格式错误";
        }else{
            int a=iud.insertSelective(user);
            if(a==1){
                return "添加成功";
            }else {
                return "添加失败，请重新添加";
            }

        }

    }

    @Override
    public String deleteById(int id) {
        int a=iud.deleteById(id);
        if(a==1){
            return "删除成功";
        }else {
            return "删除失败，请重新删除";
        }
    }

    @Override
    public String updateSelective(User user) {
        if(!isMobileNO(user.getTel())){
            return "手机号格式错误";
        }else if(!isIDNumber(user.getIDCard())){
            return "身份证格式错误";
        }else{
            int a=iud.updateSelective(user);

            if(a==1){
                return "修改成功";
            }else {
                return "修改失败，请重新修改";
            }
        }

    }

    @Override
    public List<User> getSomeUser(String param) {
        return iud.getSomeUser("%"+param+"%");
    }


    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        boolean matches = IDNumber.matches(regularExpression);
        //判断第18位校验值
        if (matches) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return matches;
    }


    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern
                    .compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(166)|(17[0-8])|(18[^4,\\D])|(199)|(198))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


}