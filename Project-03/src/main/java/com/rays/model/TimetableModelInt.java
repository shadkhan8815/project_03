package com.rays.model;

import java.util.List;

import com.rays.dto.TimetableDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DatabaseException;
import com.rays.exception.DuplicateRecordException;

/**
 * Interface of Timetable model
 * @author Shad Khan
 *
 */
public interface TimetableModelInt {
public long add(TimetableDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(TimetableDTO dto)throws ApplicationException;
public void update(TimetableDTO dto)throws ApplicationException,DuplicateRecordException,DatabaseException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(TimetableDTO dto)throws ApplicationException;
public List search(TimetableDTO dto,int pageNo,int pageSize)throws ApplicationException;
public TimetableDTO findByPK(long pk)throws ApplicationException;
public TimetableDTO checkByCourseName(long courseId,java.util.Date examDate)throws ApplicationException,DuplicateRecordException;
public TimetableDTO checkBySubjectName(long courseId,long subjectId,java.util.Date examDate)throws ApplicationException,DuplicateRecordException;
public TimetableDTO checkBysemester(long courseId,long subjectId,String semester,java.util.Date examDate)throws ApplicationException,DuplicateRecordException;

}
