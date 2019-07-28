package com.cow.cowboy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value="/user")
public class HelloController {

    @RequestMapping("msg")
    @ResponseBody
    public Map<String, String> logins() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("msg", "success-fuck");
        return hashMap;
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:" + e.getMessage();
    }

    @RequestMapping("/request-param")
    @ResponseBody
    public String requestParam(@RequestParam("id") String id) {
        // 这里访问地址是 http://127.0.0.1:8080/request-param?id=sadho
        return "requestParam id = " + id;

    }


    @RequestMapping("/path-param/{id}")
    @ResponseBody
    public String pathParam(@PathVariable("id") String id) {
        // 这里访问地址是 http://127.0.0.1:8080/user/path-param/sadho
        return "pathvariable id = " + id;

    }


    @RequestMapping("/hellocolor")
    public String index() {
        // 返回的是一个静态页面
        // 对应static/view/index.html
        // 这里访问地址是 http://127.0.0.1:8080/view/index
        return "/welcome.html";
    }


    //SpringBoot必须要依赖视图引擎才能返回视图
    @RequestMapping("getThymeleaf")
    public ModelAndView getThymeleaf() {
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addAllObjects(new HashMap<String, String>(){
            {
                this.put("name","Andy");
                this.put("sex", "female");
            }
        });
        return modelAndView;

    }



    @RequestMapping(value = "/hellocolor",method = {RequestMethod.GET})
    public String gethellocolor(){
        return "helloColor";
    }
}
