package com.rays.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.rays.dto.LeaveManagmentDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.HibDataSource;

public class LeaveManagmentHibImp implements LeaveManagmentInt {

	@Override
	public long add(LeaveManagmentDTO dto) throws ApplicationException, DuplicateRecordException {

		LeaveManagmentDTO existDto = findByLeaveCode(dto.getLeaveCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Leave Code already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Leave Add " + e.getMessage());

		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void update(LeaveManagmentDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		LeaveManagmentDTO existDto = findByLeaveCode(dto.getLeaveCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Leave Code already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Leave Update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void delete(LeaveManagmentDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Leave Delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public LeaveManagmentDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		LeaveManagmentDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (LeaveManagmentDTO) session.get(LeaveManagmentDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Leave by PK");

		} finally {
			session.close();
		}

		return dto;
	}

	public LeaveManagmentDTO findByLeaveCode(String leaveCode) throws ApplicationException {

		Session session = null;
		LeaveManagmentDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LeaveManagmentDTO.class);
			criteria.add(Restrictions.eq("leaveCode", leaveCode));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (LeaveManagmentDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Leave by LeaveCode " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	@Override
	public List search(LeaveManagmentDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(LeaveManagmentDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
				    criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getLeaveCode() != null && dto.getLeaveCode().length() > 0) {
					criteria.add(Restrictions.like("leaveCode", dto.getLeaveCode() + "%"));
				}

				if (dto.getEmployeeName() != null && dto.getEmployeeName().length() > 0) {
					criteria.add(Restrictions.like("employeeName", dto.getEmployeeName() + "%"));
				}

				if (dto.getLeaveStatus() != null && dto.getLeaveStatus().length() > 0) {
					criteria.add(Restrictions.like("leaveStatus", dto.getLeaveStatus() + "%"));
				}

				if (dto.getLeaveStartDate() != null) {
					criteria.add(Restrictions.eq("leaveStartDate", dto.getLeaveStartDate()));
				}

				if (dto.getLeaveEndDate() != null) {
					criteria.add(Restrictions.eq("leaveEndDate", dto.getLeaveEndDate()));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Leave Search");

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

}