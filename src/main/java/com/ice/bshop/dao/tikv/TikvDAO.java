package com.ice.bshop.dao.tikv;


import com.ice.bshop.config.BingRepository;
import com.ice.bshop.dao.BaseDAO;

@BingRepository
public class TikvDAO implements BaseDAO {

    @Override
    public void test() {
        System.out.println("TikvDAO..");
    }
}
