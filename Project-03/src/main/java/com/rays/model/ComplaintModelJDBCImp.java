package com.rays.model;

import java.util.List;

import com.rays.dto.ComplaintDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;

public class ComplaintModelJDBCImp implements ComplaintModelInt {

	@Override
	public long add(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(ComplaintDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public ComplaintDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(ComplaintDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
