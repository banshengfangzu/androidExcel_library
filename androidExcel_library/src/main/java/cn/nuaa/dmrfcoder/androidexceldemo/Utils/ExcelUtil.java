package cn.nuaa.dmrfcoder.androidexceldemo.Utils;

import android.content.Context;
import android.util.SparseArray;
import android.widget.Toast;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nuaa.dmrfcoder.androidexceldemo.Bean.DemoBean;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author dmrfcoder
 * @date 2019/2/14
 */

public class ExcelUtil {

    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";


    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化Excel表格
     *
     * @param filePath  存放excel文件的路径（path/demo.xls）
     * @param sheetName Excel表格的表名
     * @param colName   excel中包含的列名（可以有多个）
     */
    public static void initExcel(String filePath, String sheetName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                return;
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, filePath, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 初始化Excel表格
     *
     * @param filePath  存放excel文件的路径（path/demo.xls）
     * @param sheetName Excel表格的表名
     * @param colName   excel中包含的列名（可以有多个）
     */
    public static void initExcel(String filePath, String sheetName, List<String> colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                return;
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, filePath, arial14format));
            for (int col = 0; col < colName.size(); col++) {
                sheet.addCell(new Label(col, 0, colName.get(col), arial10format));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将制定类型的List写入Excel中
     *
     * @param objList  待写入的list
     * @param fileName
     * @param c
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);

                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName), workbook);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 0; j < objList.size(); j++) {
                    DemoBean demoBean = (DemoBean) objList.get(j);
                    List<String> list = new ArrayList<>();
                    list.add(String.valueOf(demoBean.get倍率()));
                    list.add(String.valueOf(demoBean.get胜场()));
                    list.add(String.valueOf(demoBean.get收益倍率()));

                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                        if (list.get(i).length() <= 4) {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 8);
                        } else {
                            //设置列宽
                            sheet.setColumnView(i, list.get(i).length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(j + 1, 350);
                }


                writebook.write();
                workbook.close();
                Toast.makeText(c, "导出Excel成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    /**
     * 将制定类型的List写入Excel中
     *
     * @param objList  待写入的list
     * @param fileName
     * @param
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList, String fileName) {
        if (objList != null) {
            int size = objList.size();
            if (size > 0) {
                WritableWorkbook writebook = null;
                InputStream in = null;
                try {
                    WorkbookSettings setEncode = new WorkbookSettings();
                    setEncode.setEncoding(UTF8_ENCODING);

                    in = new FileInputStream(new File(fileName));
                    Workbook workbook = Workbook.getWorkbook(in);
                    writebook = Workbook.createWorkbook(new File(fileName), workbook);
                    WritableSheet sheet = writebook.getSheet(0);


                    for (int j = 0; j < size; j++) {
//                        DemoBean demoBean = (DemoBean) objList.get(j);

                        T t = objList.get(j);
                        List<String> list = ReflectDemoUtils.setValue(t);

//                        List<String> list = new ArrayList<>();
//                        list.add(String.valueOf(demoBean.get倍率()));
//                        list.add(String.valueOf(demoBean.get胜场()));
//                        list.add(String.valueOf(demoBean.get收益倍率()));

                        for (int i = 0; i < list.size(); i++) {
                            sheet.addCell(new Label(i, j + 1, list.get(i), arial12format));
                            if (list.get(i).length() <= 4) {
                                //设置列宽
                                sheet.setColumnView(i, list.get(i).length() + 8);
                            } else {
                                //设置列宽
                                sheet.setColumnView(i, list.get(i).length() + 5);
                            }
                        }
                        //设置行高
                        sheet.setRowView(j + 1, 350);
                    }


                    writebook.write();
                    workbook.close();
                    System.out.println("输出完毕");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (writebook != null) {
                        try {
                            writebook.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    //    public static final String[] titles = {"姓名", "手机号", "生日"};
    public static final String config = "config";

    /**
     * 将制定类型的List写入Excel中
     *
     * @param
     * @param fileName
     * @param
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> void readObjListToExcel(Class<T> clazz, String fileName) {
//        List<String> filedNames = ReflectDemoUtils.getFiledNames(clazz);

//        WritableWorkbook writebook = null;
//        InputStream out = null;
        try {

            ArrayList<Integer> neededColums = new ArrayList<>();
//            String configStr = SpUtil.getInstance().getStringValue(ExcelUtil.config);
//            WorkbookSettings setEncode = new WorkbookSettings();
//            setEncode.setEncoding(UTF8_ENCODING);


            FileInputStream in = new FileInputStream(new File(fileName));
            Workbook wbb = Workbook.getWorkbook(in);
            WritableWorkbook wb1 = Workbook.createWorkbook(new File(fileName), wbb);
//            Workbook workbook = Workbook.getWorkbook(out);
//            writebook = Workbook.createWorkbook(new File(fileName), workbook);
            Sheet sheet = wb1.getSheet(0);
            int columns = sheet.getColumns();
            int rows = sheet.getRows();
            Gson gson = new Gson();

            SparseArray<String> usefulColums = new SparseArray<>();
//            if (configStr == null || configStr.isEmpty()) {
                Cell[] column = sheet.getColumn(0);
                int length = column.length;

                /**
                 * 循环找出需要的列名
                 */
                List<String> filedNames = ReflectDemoUtils.getFiledNames(clazz);
                out:
                for (int i = 0; i < length; i++) {
                    String contents = column[i].getContents();
                    int size = filedNames.size();
                    inner:
                    for (int i1 = 0; i1 < size; i1++) {
                        if (filedNames.get(i1).equals(contents)) {
                            usefulColums.append(i, filedNames.get(i1));
                            continue out;
                        }
                        if (i1 == size - 1) {
//                            ToastUtils.showShort("没有找到该列" + filedNames.get(i1) + "-----，请注意关键词");
                            System.out.println("没有找到该列" + filedNames.get(i1) + "-----，请注意关键词");
                        }
                    }
                }
                String s = gson.toJson(usefulColums);
                SpUtil.getInstance().setStringValue(ExcelUtil.config, s);

//            } else {
//
//                usefulColums = gson.fromJson(configStr, new TypeToken<SparseArray<String>>() {
//                }.getType());
//
//            }

            List<T> datas = new ArrayList<>();

            int size = usefulColums.size();
            for (int i = 0; i < (columns - 1); i++) {
                T newInstance = clazz.newInstance();
                for (int i1 = 0; i1 < size; i1++) {
                    Cell cell = sheet.getCell(usefulColums.keyAt(i1), i);
                    Method m = clazz.getMethod("set" + usefulColums.valueAt(i1));
                    Field declaredField = newInstance.getClass().getDeclaredField(usefulColums.valueAt(i1));
                    declaredField.set(newInstance,cell.getContents());

                }
                datas.add(newInstance);
            }

            LogUtils.e(datas);
            System.out.println("输出完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


}
