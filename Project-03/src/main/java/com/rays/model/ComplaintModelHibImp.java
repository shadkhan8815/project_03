package com.rays.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.rays.dto.ComplaintDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.util.HibDataSource;

public class ComplaintModelHibImp implements ComplaintModelInt {

	public long add(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {

		ComplaintDTO existDto = null;
		existDto = findByComplaintCode(dto.getComplaintCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Complaint Code already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
				HibDataSource.handleException(e);
			}
			throw new ApplicationException("Exception in Complaint Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	public void update(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		ComplaintDTO existDto = findByComplaintCode(dto.getComplaintCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Complaint Code already exists");
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
			throw new ApplicationException("Exception in Complaint Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void delete(ComplaintDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Complaint Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public ComplaintDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		ComplaintDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ComplaintDTO) session.get(ComplaintDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Complaint by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	public ComplaintDTO findByComplaintCode(String complaintCode) throws ApplicationException {

		Session session = null;
		ComplaintDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ComplaintDTO.class);
			criteria.add(Restrictions.eq("complaintCode", complaintCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ComplaintDTO) list.get(0);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Complaint by Code " + e.getMessage());
		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List search(ComplaintDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ComplaintDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}

				if (dto.getComplaintCode() != null && dto.getComplaintCode().length() > 0) {
					criteria.add(Restrictions.like("complaintCode", dto.getComplaintCode() + "%"));
				}

				if (dto.getComplaintTitle() != null && dto.getComplaintTitle().length() > 0) {
					criteria.add(Restrictions.like("complaintTitle", dto.getComplaintTitle() + "%"));
				}

				if (dto.getRaisedBy() != null && dto.getRaisedBy().length() > 0) {
					criteria.add(Restrictions.like("raisedBy", dto.getRaisedBy() + "%"));
				}

				if (dto.getComplaintStatus() != null && dto.getComplaintStatus().length() > 0) {
					criteria.add(Restrictions.like("complaintStatus", dto.getComplaintStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Complaint Search");
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}
}