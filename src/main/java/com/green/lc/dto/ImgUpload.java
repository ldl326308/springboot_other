
/*
 * User:liveGreen
 * Date: 2019/6/2
 */

package com.green.lc.dto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ImgUpload {


    @RequestMapping(value = "/upload/img", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String imgUpload(@RequestPart("file") MultipartFile multipartFile, HttpServletRequest request) {


        //验证是否选择了要上传的文件(multipartFile是否有值)
        if (multipartFile.isEmpty()) {
            return "未接收到您要上传的图片，请重新选择您要上传的图片！";
        }

        //验证文件类型是否是图片
        //1、获得文件类型字符串
        String contentType = multipartFile.getContentType();
        //2、判断类型是否是图片
        if (!contentType.contains("image/")) {
            return "不是图片类型的文件，上传失败！";
        }

        //验证文件大小
        if (multipartFile.getSize() > 1024 * 1024 * 5) {
            return "图片太大，不允许上传，上传失败！";
        }


        //生成图片的保存路径
//        String savePath = request.getServletContext().getRealPath("/images");
        String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

//        savePath += "images\\";
        File file = new File(savePath);
        if (!file.exists()) {  //如果文件夹不存在，则创建它
            file.mkdirs();
        }


        //生成自己期望的文件名称
        //图片名格式:upload_原图片名称_日期.后缀名

        //获取原文件名称
        String fileName = multipartFile.getOriginalFilename();
        //截取后缀名
        String lastName = fileName.substring(fileName.lastIndexOf("."));
        //生成新的文件名称
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        String datetime = new SimpleDateFormat("yyyyMMddHHssmm").format(new Date());
        String newFileName = "upload_" + fileName + "_" + datetime + lastName;

        //生成物理绝对路径，也就是磁盘路径
        String path = savePath + newFileName + lastName;

        //文件保存到指定位置，上传文件最后的操作
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "上传图片失败了，请重试！";
        }


        return "文件上传成功，新名称是：" + newFileName + "  访问地址：" + "/images/" + newFileName;
    }


}
