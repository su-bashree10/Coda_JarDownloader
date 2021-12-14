package com.presidiojardownloader.downloader;

import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.presidiojardownloader.entity.JarFiles;
 
public class ExcelDownload {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<JarFiles> jarFiles;
     
    public ExcelDownload(List<JarFiles> jarFiles) {
        this.jarFiles = jarFiles;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "JarID", style);      
        createCell(row, 1, "JarName", style);       
        createCell(row, 2, "Description", style);    
        createCell(row, 3, "Download link", style);
        createCell(row, 4, "Version", style);
        createCell(row, 5, "Download", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (JarFiles jarFiles : jarFiles) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, jarFiles.getJarFileId().toString(), style);
            createCell(row, columnCount++,jarFiles.getJarName(), style);
            createCell(row, columnCount++, jarFiles.getJarFileDescription(), style);
            createCell(row, columnCount++, jarFiles.getJarFileDownloadUrl().toString(), style);
            createCell(row, columnCount++, jarFiles.getJarFileVersion(), style);
            createCell(row, columnCount++, jarFiles.getNoOfDownloads(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}