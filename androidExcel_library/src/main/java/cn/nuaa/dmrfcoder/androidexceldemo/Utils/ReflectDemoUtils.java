package cn.nuaa.dmrfcoder.androidexceldemo.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectDemoUtils {

    public void test2(Map<String, String> map) throws Exception {
        MyFK myFK = new MyFK();
        Field[] field = myFK.getClass().getDeclaredFields();
        Set<String> keysets = map.keySet();
        for (int i = 0; i < field.length; i++) {
            String fieldName = field[i].getName();//字段名称
            if (keysets.contains(fieldName)) {
                String value = map.get(fieldName);
                String methname = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); //将属性的首字符大写，方便构造get，set方法
                Method m = myFK.getClass().getMethod("set" + methname, String.class);
                m.invoke(myFK, value);
            }
        }
        System.out.println(myFK.toString());
    }


    public class MyFK {
        String name;
        String password;

        /**
         * 通过反射遍历对象得到对象的值
         *
         * @param myFK
         * @throws Exception
         */
        public void test(MyFK myFK) throws Exception {
            Field[] field = myFK.getClass().getDeclaredFields();
            for (int i = 0; i < field.length; i++) {
                String fieldName = field[i].getName();//字段名称
                String methname = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); //将属性的首字符大写，方便构造get，set方法
                Method m = myFK.getClass().getMethod("get" + methname);
                String value = (String) m.invoke(myFK);
                System.out.println(value);
            }
        }
    }


    public static  <T>   Map<String, String>  setValue(T release,Map<String, String>solrMap ) {// 为一个实体类
        Field[] fields = release.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName1 = field.getName(); //得到属性名称
            String fieldName = fieldName1.substring(0, 1).toUpperCase() + fieldName1.substring(1);//把得到属性名称的第一个字母大写
            Object type = null;
            Method m;
            try {
                m = release.getClass().getMethod("get" + fieldName);
                type = m.invoke(release); //得到属性值
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            solrMap.put(fieldName1,String.valueOf(type) );//把属性名称作为Key,属性值作为Value

        }
        return solrMap;
    }

    public static  <T> List<String> setValue(T release) {// 为一个实体类
        Field[] fields = release.getClass().getDeclaredFields();
        List<String> listStrs=new ArrayList<>();
        for (Field field : fields) {
            String fieldName1 = field.getName(); //得到属性名称
            String fieldName = fieldName1.substring(0, 1).toUpperCase() + fieldName1.substring(1);//把得到属性名称的第一个字母大写
            Object type = null;
            Method m;
            try {
                m = release.getClass().getMethod("get" + fieldName);
                type = m.invoke(release); //得到属性值
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            listStrs.add(String.valueOf(type));//把属性名称作为Key,属性值作为Value

        }
        return listStrs;
    }

    /**
     * 获取列名
     * @param release
     * @param <T>
     * @return
     */
    public static  <T> List<String> getFiledNames(Class <T> release) {// 为一个实体类
        Field[] fields = release.getDeclaredFields();
        List<String> listStrs=new ArrayList<>();
        for (Field field : fields) {
            String fieldName1 = field.getName(); //得到属性名称
            listStrs.add(fieldName1);//把属性名称作为Key,属性值作为Value

        }
        return listStrs;
    }

}
