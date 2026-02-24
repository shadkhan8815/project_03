package com.rays.model;

import java.util.List;

import com.rays.dto.LeaveManagmentDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;

public interface LeaveManagmentInt {

	public long add(LeaveManagmentDTO dto) throws ApplicationException, DuplicateRecordException;

	public void update(LeaveManagmentDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(LeaveManagmentDTO dto) throws ApplicationException;

	public LeaveManagmentDTO findByPK(long pk) throws ApplicationException;

	public LeaveManagmentDTO findByLeaveCode(String leaveCode)throws ApplicationException;

	public List list() throws ApplicationException;

	public List search(LeaveManagmentDTO dto, int pageNo, int pageSize) throws ApplicationException;

}