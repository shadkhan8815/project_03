package com.rays.model;

import java.util.List;

import com.rays.dto.LeaveManagmentDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;

public class LeaveManagmentModelJDBCImpl implements LeaveManagmentInt{

	@Override
	public long add(LeaveManagmentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(LeaveManagmentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(LeaveManagmentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LeaveManagmentDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LeaveManagmentDTO findByLeaveCode(String leaveCode) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(LeaveManagmentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
