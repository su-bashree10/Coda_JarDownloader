package com.presidiojardownloader.downloader;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.presidiojardownloader.entity.JarFiles;
 
 
public class PdfDownload {
    private List<JarFiles> jarFiles;
    public PdfDownload() {}
    
    
    
     
    public List<JarFiles> getJarFiles() {
		return jarFiles;
	}




	public void setJarFiles(List<JarFiles> jarFiles) {
		this.jarFiles = jarFiles;
	}




	public PdfDownload(List<JarFiles> jarFiles) {
        this.jarFiles = jarFiles;
    }
    
    
    public void setResponseHeader(HttpServletResponse response,String contentType ,String extension,String prefix) throws IOException{
    	DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    	String timestamp = dateformat.format(new Date());
    	String fileName = prefix + timestamp + extension;
    	response.setContentType(contentType);
    	String header_key = "Content-Disposition";
    	String header_value = "attachment; filename="+fileName;
    	response.setHeader(header_key, header_value);
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
    
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("Jar Id", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Jar Name", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Jar Description", font));
        table.addCell(cell);
        
        
        cell.setPhrase(new Phrase("Version", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Download Link", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("downloads", font));
        table.addCell(cell);    
    }
     
    private void writeTableData(PdfPTable table) {
        for (JarFiles jarFiles : jarFiles) {
            table.addCell(String.valueOf(jarFiles.getJarFileId()));
            table.addCell(jarFiles.getJarName());
            table.addCell(jarFiles.getJarFileDescription());
            table.addCell(jarFiles.getJarFileVersion());
            table.addCell(jarFiles.getJarFileDownloadUrl());
            table.addCell(String.valueOf(jarFiles.getNoOfDownloads()));
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
    	setResponseHeader(response,"application/pdf",".pdf","User_");
        Document document = new Document(PageSize.A4);
        
        
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(18);
        font.setColor(Color.BLACK);
         
        Paragraph p = new Paragraph("Jars Uploaded in Jardownloader", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 1.5f, 2.0f, 1.0f, 2.0f,1.0f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
        
         
    }
}