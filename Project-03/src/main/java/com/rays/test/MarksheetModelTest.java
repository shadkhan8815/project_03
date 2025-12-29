package com.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.dto.MarksheetDTO;
import com.rays.dto.StudentDTO;
import com.rays.exception.ApplicationException;
import com.rays.exception.DuplicateRecordException;
import com.rays.model.MarksheetModelHibImp;
import com.rays.model.MarksheetModelInt;
import com.rays.model.MarksheetModelJDBCImpl;

public class MarksheetModelTest {
	public static MarksheetModelInt model = new MarksheetModelHibImp();

	// public static MarksheetModelInt model=new MarksheetModelJDBCImpl();
	public static void main(String[] args) throws Exception {
		// addTest();
		// deleteTest();
		// updateTest();
		// findByPkTest();
		// findByEmailIdTest();
		listTest();
		// searchTest();
	}

	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		MarksheetDTO dto = null;
		List list = new ArrayList();
		list = model.getMeritList(0, 10);
		System.out.println(list.size());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (MarksheetDTO) it.next();
			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollNo());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
	}

	public static void searchTest() throws ApplicationException {
		// TODO Auto-generated method stub
		MarksheetDTO dto1 = new MarksheetDTO();
		dto1.setId(1L);
		// dto1.setRollNo("101");
		ArrayList<MarksheetDTO> a = (ArrayList<MarksheetDTO>) model.search(dto1, 0, 0);
		for (MarksheetDTO dto : a) {

			System.out.print(dto.getId());
			System.out.print("\t" + dto.getRollNo());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.println("\t" + dto.getMaths());
		}
	}

	public static void findByEmailIdTest() throws ApplicationException {
		// TODO Auto-generated method stub
		MarksheetDTO dto = model.findByRollNo("101");
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getRollNo());
		System.out.print("\t" + dto.getStudentId());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getPhysics());
		System.out.print("\t" + dto.getChemistry());
		System.out.println("\t" + dto.getMaths());
	}

	public static void findByPkTest() throws ApplicationException {
		// TODO Auto-generated method stub
		MarksheetDTO dto = model.fingByPK(1L);
		System.out.print(dto.getId());
		System.out.print("\t" + dto.getRollNo());
		System.out.print("\t" + dto.getStudentId());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getPhysics());
		System.out.print("\t" + dto.getChemistry());
		System.out.println("\t" + dto.getMaths());
	}

	public static void deleteTest() throws ApplicationException {
		// TODO Auto-generated method stub
		MarksheetDTO dto = new MarksheetDTO();
		dto.setId(2L);
		model.delete(dto);
	}

	public static void addTest() throws Exception {
		// TODO Auto-generated method stub
		MarksheetDTO dto = new MarksheetDTO();
		dto.setChemistry(70);
		dto.setMaths(88);
		dto.setRollNo("102");
		dto.setStudentId(2);
		dto.setPhysics(75);
		dto.setName("Yash");
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(dto);

	}

	public static void updateTest() throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		MarksheetDTO dto = new MarksheetDTO();
		dto.setId(8L);
		dto.setChemistry(50);
		dto.setMaths(60);
		dto.setRollNo("101");
		dto.setStudentId(2);
		dto.setPhysics(25);
		dto.setCreatedBy("Admin");
		dto.setModifiedBy("Admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
	}
}
