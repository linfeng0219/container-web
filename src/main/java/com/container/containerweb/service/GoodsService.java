package com.container.containerweb.service;

import com.container.containerweb.dao.GoodsDao;
import com.container.containerweb.model.biz.Goods;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    @Value("${web.upload.img}")
    private String imgPath;

    public void addGoods(CommonsMultipartFile goodsImg, Goods goods) throws IOException {
        OutputStream os = null;
        try {
            String hash = DigestUtils.md5DigestAsHex(goodsImg.getBytes());
            os = new FileOutputStream(imgPath + "/" + hash);
            goods.setId(null);
            goods.setComment(hash);
            os.write(goodsImg.getBytes());
            goodsDao.save(goods);
        } finally {
            if (os != null)
                os.close();
        }
    }

    public void saveGoods(Goods goods) {

    }
}
