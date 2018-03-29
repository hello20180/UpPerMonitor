package com.hnzy.per.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.WdHistory;


public class ExcelUtil {
	//实时
	public static void exportExcel(List<CGQInfo> wdInfos,ServletOutputStream outputStream) throws IOException{
		//定义显示日期的公共格式
    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
   // String newdate=simpleDateFormat.format(new Date());
		//创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建合并单元格对象
		CellRangeAddress cellRangeAddress=new CellRangeAddress(0,0,0,0);
		//头标题样式
		HSSFCellStyle style1 = creatCellStyle(workbook, (short) 9);
		//列标题样式
		//HSSFCellStyle style2 = creatCellStyle(workbook, (short) 9);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("温度监控系统信息");
		//加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//设置默认列宽
		sheet.setDefaultColumnWidth(15);
		//创建行
		//创建头标题行，并设置头标题
		//HSSFRow row1 = sheet.createRow(0);
		//HSSFCell cell1 = row1.createCell(0);
		//创建题行，并设置头标题
		HSSFRow row2 = sheet.createRow(1);
		String[] titles =
		{ "序号", "小区名称", "地址信息", "温度", "采集日期" , "传感器ID", "用户信息", "联系方式"};
		for (int i = 0; i < titles.length; i++)
		{
			HSSFCell cell2 = row2.createCell(i);
			//加载单元格样式
			cell2.setCellStyle(style1);
			cell2.setCellValue(titles[i]);
		}
		//操作单元格，将用户写Execl
		if(wdInfos!=null){
			for(int j=0;j<wdInfos.size();j++){
			HSSFRow row =sheet .createRow(j+2);
			//序号
			HSSFCell c1=row.createCell(0);
			c1.setCellValue(wdInfos.get(j).getId());
			//小区名称
			HSSFCell c2=row.createCell(1);
			c2.setCellValue(wdInfos.get(j).getXqName());
			//地址信息
			HSSFCell c3=row.createCell(2);
			c3.setCellValue(wdInfos.get(j).getXqName()+wdInfos.get(j).getBuildNO()+"号楼"+wdInfos.get(j).getCellNO()+"单元"+wdInfos.get(j).getHouseNO()+"室");
			//温度
			HSSFCell c4=row.createCell(3);
			if(wdInfos.get(j).getWd()!=null){
				c4.setCellValue(wdInfos.get(j).getWd());
			}else {
				c4.setCellValue("");
			}
			//采集日期
			HSSFCell c5=row.createCell(4); 
			if(wdInfos.get(j).getRecordTime()!=null){
				c5.setCellValue(wdInfos.get(j).getRecordTime());
			}else {
				c5.setCellValue("");
			}
			//传感器ID
			HSSFCell c6=row.createCell(5);
			c6.setCellValue(wdInfos.get(j).getCgqId());
			//用户信息
			HSSFCell c7=row.createCell(6);
			c7.setCellValue(wdInfos.get(j).getName());
			//联系方式
			HSSFCell c8=row.createCell(7);
			c8.setCellValue(wdInfos.get(j).getTel());
			}
		}
		workbook.write(outputStream);
		workbook.close();
	}
	private static HSSFCellStyle creatCellStyle(HSSFWorkbook workbook, short fontSize)
	{
		HSSFCellStyle style=workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font=workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
		font.setFontHeightInPoints(fontSize);
		//加载字体
		style.setFont(font);
		
		return style;
	}
	
	//历史
	public static void exportExcelHis(List<WdHistory> wdInfos,ServletOutputStream outputStream) throws IOException{
		//定义显示日期的公共格式
    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
   // String newdate=simpleDateFormat.format(new Date());
		//创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建合并单元格对象
		CellRangeAddress cellRangeAddress=new CellRangeAddress(0,0,0,0);
		//头标题样式
		HSSFCellStyle style1 = creatCellStyle(workbook, (short) 9);
		//列标题样式
		//HSSFCellStyle style2 = creatCellStyle(workbook, (short) 9);
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("温度监控系统历史信息");
		//加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//设置默认列宽
		sheet.setDefaultColumnWidth(15);
		//创建行
		//创建头标题行，并设置头标题
		//HSSFRow row1 = sheet.createRow(0);
		//HSSFCell cell1 = row1.createCell(0);
		//创建题行，并设置头标题
		HSSFRow row2 = sheet.createRow(1);
		String[] titles =
		{ "序号", "小区名称", "地址信息", "温度", "采集日期" , "传感器ID", "用户信息", "联系方式"};
		for (int i = 0; i < titles.length; i++)
		{
			HSSFCell cell2 = row2.createCell(i);
			//加载单元格样式
			cell2.setCellStyle(style1);
			cell2.setCellValue(titles[i]);
		}
		//操作单元格，将用户写Execl
		if(wdInfos!=null){
			for(int j=0;j<wdInfos.size();j++){
			HSSFRow row =sheet .createRow(j+2);
			//序号
			HSSFCell c1=row.createCell(0);
			c1.setCellValue(wdInfos.get(j).getId());
			//小区名称
			HSSFCell c2=row.createCell(1);
			c2.setCellValue(wdInfos.get(j).getCgq().getXqName());
			//地址信息
			HSSFCell c3=row.createCell(2);
			c3.setCellValue(wdInfos.get(j).getCgq().getXqName()+wdInfos.get(j).getCgq().getBuildNO()+"号楼"+wdInfos.get(j).getCgq().getCellNO()+"单元"+wdInfos.get(j).getCgq().getHouseNO()+"室");
			//温度
			HSSFCell c4=row.createCell(3);
			if(wdInfos.get(j).getWd()!=null){
				c4.setCellValue(wdInfos.get(j).getWd());
			}else {
				c4.setCellValue("");
			}
			//采集日期
			HSSFCell c5=row.createCell(4); 
			if(wdInfos.get(j).getTime()!=null){
				System.out.println("+++++++++++++++"+wdInfos.get(j).getTime());
				c5.setCellValue(wdInfos.get(j).getTime());
			}else {
				c5.setCellValue("");
			}
			//传感器ID
			HSSFCell c6=row.createCell(5);
			c6.setCellValue(wdInfos.get(j).getCgqId());
			//用户信息
			HSSFCell c7=row.createCell(6);
			c7.setCellValue(wdInfos.get(j).getCgq().getName());
			//联系方式
			HSSFCell c8=row.createCell(7);
			c8.setCellValue(wdInfos.get(j).getCgq().getTel());
			}
		}
		workbook.write(outputStream);
		workbook.close();
	}

}
