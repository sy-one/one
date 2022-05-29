package com.example.demo.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.pojo.query.UserQuery;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Override
    public  PageInfo<User> getAllUser(UserQuery userQuery) {

        PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());

        return new PageInfo<User>(mapper.getAllUser());
    }

    @Override
    public long addUser(User user) {
        long k=mapper.addUser(user);
        return k;
    }


    @Override
    public void dwonload(String data, String FilePathAndName) throws IOException {
        //json解析 json=>数组
        JSONArray arr = JSON.parseArray(data);
        //创建工作簿
        Workbook  workbook =new XSSFWorkbook();
        //创建工作表
        Sheet sheet = workbook.createSheet("工作表1");
        Row row = sheet.createRow(0);//创建一行
        Cell ce11=row.createCell(0);//创建单元格
        ce11.setCellValue("id");//给单元格填入内容
        Cell ce21=row.createCell(1);//创建单元格
        ce21.setCellValue("姓名");//给单元格填入内容
        Cell ce31=row.createCell(2);//创建单元格
        ce31.setCellValue("密码");//给单元格填入内容
        for(int i=1;i<arr.size();i++){
            Row row1 = sheet.createRow(i);//创建一行
            Cell ce1=row1.createCell(0);//创建单元格
            ce1.setCellValue(arr.getJSONObject(i).getString("id"));//给单元格填入内容
            Cell ce2=row1.createCell(1);//创建单元格
            ce2.setCellValue(arr.getJSONObject(i).getString("姓名"));//给单元格填入内容
            Cell ce3=row1.createCell(2);//创建单元格
            ce3.setCellValue(arr.getJSONObject(i).getString("密码"));//给单元格填入内容
        }
        //时间
        String time=new DateTime().toString();
        //生成一张表
//        FileOutputStream fileOutputStream=new FileOutputStream(FilePathAndName);
//        workbook.write(fileOutputStream);
//        fileOutputStream.close();
        //创建zip输出流
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/text.zip"));
        //创建输出对象
        ZipEntry zipEntry =new ZipEntry("my.xls");
        //将输出对象添加到压缩输出流
        zipOut.putNextEntry(zipEntry);
        //写数据
//        zipOut.write("ok".getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        bos.writeTo(zipOut);
        //关闭输出流
        zipOut.close();


    }
}

