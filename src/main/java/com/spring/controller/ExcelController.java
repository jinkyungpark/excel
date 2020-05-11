package com.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ExcelController {
	
	
	
	@GetMapping(value = "/downloadExcelFile")
    public String downloadExcelFile(Model model) {     
		log.info("엑셀파일 생성");
        
        List<Member> list = makeMemberList();
        
       // List<Model> list = service.makeFruitList(names, prices, quantities);
        
        SXSSFWorkbook workbook = excelFileDownloadProcess(list);
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook", workbook);
        model.addAttribute("workbookName", "member");
        
        return "excelView";
    }

	
	public ArrayList<Member> makeMemberList(){
        ArrayList<Member> list = new ArrayList<Member>();
        for(int i=0; i< 10; i++) {
        	Member member = new Member();
        	member.setBno(i+"");
        	member.setName("홍길동"+i);
            list.add(member);
        }
        return list;
    }
	
	public SXSSFWorkbook makeSimpleMemberExcelWorkbook(List<Member> list) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        
        // 시트 생성
        SXSSFSheet sheet = workbook.createSheet("멤버");
        
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(0, 3000);
       
        
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("번호");
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("이름");
       
        
        // 과일표 내용 행 및 셀 생성
        Row bodyRow = null;
        Cell bodyCell = null;
        int i=0;
        for(Member member:list) {
          
            // 행 생성
            bodyRow = sheet.createRow(i+1);
            // 데이터 번호 표시
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(member.getBno());
            // 데이터 이름 표시
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(member.getName());
            i++;           
        }
        
        return workbook;
    }
    
    /**
     * 생성한 엑셀 워크북을 컨트롤레에서 받게해줄 메소
     * @param list
     * @return
     */
    public SXSSFWorkbook excelFileDownloadProcess(List<Member> list) {
        return this.makeSimpleMemberExcelWorkbook(list);
    }
}
