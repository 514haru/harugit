package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    public UserService userService;
    @RequestMapping("/welcome")
    public String welcome()
    {
        return "welcome.html";
    }

    @RequestMapping("/getuserlist")
    public @ResponseBody Map<String,Object> getuserlist()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("userlist",userService.getuserlist(1));
        return map;
    }
    //json
    @RequestMapping("/testjson")
    public @ResponseBody Map<String,Object> testjson(@RequestBody Map<String,Object> requestmap)
    {
        System.out.println(requestmap.get("username"));
        Map<String,Object> map = new HashMap<>();
        map.put("userlist",userService.getuserlist(1));
        return map;
    }
    //url1
    @RequestMapping(value = "/testurl1",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> testurl1(@RequestParam("username") String username,@RequestParam("password") String password)
    {
        System.out.println(username);
        System.out.println((password));
        Map<String,Object> map = new HashMap<>();
        map.put("userlist",userService.getuserlist(1));
        return map;
    }
    //url2
    @RequestMapping(value = "/testurl2/{username}/{password}",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> testurl2(@PathVariable String username,@PathVariable String password)
    {
        System.out.println(username);
        System.out.println((password));
        Map<String,Object> map = new HashMap<>();
        map.put("userlist",userService.getuserlist(1));
        return map;
    }
//vue跨域测试
    @RequestMapping("/student/upd")
    public @ResponseBody Map<String,Object> upd(@RequestBody Map<String,Object> requestmap)
    {
        System.out.println(requestmap.get("stuId"));
        Map<String,Object> map = new HashMap<>();
        map.put("data",true);
        return map;
    }
//pagehelper分页测试
    @RequestMapping("/getuserlistpage")
    public @ResponseBody Object getuserlistpage(@RequestBody Map<String,Object> requestmap)
    {
        return userService.getuserlistpage(Integer.parseInt(requestmap.get("pageNum").toString()),Integer.parseInt(requestmap.get("pageSize").toString()),requestmap.get("password").toString());
    }
//上传文件
    @RequestMapping("/upload")
    public @ResponseBody Map<String,Object> upload(@RequestParam("file") MultipartFile file, Model model)
    {
        Map<String,Object> map = new HashMap<>();
        if(file.isEmpty())
        {
            map.put("msg","最大上传文件为10M，上传文件大小超出限制!");
            map.put("result",false);
            return map;
        }

        try {
           String filepath = "e:/upload/";
           String filename = file.getOriginalFilename();

           File targetpath = new File(filepath);

           if (!targetpath.isDirectory())
           {
               targetpath.mkdirs();
           }
            FileOutputStream out = new FileOutputStream(filepath+filename);
            out.write(file.getBytes());
            out.flush();
            out.close();
            map.put("msg","ok");
            map.put("result",true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("msg","最大上传文件为10M，上传文件大小超出限制!");
        map.put("result",false);
        return map;
    }
//多文件上传
    @RequestMapping("/multiupload")
    public @ResponseBody Map<String,Object> multiupload(@RequestParam("file") MultipartFile[] files, Model model)
    {
        Map<String,Object> map = new HashMap<>();
        for (MultipartFile file : files){
            if(file.isEmpty())
            {
                map.put("msg",file.getOriginalFilename()+"文件为空");
                map.put("result",false);
                return map;
            }
        }
        FileOutputStream out = null;
        for (MultipartFile file : files){
            try {
                String filepath = "e:/upload/";
                String filename = file.getOriginalFilename();

                File targetpath = new File(filepath);

                if (!targetpath.isDirectory())
                {
                    targetpath.mkdirs();
                }
                out = new FileOutputStream(filepath+filename);
                out.write(file.getBytes());
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("msg","ok");
        map.put("result",true);
        return map;
    }
//文件下载
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

        // 获取指定目录下的第一个文件
        File filepath = new File("E://upload");
//        File TrxFiles[] = scFileDir.listFiles();
//        System.out.println(TrxFiles[0]);
        String fileName = "测试数据.csv"; //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            File file = new File(filepath, fileName);

            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream;charset=utf-8");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "success";
                }
                catch (Exception e) {
                    return "fail";
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "success";
    }

}
