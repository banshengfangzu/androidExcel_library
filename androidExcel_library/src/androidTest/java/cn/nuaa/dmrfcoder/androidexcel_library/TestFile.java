package cn.nuaa.dmrfcoder.androidexcel_library;

import java.util.ArrayList;
import java.util.List;

import cn.nuaa.dmrfcoder.androidexceldemo.Bean.DemoBean;
import cn.nuaa.dmrfcoder.androidexceldemo.Utils.ExcelUtil;
import cn.nuaa.dmrfcoder.androidexceldemo.Utils.ReflectDemoUtils;

public class TestFile {
    public static final int max_ex_num = 24;
    public static final double yingli = 0.05;
    public static final double zhisun = 0.02;

    public static void main(String[] args) {

        String filePath = "D:\\androidProject\\demo.xls";

        //创建文件部分     bean可以自己定义  如有需要可以中文命名，这样文件就会显示中文列明
        List<String> titles = ReflectDemoUtils.getFiledNames(DemoBean.class);


        ExcelUtil.initExcel(filePath, "demo", titles);



        //数据生成部分 写自己的代码
        List<DemoBean> demoBeanList = new ArrayList<>();
        inner:for (int i1 = 1; i1 <= 8; i1++) {

            double temp = 0;
            out:for (int i = 0; i < 31; i++) {

                double result = getResult(i, i1);
                if (result <= 1) continue out;
//                System.out.println("倍率:" + i + "胜场:" + i1 + "收益倍数:" + result);
                temp = result > temp ? result : temp;

//                if (result<300&&result>100)
                demoBeanList.add(new DemoBean(i, i1, result));
            }
        }

        //写入数据部分
        ExcelUtil.writeObjListToExcel(demoBeanList, filePath);
    }


//    public static void main(String[] args) {
//
//        String filePath = "D:\\androidProject\\demo.xls";
//
//        //创建文件部分     bean可以自己定义  如有需要可以中文命名，这样文件就会显示中文列明
////        List<String> titles = ReflectDemoUtils.getFiledNames(DemoBean.class);
//
//
//
//
//
//        //写入数据部分
//        ExcelUtil.readObjListToExcel(DemoBean.class,filePath);
//    }


    /**
     * @param x 倍率
     * @param y 胜场 以100为总场次
     * @return
     */
    public static double getResult(int x, int y) {
        double a = 1 - zhisun * x;
        a = a < 0.2 ? 0 : a;
        return Math.pow(yingli * x + 1, y) * Math.pow(a, max_ex_num - y);
    }


}
