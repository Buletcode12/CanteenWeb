package nirmalya.aathithya.webmodule.master.controller;

/**
 * Download Excel ViewFucntion
 */

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;
import nirmalya.aathithya.webmodule.master.model.EmployeeShiftSchedulingModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NirmalyaLabs
 *
 */
public class EmployeePayrollAttendanceExcelModel extends AbstractXlsView {
	Logger logger = LoggerFactory.getLogger(EmployeePayrollAttendanceExcelModel.class);

	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Method : buildExcelDocument function starts");
		try {
			List<AttendanceDateModel> attendanceDateModel = (List<AttendanceDateModel>) model.get("attendance");
			String days = attendanceDateModel.get(0).getDays();
			System.err.println("DAYss" + days);
			HSSFSheet realSheet = ((HSSFWorkbook) workbook).createSheet("Employee Attendance Sheet");

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			// System.err.println("DAYss"+days.equals("28"));
			font.setBold(true);
			font.setColor(HSSFColor.RED.index);
			style.setFont(font);
			realSheet.setColumnWidth(0, 12 * 256);
			realSheet.setColumnWidth(1, 40 * 256);
			realSheet.setColumnWidth(2, 15 * 256);

			realSheet.setColumnWidth(3, 6 * 256);
			realSheet.setColumnWidth(4, 6 * 256);
			realSheet.setColumnWidth(5, 6 * 256);
			realSheet.setColumnWidth(6, 6 * 256);
			realSheet.setColumnWidth(7, 6 * 256);
			realSheet.setColumnWidth(8, 6 * 256);
			realSheet.setColumnWidth(9, 6 * 256);
			realSheet.setColumnWidth(10, 6 * 256);
			realSheet.setColumnWidth(11, 6 * 256);
			realSheet.setColumnWidth(12, 7 * 256);
			realSheet.setColumnWidth(13, 7 * 256);
			realSheet.setColumnWidth(14, 7 * 256);
			realSheet.setColumnWidth(15, 7 * 256);
			realSheet.setColumnWidth(16, 7 * 256);
			realSheet.setColumnWidth(17, 7 * 256);
			realSheet.setColumnWidth(18, 7 * 256);
			realSheet.setColumnWidth(19, 7 * 256);
			realSheet.setColumnWidth(20, 7 * 256);
			realSheet.setColumnWidth(21, 7 * 256);
			realSheet.setColumnWidth(22, 7 * 256);
			realSheet.setColumnWidth(23, 7 * 256);
			realSheet.setColumnWidth(24, 7 * 256);
			realSheet.setColumnWidth(25, 7 * 256);
			realSheet.setColumnWidth(26, 7 * 256);
			realSheet.setColumnWidth(27, 7 * 256);
			realSheet.setColumnWidth(28, 7 * 256);
			realSheet.setColumnWidth(29, 7 * 256);
			realSheet.setColumnWidth(30, 7 * 256);
			if (days.equals("28")) {
				realSheet.setColumnWidth(31, 14 * 256);
				realSheet.setColumnWidth(32, 7 * 256);
				realSheet.setColumnWidth(33, 9 * 256);
				realSheet.setColumnWidth(34, 8 * 256);
				realSheet.setColumnWidth(35, 17 * 256);
				realSheet.setColumnWidth(36, 18 * 256);
			}

			if (days.equals("29")) {
				realSheet.setColumnWidth(31, 7 * 256);
				realSheet.setColumnWidth(32, 14 * 256);
				realSheet.setColumnWidth(33, 7 * 256);
				realSheet.setColumnWidth(34, 9 * 256);
				realSheet.setColumnWidth(35, 8 * 256);
				realSheet.setColumnWidth(36, 17 * 256);
				realSheet.setColumnWidth(37, 18 * 256);
			} else if (days.equals("30")) {
				realSheet.setColumnWidth(31, 7 * 256);
				realSheet.setColumnWidth(32, 7 * 256);
				realSheet.setColumnWidth(33, 14 * 256);
				realSheet.setColumnWidth(34, 7 * 256);
				realSheet.setColumnWidth(35, 9 * 256);
				realSheet.setColumnWidth(36, 8 * 256);
				realSheet.setColumnWidth(37, 17 * 256);
				realSheet.setColumnWidth(38, 18 * 256);
			} else if (days.equals("31")) {
				realSheet.setColumnWidth(31, 7 * 256);
				realSheet.setColumnWidth(32, 7 * 256);
				realSheet.setColumnWidth(33, 7 * 256);
				realSheet.setColumnWidth(34, 14 * 256);
				realSheet.setColumnWidth(35, 7 * 256);
				realSheet.setColumnWidth(36, 9 * 256);
				realSheet.setColumnWidth(37, 8 * 256);
				realSheet.setColumnWidth(38, 17 * 256);
				realSheet.setColumnWidth(39, 18 * 256);
			} else {

			}

			HSSFRow row = realSheet.createRow(0);
			HSSFCell cell = row.createCell(0);

			row.getCell(0).setCellStyle(style);
			cell.setCellValue("Employee Id");

			cell = row.createCell(1);
			row.getCell(1).setCellStyle(style);
			cell.setCellValue("Employee Name");

			cell = row.createCell(2);
			row.getCell(2).setCellStyle(style);
			cell.setCellValue("Approve Status");

			cell = row.createCell(3);
			row.getCell(3).setCellStyle(style);
			cell.setCellValue("Day 1");

			cell = row.createCell(4);
			row.getCell(4).setCellStyle(style);
			cell.setCellValue("Day 2");

			cell = row.createCell(5);
			row.getCell(5).setCellStyle(style);
			cell.setCellValue("Day 3");

			cell = row.createCell(6);
			row.getCell(6).setCellStyle(style);
			cell.setCellValue("Day 4");

			cell = row.createCell(7);
			row.getCell(7).setCellStyle(style);
			cell.setCellValue("Day 5");

			cell = row.createCell(8);
			row.getCell(8).setCellStyle(style);
			cell.setCellValue("Day 6");

			cell = row.createCell(9);
			row.getCell(9).setCellStyle(style);
			cell.setCellValue("Day 7");

			cell = row.createCell(10);
			row.getCell(10).setCellStyle(style);
			cell.setCellValue("Day 8");

			cell = row.createCell(11);
			row.getCell(11).setCellStyle(style);
			cell.setCellValue("Day 9");

			cell = row.createCell(12);
			row.getCell(12).setCellStyle(style);
			cell.setCellValue("Day 10");

			cell = row.createCell(13);
			row.getCell(13).setCellStyle(style);
			cell.setCellValue("Day 11");

			cell = row.createCell(14);
			row.getCell(14).setCellStyle(style);
			cell.setCellValue("Day 12");

			cell = row.createCell(15);
			row.getCell(15).setCellStyle(style);
			cell.setCellValue("Day 13");

			cell = row.createCell(16);
			row.getCell(16).setCellStyle(style);
			cell.setCellValue("Day 14");

			cell = row.createCell(17);
			row.getCell(17).setCellStyle(style);
			cell.setCellValue("Day 15");

			cell = row.createCell(18);
			row.getCell(18).setCellStyle(style);
			cell.setCellValue("Day 16");

			cell = row.createCell(19);
			row.getCell(19).setCellStyle(style);
			cell.setCellValue("Day 17");

			cell = row.createCell(20);
			row.getCell(20).setCellStyle(style);
			cell.setCellValue("Day 18");

			cell = row.createCell(21);
			row.getCell(21).setCellStyle(style);
			cell.setCellValue("Day 19");

			cell = row.createCell(22);
			row.getCell(22).setCellStyle(style);
			cell.setCellValue("Day 20");

			cell = row.createCell(23);
			row.getCell(23).setCellStyle(style);
			cell.setCellValue("Day 21");

			cell = row.createCell(24);
			row.getCell(24).setCellStyle(style);
			cell.setCellValue("Day 22");

			cell = row.createCell(25);
			row.getCell(25).setCellStyle(style);
			cell.setCellValue("Day 23");

			cell = row.createCell(26);
			row.getCell(26).setCellStyle(style);
			cell.setCellValue("Day 24");

			cell = row.createCell(27);
			row.getCell(27).setCellStyle(style);
			cell.setCellValue("Day 25");

			cell = row.createCell(28);
			row.getCell(28).setCellStyle(style);
			cell.setCellValue("Day 26");

			cell = row.createCell(29);
			row.getCell(29).setCellStyle(style);
			cell.setCellValue("Day 27");

			cell = row.createCell(30);
			row.getCell(30).setCellStyle(style);
			cell.setCellValue("Day 28");

			// logger.info("cell.setCellValue("Day 28");");

			// logger.info("Valueee"+setCellValue(););

			/*
			 * if(days.equals("28")) {
			 * 
			 * 
			 * cell = row.createCell(1); row.getCell(1).setCellStyle(style);
			 * cell.setCellValue("Employee Name");
			 * 
			 * 
			 * cell = row.createCell(31); row.getCell(31).setCellStyle(style);
			 * cell.setCellValue("Total Present");
			 * 
			 * cell = row.createCell(32); row.getCell(32).setCellStyle(style);
			 * cell.setCellValue("Leave");
			 * 
			 * cell = row.createCell(33); row.getCell(33).setCellStyle(style);
			 * cell.setCellValue("WeekOff");
			 * 
			 * cell = row.createCell(34); row.getCell(34).setCellStyle(style);
			 * cell.setCellValue("Holiday");
			 * 
			 * cell = row.createCell(35); row.getCell(35).setCellStyle(style);
			 * cell.setCellValue("Company workday");
			 * 
			 * cell = row.createCell(36); row.getCell(36).setCellStyle(style);
			 * cell.setCellValue("Employee workday");
			 * 
			 * } else
			 */if (days.equals("29")) {

				cell = row.createCell(31);
				row.getCell(31).setCellStyle(style);
				cell.setCellValue("Day 29");

				cell = row.createCell(32);
				row.getCell(32).setCellStyle(style);
				cell.setCellValue("Total Present");

				cell = row.createCell(33);
				row.getCell(33).setCellStyle(style);
				cell.setCellValue("Leave");

				cell = row.createCell(34);
				row.getCell(34).setCellStyle(style);
				cell.setCellValue("WeekOff");

				cell = row.createCell(35);
				row.getCell(35).setCellStyle(style);
				cell.setCellValue("Holiday");

				cell = row.createCell(36);
				row.getCell(36).setCellStyle(style);
				cell.setCellValue("Company workday");

				cell = row.createCell(37);
				row.getCell(37).setCellStyle(style);
				cell.setCellValue("Employee workday");

			} else if (days.equals("30")) {
				cell = row.createCell(31);
				row.getCell(31).setCellStyle(style);
				cell.setCellValue("Day 29");

				cell = row.createCell(32);
				row.getCell(32).setCellStyle(style);
				cell.setCellValue("Day 30");

				cell = row.createCell(33);
				row.getCell(33).setCellStyle(style);
				cell.setCellValue("Total Present");

				cell = row.createCell(34);
				row.getCell(34).setCellStyle(style);
				cell.setCellValue("Leave");

				cell = row.createCell(35);
				row.getCell(35).setCellStyle(style);
				cell.setCellValue("WeekOff");

				cell = row.createCell(36);
				row.getCell(36).setCellStyle(style);
				cell.setCellValue("Holiday");

				cell = row.createCell(37);
				row.getCell(37).setCellStyle(style);
				cell.setCellValue("Company workday");

				cell = row.createCell(38);
				row.getCell(38).setCellStyle(style);
				cell.setCellValue("Employee workday");

			}

			else if (days.equals("31")) {
				cell = row.createCell(31);
				row.getCell(31).setCellStyle(style);
				cell.setCellValue("Day 29");

				cell = row.createCell(32);
				row.getCell(32).setCellStyle(style);
				cell.setCellValue("Day 30");

				cell = row.createCell(33);
				row.getCell(33).setCellStyle(style);
				cell.setCellValue("Day 31");

				cell = row.createCell(34);
				row.getCell(34).setCellStyle(style);
				cell.setCellValue("Total Present");

				cell = row.createCell(35);
				row.getCell(35).setCellStyle(style);
				cell.setCellValue("Leave");

				cell = row.createCell(36);
				row.getCell(36).setCellStyle(style);
				cell.setCellValue("WeekOff");

				cell = row.createCell(37);
				row.getCell(37).setCellStyle(style);
				cell.setCellValue("Holiday");

				cell = row.createCell(38);
				row.getCell(38).setCellStyle(style);
				cell.setCellValue("Company workday");

				cell = row.createCell(39);
				row.getCell(39).setCellStyle(style);
				cell.setCellValue("Employee workday");
			} else {
				 cell = row.createCell(31); 
				 row.getCell(31).setCellStyle(style);
				 cell.setCellValue("Total Present");
				 
				 cell = row.createCell(32); 
				 row.getCell(32).setCellStyle(style);
				 cell.setCellValue("Leave");
				 
				 cell = row.createCell(33); 
				 row.getCell(33).setCellStyle(style);
				 cell.setCellValue("WeekOff");
				 
				 cell = row.createCell(34); 
				 row.getCell(34).setCellStyle(style);
				 cell.setCellValue("Holiday");
				 
				 cell = row.createCell(35); 
				 row.getCell(35).setCellStyle(style);
				 cell.setCellValue("Company workday");
				 
				 cell = row.createCell(36); 
				 row.getCell(36).setCellStyle(style);
				 cell.setCellValue("Employee workday");
			}

			int i = 1;
			// int j = 1;

			for (AttendanceDateModel m : attendanceDateModel) {

				row = realSheet.createRow(i++);
				/*
				 * cell = row.createCell(0); cell.setCellValue(j++);
				 */

				cell = row.createCell(0);
				cell.setCellValue(m.getEmpId());

				cell = row.createCell(1);
				cell.setCellValue(m.getName());

				cell = row.createCell(2);
				cell.setCellValue(m.getApproveStatus());

				cell = row.createCell(3);
				cell.setCellValue(m.getId1());

				cell = row.createCell(4);
				cell.setCellValue(m.getId2());

				cell = row.createCell(5);
				cell.setCellValue(m.getId3());

				cell = row.createCell(6);
				cell.setCellValue(m.getId4());

				cell = row.createCell(7);
				cell.setCellValue(m.getId5());

				cell = row.createCell(8);
				cell.setCellValue(m.getId6());

				cell = row.createCell(9);
				cell.setCellValue(m.getId7());

				cell = row.createCell(10);
				cell.setCellValue(m.getId8());

				cell = row.createCell(11);
				cell.setCellValue(m.getId9());

				cell = row.createCell(12);
				cell.setCellValue(m.getId10());

				cell = row.createCell(13);
				cell.setCellValue(m.getId11());

				cell = row.createCell(14);
				cell.setCellValue(m.getId12());

				cell = row.createCell(15);
				cell.setCellValue(m.getId13());

				cell = row.createCell(16);
				cell.setCellValue(m.getId14());

				cell = row.createCell(17);
				cell.setCellValue(m.getId15());

				cell = row.createCell(18);
				cell.setCellValue(m.getId16());

				cell = row.createCell(19);
				cell.setCellValue(m.getId17());

				cell = row.createCell(20);
				cell.setCellValue(m.getId18());

				cell = row.createCell(21);
				cell.setCellValue(m.getId19());

				cell = row.createCell(22);
				cell.setCellValue(m.getId20());

				cell = row.createCell(23);
				cell.setCellValue(m.getId21());

				cell = row.createCell(24);
				cell.setCellValue(m.getId22());

				cell = row.createCell(25);
				cell.setCellValue(m.getId23());

				cell = row.createCell(26);
				cell.setCellValue(m.getId24());

				cell = row.createCell(27);
				cell.setCellValue(m.getId25());

				cell = row.createCell(28);
				cell.setCellValue(m.getId26());

				cell = row.createCell(29);
				cell.setCellValue(m.getId27());

				cell = row.createCell(30);
				cell.setCellValue(m.getId28());

				if (days.equals("29")) {
					cell = row.createCell(31);
					cell.setCellValue(m.getId29());

					cell = row.createCell(32);
					cell.setCellValue(m.getTotalPresent());

					cell = row.createCell(33);
					cell.setCellValue(m.getLeave());

					cell = row.createCell(34);
					cell.setCellValue(m.getWeekoff());

					cell = row.createCell(35);
					cell.setCellValue(m.getHoliday());

					cell = row.createCell(36);
					cell.setCellValue(m.getWorkday());

					cell = row.createCell(37);
					cell.setCellValue(m.getWorkingday());

				} else if (days.equals("30")) {
					cell = row.createCell(31);
					cell.setCellValue(m.getId29());

					cell = row.createCell(32);
					cell.setCellValue(m.getId30());

					cell = row.createCell(33);
					cell.setCellValue(m.getTotalPresent());

					cell = row.createCell(34);
					cell.setCellValue(m.getLeave());

					cell = row.createCell(35);
					cell.setCellValue(m.getWeekoff());

					cell = row.createCell(36);
					cell.setCellValue(m.getHoliday());

					cell = row.createCell(37);
					cell.setCellValue(m.getWorkday());

					cell = row.createCell(38);
					cell.setCellValue(m.getWorkingday());

				} else if (days.equals("31")) {

					cell = row.createCell(31);
					cell.setCellValue(m.getId29());

					cell = row.createCell(32);
					cell.setCellValue(m.getId30());

					cell = row.createCell(33);
					cell.setCellValue(m.getId31());

					cell = row.createCell(34);
					cell.setCellValue(m.getTotalPresent());

					cell = row.createCell(35);
					cell.setCellValue(m.getLeave());

					cell = row.createCell(36);
					cell.setCellValue(m.getWeekoff());

					cell = row.createCell(37);
					cell.setCellValue(m.getHoliday());

					cell = row.createCell(38);
					cell.setCellValue(m.getWorkday());

					cell = row.createCell(39);
					cell.setCellValue(m.getWorkingday());
				} else {
					cell = row.createCell(31);
					cell.setCellValue(m.getTotalPresent());
					
					cell = row.createCell(32);
					cell.setCellValue(m.getLeave());
					
					cell = row.createCell(33);
					cell.setCellValue(m.getWeekoff());
					
					cell = row.createCell(34);
					cell.setCellValue(m.getHoliday());
					
					cell = row.createCell(35);
					cell.setCellValue(m.getWorkday());
					
					cell = row.createCell(36);
					cell.setCellValue(m.getWorkingday());
				}

			}
			logger.info("Method : buildExcelDocument function starts");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
