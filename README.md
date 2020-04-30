# androidExcel_library   

使用方法

    String filePath = "D:\\androidProject\\demo.xls";

        //创建文件部分     bean可以自己定义  如有需要可以中文命名，这样文件就会显示中文列名
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
