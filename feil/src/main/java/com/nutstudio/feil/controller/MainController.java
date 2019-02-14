package com.nutstudio.feil.controller;

import com.nutstudio.feil.domain.MainListItem;
import com.nutstudio.feil.domain.TestBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @RequestMapping("/")
    public String home() {
        return "hello";
    }

    @RequestMapping("/getTest")
    public TestBean getTest(int code) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new TestBean(code, "success", "dataset");
    }

    @RequestMapping("/getPost")
    public TestBean getPost(int code) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new TestBean(code, "success", "dataset");
    }


    @RequestMapping("/uploads")
    public TestBean uploads(@RequestParam("file") MultipartFile file) {
       // MultipartFile file1 = file[0];
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(file.getOriginalFilename());
        return new TestBean(1, file.getOriginalFilename(), "SIZE " + file.getName() + "   " + file.getOriginalFilename());
    }
    @RequestMapping("/upload")
    public TestBean upload(String des, @RequestParam("file") MultipartFile[] file) {
        MultipartFile file1 = file[0];
        System.out.println(des);
        return new TestBean(1, des, "SIZE " + file.length + "   " + file1.getOriginalFilename());
    }
    @RequestMapping("/getListData")
    public TestBean getListData(String token) {
      

        return new TestBean(1, token, mainListItems);
    }
    private List<MainListItem> mainListItems =new ArrayList<>();

    @RequestMapping(value = "saveItemData",method = RequestMethod.POST)
    @ResponseBody
    public TestBean saveItemData( @RequestBody MainListItem mainListItem) {
        System.out.println(mainListItem.getContent());
        mainListItems.add(mainListItem);
        return new TestBean(1, "121", mainListItems);
    }
}
