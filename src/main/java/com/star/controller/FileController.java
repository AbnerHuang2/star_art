package com.star.controller;

import com.star.model.api.CommonResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * @Author Abner
 * @CreateDate 2020/5/10
 */
@RestController
@RequestMapping("/file")
@Log4j2
public class FileController {

    @Value("${fileDirPath}")
    String fileDirPath;

    @Value("${server.port}")
    String port;

    String server = "http://localhost";

    @RequestMapping("/uploadFile")
    public CommonResult<String> uploadFile(MultipartFile file){
        //图片上传到本地
		try {
			if(!file.isEmpty()) {
				String imgName = file.getOriginalFilename();

				//获取图片后缀名
				String suffix  = imgName.substring(imgName.lastIndexOf(".")+1);
				System.out.println(suffix );

				String randomUUID = UUID.randomUUID().toString();

				randomUUID = randomUUID.replaceAll("-", "");

				System.out.println(randomUUID+"."+suffix);
				File dir = new File(fileDirPath);
                if(!dir.exists()){//如果文件夹不存在
                    dir.mkdir();//创建文件夹
                }
                System.out.println(fileDirPath);
				//将图片保存到本地
				StreamUtils.copy(file.getInputStream(),
						new FileOutputStream(new File(fileDirPath,randomUUID+"."+suffix)));

                return CommonResult.success(server+":"+port+"/file/getImage?name="+randomUUID+"."+suffix,"上传图片成功");
			}

		}catch(Exception e) {
            log.error("图片上传失败 " + e.getMessage());
            e.printStackTrace();
            return CommonResult.failed("上传图片失败");
        }
        return CommonResult.failed("上传图片失败");
    }

    @RequestMapping("/getImage")
    public void getImage(HttpServletResponse response, @RequestParam("name")String img) {

        System.out.println(img);
        //从本地获取图片
		try {

			response.setContentType("image/jpeg");

			StreamUtils.copy(new FileInputStream(new File(fileDirPath,img)),
					response.getOutputStream());
		} catch (Exception e) {

			log.error("获取图片失败");

			e.printStackTrace();
		}
    }

}
