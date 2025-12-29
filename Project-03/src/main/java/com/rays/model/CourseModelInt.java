package com.rays.model;

import java.util.List;

import com.rays.dto.CourseDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;

/**
 * Interface of Course model
 * @author Shad Khan
 *
 */
public interface CourseModelInt {
public long add(CourseDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(CourseDTO dto)throws ApplicationException;
public void update(CourseDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(CourseDTO dto)throws ApplicationException;
public List search(CourseDTO dto,int pageNo,int pageSize)throws ApplicationException;
public CourseDTO findByPK(long pk)throws ApplicationException;
public CourseDTO findByName(String name)throws ApplicationException;
}
