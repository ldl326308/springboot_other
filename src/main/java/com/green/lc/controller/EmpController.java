
/*
 * User:liveGreen
 * Date: 2019/5/27
 */

package com.green.lc.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.green.lc.entity.Emp;
import com.green.lc.service.EmpService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @RequestMapping("/emp")
    public List<Emp> findAll() {
        return empService.findAll();
    }


    @ApiOperation(value = "根据ID查询用户", notes = "根据URL的ID来指定对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping("/emp/{empId}")
    public Optional<Emp> findById(@PathVariable("empId") Long empId) {
        return empService.findById(empId);
    }



    /**
     * 导出PDF工具com.lowagie.itext测试
     *
     * @param response
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(value = "/emp/download/pdf", method = RequestMethod.GET)
    public void downloadPdf(HttpServletResponse response) throws IOException, DocumentException {
        // 设置编码
        response.setCharacterEncoding("utf-8");

        //设置响头部
        response.setHeader("Content-Type","application/pdf");
        //设置文件下载的默认名称
        StringBuilder filename = new StringBuilder("attachment;filename=");
        filename.append("employee["+new SimpleDateFormat("yyyyMMdd").format(new Date())+"].pdf");
        response.setHeader("Content-Disposition", String.valueOf(filename));

        //相关中文字体显示配置
        //第一种：使用iTextAsian.jar包中的字体
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);

        //第二种：使用Windows系统字体
        BaseFont baseFont_zh = BaseFont.createFont("C:\\Windows\\Fonts\\STFANGSO.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font_zh = new Font(baseFont_zh);

        //第三种：使用资源字体，也就是自己下载的字体
        BaseFont baseFont_resources = BaseFont.createFont("\\SIMYOU.TIF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font font_resources = new Font(baseFont_resources);


        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());


        document.open();

        List<Emp> all = empService.findAll();

        for (Emp emp : all) {
            PdfPTable pdfPTable = new PdfPTable(5);

            PdfPCell pdfPCell = new PdfPCell();

            pdfPCell.setPhrase(new Paragraph(String.valueOf(emp.getEmpId())));
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);


            pdfPCell = new PdfPCell();
            pdfPCell.setPhrase(new Paragraph(emp.getEmpName(),font_zh));
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);

            pdfPCell = new PdfPCell();
            pdfPCell.setPhrase(new Paragraph(emp.getEmpGender(),font_zh));
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);

            pdfPCell = new PdfPCell();
            pdfPCell.setPhrase(new Paragraph(emp.getEmail(),font_zh));
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);


            pdfPCell = new PdfPCell();
            pdfPCell.setPhrase(new Paragraph(emp.getDepartment(),font_zh));
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        }

        document.close();

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.flush();
        outputStream.close();


    }


    /**
     * POI导出Excel文件
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(value = "/emp/download/excel", method = RequestMethod.GET)
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //编码格式设置
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        //设置头部
        //设置http头部，设置输出为附件
        String filename = new String("employee[" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "].xlsx");

//        response.setHeader("Content-Type","application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("application/octet-stream;charset=utf-8");



        //新建一个工作铺
        XSSFWorkbook wb = new XSSFWorkbook();

        //新建一个sheet页
        Sheet sheet = wb.createSheet("员工信息表");

        //表头
        String[] header = new String[]{"编号", "姓名", "性别", "邮箱", "描述"};

        //创建第一行
        Row row = sheet.createRow(0);

        //表头内容录入
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }


        List<Emp> all = empService.findAll();

        for (int i = 0; i < all.size(); i++) {
            // 创建行
            row = sheet.createRow(i + 1);

            //创建列，并写入内容
            row.createCell(0).setCellValue(all.get(i).getEmpId());
            row.createCell(1).setCellValue(all.get(i).getEmpName());
            row.createCell(2).setCellValue(all.get(i).getEmpGender());
            row.createCell(3).setCellValue(all.get(i).getEmail());
            row.createCell(4).setCellValue(all.get(i).getDepartment());
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

        row = sheet.createRow(all.size() + 1);
        row.createCell(4).setCellValue("生成日期：");
        row.createCell(5).setCellValue(format.format(new Date()));


        ServletOutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }


}
