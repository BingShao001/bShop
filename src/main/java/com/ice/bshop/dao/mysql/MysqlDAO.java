package com.ice.bshop.dao.mysql;

import com.ice.bshop.config.BingRepository;
import com.ice.bshop.dao.BaseDAO;


@BingRepository
public class MysqlDAO implements BaseDAO {

    @Override
    public void test() {
        System.out.println("MysqlDAO...");
    }
}
