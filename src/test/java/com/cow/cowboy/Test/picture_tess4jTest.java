package com.cow.cowboy.Test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class picture_tess4jTest {

    public static void main(String[] args) throws TesseractException {

        ITesseract instance = new Tesseract();
        //如果未将tessdata放在根目录下需要指定绝对路径
        //设置训练库的位置
        //instance.setDatapath("the absolute path of tessdata");

        //如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
        // chi_sim ：简体中文， eng    根据需求选择语言库
        instance.setLanguage("chi_sim");

        // 指定识别图片
        //C:\\Users\\20153\\Desktop\\图像处理\\read.PNG
        File imgDir = new File("C:\\Users\\20153\\Desktop\\图像处理\\read.PNG");
        long startTime = System.currentTimeMillis();
        String ocrResult = instance.doOCR(imgDir);

        // 输出识别结果
        System.out.println("识别结果: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
    }
}
