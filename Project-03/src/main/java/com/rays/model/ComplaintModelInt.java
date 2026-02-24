package com.rays.model;

import java.util.List;

import com.rays.dto.ComplaintDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;

/**
 * Interface of Complaint model
 * 
 * @author Shad Khan
 *
 */
public interface ComplaintModelInt {

	public long add(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(ComplaintDTO dto) throws ApplicationException;

	public void update(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException;

	public ComplaintDTO findByPK(long pk) throws ApplicationException;

	public List list() throws ApplicationException;

	public List search(ComplaintDTO dto, int pageNo, int pageSize) throws ApplicationException;

}
