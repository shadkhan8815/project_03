package com.rays.model;

import java.util.List;

import com.rays.dto.EmployeeDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;

public interface EmployeeModelInt {

	public long add(EmployeeDTO dto) throws ApplicationException, DuplicateRecordException;

	public void update(EmployeeDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(EmployeeDTO dto) throws ApplicationException;

	public EmployeeDTO findByPK(long pk) throws ApplicationException;

	public EmployeeDTO findByUsername(String username) throws ApplicationException;

	public List list() throws ApplicationException;

	public List search(EmployeeDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
