package com.ruiliang.appsrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruiliang.appsrv.dao.PimDAO;
import com.ruiliang.appsrv.pojo.Pim;
import com.ruiliang.appsrv.service.PimService;

/**
 * @author LinJian.Liu
 *
 */
@Service
public class PimServiceImpl implements PimService {

	
	@Autowired
	private PimDAO pDao;
	
	
	/* (non-Javadoc)
	 * @see com.ruiliang.appsrv.service.PimService#savePim(com.ruiliang.appsrv.pojo.Pim)
	 */
	@Override
	public Integer savePim(Pim pim) {
		return pDao.savePim(pim);
	}

}
