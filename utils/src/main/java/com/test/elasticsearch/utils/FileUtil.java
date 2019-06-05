package com.test.elasticsearch.utils;

import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.CharsetUtil;
import com.test.elasticsearch.utils.word.WordExportUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils
 * @ClassName: WordUtil
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/6/5 9:46
 * @Version: 1.0
 */
public class FileUtil {

    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * 模版变量中变量格式：{{foo}}
     * @param templatePath word模板地址
     * @param fileName 文件名
     * @param params 替换的参数
     * @param request
     * @param response
     */
    public static void exportWord(String templatePath, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, CharsetUtil.UTF_8);
            } else {
                fileName = new String(fileName.getBytes( CharsetUtil.UTF_8),  CharsetUtil.ISO_8859_1);
            }
            MyXWPFDocument doc = new MyXWPFDocument(new ClassPathResource(templatePath).getStream());
            WordExportUtil.exportWord07(doc, params);
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            IoUtil.close(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
