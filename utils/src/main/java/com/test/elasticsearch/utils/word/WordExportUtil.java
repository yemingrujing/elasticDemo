package com.test.elasticsearch.utils.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.Map;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.utils.word
 * @ClassName: WordExportUtil
 * @Author: guang
 * @Description: 导出工具类
 * @Date: 2019/6/5 11:33
 * @Version: 1.0
 */
public class WordExportUtil {

    private WordExportUtil() {

    }

    /**
     * 解析Word2007版本
     *
     * @param url
     *            模板地址
     * @param map
     *            解析数据源
     * @return
     */
    public static XWPFDocument exportWord07(String url, Map<String, Object> map) throws Exception {
        return new ParseWord07().parseWord(url, map);
    }

    /**
     * 解析Word2007版本
     *
     * @param document
     *            模板
     * @param map
     *            解析数据源
     */
    public static void exportWord07(XWPFDocument document,
                                    Map<String, Object> map) throws Exception {
        new ParseWord07().parseWord(document, map);
    }
}
