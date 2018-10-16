package com.container.containerweb.service;

import com.container.containerweb.dao.MerchantDao;
import com.container.containerweb.model.biz.Merchant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MerchantService {

    @Resource
    private MerchantDao merchantDao;

    public List<Merchant> merchantList() {
        return merchantDao.findAll();
    }

    public void save(Merchant merchant) {
        merchantDao.save(merchant);
    }

    public List<Merchant> getByNameLike(String name) {
        if (StringUtils.isEmpty(name)){
            return merchantDao.findAll();
        }
        return merchantDao.findByNameLike("%" + name + "%");
    }
}
