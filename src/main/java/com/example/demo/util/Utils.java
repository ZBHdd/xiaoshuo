package com.example.demo.util;

import org.apache.commons.codec.binary.Hex;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {

    /**
     * md5加密
     *
     * @param src
     * @return
     */
    public static String md5Hex(String pwd, String src) {
        byte[] bs = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bs = md5.digest((pwd + src).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(new Hex().encode(bs));
    }

    /**
     * 保存图片
     * @param img
     * @param request
     * @param url
     * @return
     */
    public static String saveImg(MultipartFile img, HttpServletRequest request, String url) {
        /**页面控件的文件流**/
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
        /**构建图片保存的目录**/
        String logoPathDir = url + "/" + dateformat.format(new Date());
        /**得到图片保存目录的真实路径**/
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
        /**根据真实路径创建目录**/
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) logoSaveFile.mkdirs();
        /**页面控件的文件流**/ /**获取文件的后缀**/
        String suffix = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        // /**使用UUID生成文件名称**/ //
        String logImageName = UUID.randomUUID().toString() + suffix;
        //构建文件名称
//        String logImageName = img.getOriginalFilename();
        /**拼成完整的文件保存路径加文件**/
        String fileName = logoRealPathDir + File.separator + logImageName;
        logoPathDir += File.separator + logImageName;
        File file = new File(fileName);
        try {
            img.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return logoPathDir;
    }

    /**
     * request 转 map
     */
    public static Map<String,Object> requestToMap(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        for(Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()){
            map.put(entry.getKey(),entry.getValue()[0]);
        }
        return map;
    }
}
